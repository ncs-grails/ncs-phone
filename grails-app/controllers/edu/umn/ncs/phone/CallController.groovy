package edu.umn.ncs.phone

import edu.umn.ncs.*
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class CallController {

	def reportService
	def authenticateService	
	def debug = false
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
		if (debug) { println "DEBUG: call.create.params: ${params}" }
        def callInstance = new Call()
        callInstance.properties = params
        return [callInstance: callInstance]
    }

	def save = {
		if (debug) { println "DEBUG: call.save.params: ${params}" }
		
		// get the username from the authenticated principal (person) from the auth service
		def username = authenticateService?.principal()?.getUsername()
		
        def callInstance = new Call(params)
		
		callInstance.userCreated = username
		
		if (params?.startTimeString) {
			def fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			def start = fmt.parseDateTime(params?.startTimeString)
			callInstance.startTime = start.toCalendar().time
		}
		if ( ! callInstance.phoningUser ) {
			callInstance.phoningUser = username
		}
		if ( ! callInstance.endTime ) {
			callInstance.endTime = new Date()
		}
		
        if (callInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'call.label', default: 'Call'), callInstance.id])}"
            redirect(action: "show", id: callInstance.id)
        }
        else {
            render(view: "create", model: [callInstance: callInstance])
        }
    }

    def show = {
        def callInstance = Call.get(params.id)
        if (!callInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
        else {
            [callInstance: callInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def edit = {
		if (debug) { println "DEBUG: call.edit" }
        def callInstance = Call.get(params.id)
        if (!callInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [callInstance: callInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def update = {
		if (debug) { println "DEBUG: call.update.params: ${params}" }

		        def callInstance = Call.get(params.id)
        if (callInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (callInstance.version > version) {
                    
                    callInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'call.label', default: 'Call')] as Object[], "Another user has updated this Call while you were editing")
                    render(view: "edit", model: [callInstance: callInstance])
                    return
                }
            }
            callInstance.properties = params
            if (!callInstance.hasErrors() && callInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'call.label', default: 'Call'), callInstance.id])}"
                redirect(action: "show", id: callInstance.id)
            }
            else {
                render(view: "edit", model: [callInstance: callInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def delete = {
        def callInstance = Call.get(params.id)
        if (callInstance) {
            try {
                callInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'call.label', default: 'Call'), params.id])}"
            redirect(action: "list")
        }
    }

	def resultGrid = {
		// find all call attempts to interested parties in the last year
		// and display it in a grid
		def trackedItemInstanceList = []
		def grid = null
		
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
			// fill in the collections that fill the page. (or should we AJAX this?)
						
			grid = reportService.getCallResultGrid(trackedItemInstanceList)
			
			return [ grid: grid ]
		} else {
			render "item(s) not found."
		}
	}

	def history = {
		def trackedItemInstanceList = []
		def callInstanceList = []
		
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
			// fill in the collections that fill the page. (or should we AJAX this?)
			def trackedItemIds = trackedItemInstanceList.collect{it?.id}
			
			callInstanceList = Call.createCriteria().list{
				items {
					'in'('id', trackedItemIds)
				}
				order 'startTime', 'desc'
			}
			
			return [ callInstanceList: callInstanceList ]
		} else {
			render "item(s) not found."
		}
	}

}
