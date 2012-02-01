package edu.umn.ncs.phone
import edu.umn.ncs.*

import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class ItemCallResultController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def authenticateService
	def debug = false

	@Secured(['ROLE_NCS_IT'])
    def create = {
		if (debug) { println "DEBUG: ItemCallResultController.create.params: ${params}" }
        def itemCallResultInstance = new ItemCallResult()
        itemCallResultInstance.properties = params
        return [itemCallResultInstance: itemCallResultInstance]
    }
	
	def createCollection = {
		def trackedItemInstanceList = []
		def resultInstanceList = []
		def itemCallResultInstance = new ItemCallResult()
		def nonParticipationReasonInstanceList = NonParticipationReason.list(sort:'name')
		
		def refusedResultList = Result.findAllByRefused(true)
		
		if (params?.id) {
			trackedItemInstanceList.add(TrackedItem.read(params?.id))
		}
		if (trackedItemInstanceList) {
			// fill in the collections that fill the page. (or should we AJAX this?)
			
			resultInstanceList = Result.createCriteria().list{
				eq('phoneCall', true)
				order 'name', 'asc'
			}
			
			return [ trackedItemInstanceList: trackedItemInstanceList
				, resultInstanceList: resultInstanceList
				, nonParticipationReasonInstanceList: nonParticipationReasonInstanceList
				, itemCallResultInstance: itemCallResultInstance
				, refusedResultIds: refusedResultList.collect{it.id}.join(',') ]
		} else {
			render "item(s) not found."
		}
	}

	@Secured(['ROLE_NCS_IT'])
    def save = {
		if (debug) { println "DEBUG: ItemCallResultController.save.params: ${params}" }

		        def itemCallResultInstance = new ItemCallResult(params)
        if (itemCallResultInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), itemCallResultInstance.id])}"
            redirect(action: "show", id: itemCallResultInstance.id)
        }
        else {
            render(view: "create", model: [itemCallResultInstance: itemCallResultInstance])
        }
    }

	// saves a list instances at once
	def saveCollection = {
		
		// get the username from the authenticated principal (person) from the auth service
		def username = authenticateService?.principal()?.getUsername()
		
		if (debug) { println "DEBUG: ItemCallResultController.saveCollection.params: ${params}" }

		def trackedItemInstanceList = []
		def itemCallResultInstanceList = []
		flash.message = ""
		
		if (params?.items) {
			trackedItemInstanceList.add(TrackedItem.read(params?.items))
		}
		
		def trackedItemIds = trackedItemInstanceList.collect{it?.id}
		
		if (trackedItemInstanceList) {
			// each tracked item gets a ItemCallResult
			trackedItemInstanceList.each{ ti ->
				if (debug) {
					println "DEBUG: closing out SID: ${ti.id}"
				}
				
				def itemCallResultInstance = new ItemCallResult(params)
				itemCallResultInstance.userCreated = username
				itemCallResultInstance.trackedItem = ti
				if (itemCallResultInstance.save(flush: true)) {
					flash.message += "${message(code: 'default.created.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), itemCallResultInstance.id])}"
					itemCallResultInstanceList.add(itemCallResultInstance)
					
					def userQueueInstance = UserQueue.findByUsername(username)
					if ( userQueueInstance ) {
						userQueueInstance.removeFromTrackedItems(ti)
						userQueueInstance.save(flush:true)
					}
		
					
				} else {
					println "FAILED to close out SID: ${ti.id}"
					if (debug) {
						itemCallResultInstance.errors.each{
							println "\t${it}"
						}
					}
					flash.message += "Failed to save Final Result for item: ${ti.batch.primaryInstrument}."
				}
			}
		}
		redirect(action: "show", id: itemCallResultInstanceList.collect{it?.id}.join(',') )
	}
	
    def show = {
		if (debug) { println "DEBUG: ItemCallResultController.show.params: ${params}" }

        def itemCallResultInstance = ItemCallResult.get(params.id)
        if (!itemCallResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            [itemCallResultInstance: itemCallResultInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def edit = {
		if (debug) { println "DEBUG: ItemCallResultController.edit.params: ${params}" }
		
        def itemCallResultInstance = ItemCallResult.get(params.id)
        if (!itemCallResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [itemCallResultInstance: itemCallResultInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def update = {
		if (debug) { println "DEBUG: ItemCallResultController.update.params: ${params}" }
		
        def itemCallResultInstance = ItemCallResult.get(params.id)
        if (itemCallResultInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (itemCallResultInstance.version > version) {
                    
                    itemCallResultInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'itemCallResult.label', default: 'ItemCallResult')] as Object[], "Another user has updated this ItemCallResult while you were editing")
                    render(view: "edit", model: [itemCallResultInstance: itemCallResultInstance])
                    return
                }
            }
            itemCallResultInstance.properties = params
            if (!itemCallResultInstance.hasErrors() && itemCallResultInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), itemCallResultInstance.id])}"
                redirect(action: "show", id: itemCallResultInstance.id)
            }
            else {
                render(view: "edit", model: [itemCallResultInstance: itemCallResultInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def delete = {
        def itemCallResultInstance = ItemCallResult.get(params.id)
        if (itemCallResultInstance) {
			
			def trackedItemInstance = itemCallResultInstance.trackedItem
			
            try {
                itemCallResultInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), params.id])}"
				// start logging the call
                redirect(controller:'logCall', action: "item", id:trackedItemInstance.id)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'itemCallResult.label', default: 'ItemCallResult'), params.id])}"
            redirect(action: "list")
        }
    }
}
