package edu.umn.ncs.phone

import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent
import edu.umn.ncs.*
import org.joda.time.LocalDate
import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class LogCallController {
	
	def authenticateService

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

	def newCallToPerson = {
		
		// get the username from the authenticated principal (person) from the auth service
		def username = authenticateService?.principal()?.getUsername()

		def personInstance = Person.read(params?.id)
		
		if ( ! personInstance ) {
			flash.message = "Could not find person, with Person ID: ${params?.id}"
			println "Could not find person, with Person ID: ${params?.id}"
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

			// look to see if one is still pending
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
				throw finishThisException
			}
			
			// generate batch/sid
			def trackedItemInstance = batchService.createCallingItem(username, personInstance, null, null, instrumentInstance, directionInstance)
			
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
		def username = authenticateService?.principal()?.getUsername()

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
