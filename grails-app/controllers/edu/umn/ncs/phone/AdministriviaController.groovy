package edu.umn.ncs.phone

import edu.umn.ncs.*
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class AdministriviaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def authenticateService
	
	def debug = false

    def index = {
        redirect(view:"/")
    }

    def create = {
        def administriviaInstance = new Administrivia()
        administriviaInstance.properties = params
        return [administriviaInstance: administriviaInstance]
    }

    def save = {
		if (debug) { println "AdministriviaController:save:params::${params}\n" }

		// get the username from the authenticated principal (person) from the auth service
		def username = authenticateService?.principal()?.getUsername()
		
        def administriviaInstance = new Administrivia(params)
		
		administriviaInstance.userCreated = username
		
		if (params?.startTimeString) {
			def fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			def start = fmt.parseDateTime(params?.startTimeString)
			administriviaInstance.startTime = start.toCalendar().time
		}
		if ( ! administriviaInstance.phoningUser ) {
			administriviaInstance.phoningUser = username
		}
		if ( ! administriviaInstance.endTime ) {
			administriviaInstance.endTime = new Date()
		}

		params?.formsCompleted?.each{ fcId ->
			if (debug) { println "AdministriviaController:save:form added::${fcId}\n" }
			def formInstance = Form.read(fcId)
			administriviaInstance.addToFormsCompleted(formInstance)
		}
		
		params?.tasks?.each{ taskId ->
			if (debug) { println "AdministriviaController:save:form added::${taskId}\n" }
			def taskInstance = AdminTask.read(taskId)
			administriviaInstance.addToTasks(taskInstance)
		}

		        if (administriviaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), administriviaInstance.id])}"
			if (debug) { println "AdministriviaController:save:success\n" }
            redirect(action: "show", id: administriviaInstance.id)
        }
        else {
			if (debug) {
				println "AdministriviaController:save:failure\n"
				administriviaInstance.errors.each{
					println "\terror:\n\t\t${it}"
				}
			}
            render(view: "create", model: [administriviaInstance: administriviaInstance])
        }
    }

    def show = {
        def administriviaInstance = Administrivia.get(params.id)
        if (!administriviaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), params.id])}"
            redirect(action: "list")
        }
        else {
            [administriviaInstance: administriviaInstance]
        }
    }

    @Secured(['ROLE_NCS_IT'])
	def edit = {
        def administriviaInstance = Administrivia.get(params.id)
        if (!administriviaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [administriviaInstance: administriviaInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def update = {
        def administriviaInstance = Administrivia.get(params.id)
        if (administriviaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (administriviaInstance.version > version) {
                    
                    administriviaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'administrivia.label', default: 'Administrivia')] as Object[], "Another user has updated this Administrivia while you were editing")
                    render(view: "edit", model: [administriviaInstance: administriviaInstance])
                    return
                }
            }
            administriviaInstance.properties = params
            if (!administriviaInstance.hasErrors() && administriviaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), administriviaInstance.id])}"
                redirect(action: "show", id: administriviaInstance.id)
            }
            else {
                render(view: "edit", model: [administriviaInstance: administriviaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def delete = {
        def administriviaInstance = Administrivia.get(params.id)
        if (administriviaInstance) {
            try {
                administriviaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'administrivia.label', default: 'Administrivia'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def history = {
			
		def trackedItemInstanceList = []
		def administriviaInstanceList = []
		
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
			
			administriviaInstanceList = Administrivia.createCriteria().list{
				items {
					'in'('id', trackedItemIds)
				}
				order 'startTime', 'desc'
			}
			
			return [ administriviaInstanceList: administriviaInstanceList ]
		} else {
			render "item(s) not found."
		}
	}

}
