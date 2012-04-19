package edu.umn.ncs.phone

import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent
import edu.umn.ncs.*
import org.joda.time.LocalDate
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class LogCallController {
	
	def springSecurityService

	def batchService
	
	// default to redirecting back to NCS Case Management
    def index = { redirect(url:'/ncs-case-management') }
	
	def menu = { }
	
	def tasks = { }
	
	def item = {

		// look up the tracked item(s) to be called on
		def trackedItemInstanceList = []

		def itemCallResultInstanceList = []
		
		if (params?.id) {

			def idList = params?.id.split(',')
			idList.each{trackedItemId ->
				def trackedItemInstance = TrackedItem.read(trackedItemId)
				
				// check to see if it's been closed out
				def itemCallResultInstance = ItemCallResult.findByTrackedItem(trackedItemInstance)
				
				if (itemCallResultInstance) {
					itemCallResultInstanceList.add(itemCallResultInstance) 
				} else if (trackedItemInstance) {
					trackedItemInstanceList.add(trackedItemInstance)
				}
			}
		}
		
		if (trackedItemInstanceList) {
			// fill in the collections that fill the page. (or should we AJAX this?)
			
			return [ trackedItemInstanceList: trackedItemInstanceList,
				itemCallResultInstanceList: itemCallResultInstanceList ]
			
		} else if (itemCallResultInstanceList) {
			def trackedItemIds = itemCallResultInstanceList.collect{it.trackedItem.id}.join(',')
			
			redirect(controller:'report', action:'item', id:trackedItemIds)
		} else {
			if (flash.message) {
				flash.message += "<br/>"
			} else {
				flash.message = ""
			}
			flash.message += "Sorry, we didn't find any items that matched your criteria."
			redirect(action:'index')
		}
	}
	
	def itemInformation = {
		def trackedItemInstanceList = []
		def itemCallResultInstanceList = []
		
		if (params?.id) {
			def idList = params?.id.split(',')
			idList.each{trackedItemId ->
				def trackedItemInstance = TrackedItem.read(trackedItemId)
				if (trackedItemInstance) {
					// check to see if it's been closed out
					def itemCallResultInstance = ItemCallResult.findByTrackedItem(trackedItemInstance)
					
					if (itemCallResultInstance) {
						itemCallResultInstanceList.add(itemCallResultInstance)
					}
					
					trackedItemInstanceList.add(trackedItemInstance)
				}
			}
		}
		if (trackedItemInstanceList) {
			// fill in the collections that fill the page. (or should we AJAX this?)
			
			return [ trackedItemInstanceList: trackedItemInstanceList,
				itemCallResultInstanceList: itemCallResultInstanceList ]
		} else {
			render "item(s) not found."
		}
	}

	/** this action is used to route calls to/from participants
		and give a kind of dashboard to jump off of. */
	def routeCall = {
		// get the username from the authenticated principal (person) from the auth service
		def username = springSecurityService?.principal?.getUsername()
		def now = new Date()
		def twoWeeksAgo = now - 14
		def personInstance = Person.read(params?.id)
		def formatIdList = InstrumentFormat.findAllByGroupName('phone').collect{it.id}

		if (personInstance) {
			// list recently closed calls
			def recentlyClosedItemCallResultInstanceList = ItemCallResult.createCriteria().list{
				and{
					gt('dateClosed', twoWeeksAgo)
					trackedItem{
						person{ idEq(personInstance.id) }
					}
				}
				order('dateClosed', 'desc')
			}

			def closedItemIds = recentlyClosedItemCallResultInstanceList.collect{ it.trackedItem.id }

			// list all open calls
			def openCallsTrackedItemInstanceList = TrackedItem.createCriteria().list{
				and{
					person{ idEq(personInstance.id) }
					isNull('result')
					batch{ format{ 'in'("id", formatIdList) } }
					not{ 'in'("id", closedItemIds) }
				}
			}

			// list call type available to create
			def callingInstrumentInstanceList = Instrument.createCriteria().list{
				or {
					ilike('name', '%phone%')
					ilike('name', '%call%')
				}
				order('name', 'asc')
			}

			// TODO: in the view: link to personal and work queues
		} else {
			flash.message = "No such person, ID: ${params?.id}."
			redirect(controller:'userQueue', action:'list')
		}
	}

	def newCallToPerson = {
		
		// get the username from the authenticated principal (person) from the auth service
		def username = springSecurityService?.principal?.getUsername()
		def now = new Date()
		def personInstance = Person.read(params?.id)
		
		if ( ! personInstance ) {
			flash.message = "Could not find person, with Person ID: ${params?.id}"
			log.debug "Could not find person, with Person ID: ${params?.id}"
			redirect(action: 'index')
		} else {
						
			def instrumentInstance = Instrument.read(params?.instrument?.id)
			if ( ! instrumentInstance ) {
				// no instrument was passed.  We'll default to adhoc call
				instrumentInstance = Instrument.findByNickName('adhoc_call')
			}
			
			def directionInstance = BatchDirection.read(params?.direction?.id)
			if ( ! directionInstance ) {
				// default to outgoing
				directionInstance = BatchDirection.findByName('outgoing')
			}

			// look to see if one is still pending.
			// pull the last created item of this type
			def trackedItemInstance = TrackedItem.createCriteria().get{
				batch{
					and {
						instruments{
							instrument{ idEq(instrumentInstance.id) }
						}
						direction{ idEq(directionInstance.id) }
					}
					order("dateCreated", "desc")
				}
				maxResults(1)
			}

			def itemCallResultInstance = null

			if (trackedItemInstance) {
				// check for call result
				def callResult = ItemCallResult.findByTrackedItem(trackedItemInstance)
				// if the item has a result, or a call result, let's create a new one.
				if ( trackedItemInstance.result  || callResult ) {
					trackedItemInstance = null
				}
			}

			if (! trackedItemInstance) {
				// generate batch/sid
				trackedItemInstance = batchService.createCallingItem(username, personInstance, null, null, instrumentInstance, directionInstance)
			}
			
			// Add to User Queue
			def userQueueInstance = UserQueue.findByUsername(username)
			if ( ! userQueueInstance ) {
				userQueueInstance = new UserQueue(username: username)
			}
			userQueueInstance.addToTrackedItems(trackedItemInstance)
			userQueueInstance.save(flush:true)
			
			// redirect to "item" action, passing the ID of the tracked item
			redirect(action: 'item', id: trackedItemInstance?.id)
		}
	}
	
	def deletedTransactions = {
		def trackedItemInstanceList = []
		def deletedTransactionsList = []
		// get the username from the authenticated principal (person) from the auth service
		def username = springSecurityService?.principal?.getUsername()

		if (params?.id) {
			def idList = params?.id.split(',')
			
			idList.each{trackedItemId ->
				def trackedItemInstance = TrackedItem.read(trackedItemId)
				if (trackedItemInstance) {
					trackedItemInstanceList.add(trackedItemInstance)
				}
			}
		}
		if (trackedItemInstanceList) {
			
			def auditLogEventList = AuditLogEvent.createCriteria().list {
				and{
					eq('actor', username)
					like('className', 'edu.umn.ncs.phone.%')
					eq('eventName', 'DELETE')
					isNotNull('oldValue')
				}
				order('dateCreated', 'desc')
				maxResults(20)
			}
			
			auditLogEventList.each{
				deletedTransactionsList.add(it)
			}
			
			return [ trackedItemInstanceList: trackedItemInstanceList
				, deletedTransactionsList: deletedTransactionsList ]
		} else {
			render "item(s) not found."
		}
	}
	
	def lookup = {
		def trackedItemInstanceList = []
		
		if (params?.id) {
			def idList = params?.id.split(',')
			
			idList.each{trackedItemId ->
				def trackedItemInstance = TrackedItem.read(trackedItemId)
				if (trackedItemInstance) {
					trackedItemInstanceList.add(trackedItemInstance)
				}
			}
		}
		if (trackedItemInstanceList) {			
			return [ trackedItemInstanceList: trackedItemInstanceList ]
		} else {
			render "item(s) not found."
		}
	}

	def report = { render "TODO:" }

	@Secured(['ROLE_NCS_IT'])
	def administer = { render "TODO:" }
	
}
