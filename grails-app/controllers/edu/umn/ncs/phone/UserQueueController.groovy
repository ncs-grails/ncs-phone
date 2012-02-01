package edu.umn.ncs.phone

import edu.umn.ncs.*
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class UserQueueController {
	
	def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "show", params: params)
    }

	@Secured(['ROLE_NCS_IT'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userQueueInstanceList: UserQueue.list(params), userQueueInstanceTotal: UserQueue.count()]
    }

	@Secured(['ROLE_NCS_IT'])
    def create = {
        def userQueueInstance = new UserQueue()
        userQueueInstance.properties = params
        return [userQueueInstance: userQueueInstance]
    }

	@Secured(['ROLE_NCS_IT'])
    def save = {
        def userQueueInstance = new UserQueue(params)
        if (userQueueInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userQueue.label', default: 'UserQueue'), userQueueInstance.id])}"
            redirect(action: "show", id: userQueueInstance.id)
        }
        else {
            render(view: "create", model: [userQueueInstance: userQueueInstance])
        }
    }

    def show = {
		// get the username from the authenticated principal (person) from the auth service
		def username = springSecurityService?.principal?.getUsername()

        def userQueueInstance = UserQueue.findByUsername(username)
		if (!userQueueInstance) {
			userQueueInstance = new UserQueue(username: username)
			userQueueInstance.save(flush:true)
		}
			
        [userQueueInstance: userQueueInstance]
    }

	@Secured(['ROLE_NCS_IT'])
    def edit = {
        def userQueueInstance = UserQueue.get(params.id)
        if (!userQueueInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userQueue.label', default: 'UserQueue'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userQueueInstance: userQueueInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def update = {
        def userQueueInstance = UserQueue.get(params.id)
        if (userQueueInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userQueueInstance.version > version) {
                    
                    userQueueInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userQueue.label', default: 'UserQueue')] as Object[], "Another user has updated this UserQueue while you were editing")
                    render(view: "edit", model: [userQueueInstance: userQueueInstance])
                    return
                }
            }
            userQueueInstance.properties = params
            if (!userQueueInstance.hasErrors() && userQueueInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userQueue.label', default: 'UserQueue'), userQueueInstance.id])}"
                redirect(action: "show", id: userQueueInstance.id)
            }
            else {
                render(view: "edit", model: [userQueueInstance: userQueueInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userQueue.label', default: 'UserQueue'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def delete = {
        def userQueueInstance = UserQueue.get(params.id)
        if (userQueueInstance) {
            try {
                userQueueInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userQueue.label', default: 'UserQueue'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userQueue.label', default: 'UserQueue'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userQueue.label', default: 'UserQueue'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def removeItem = {
		def userQueueInstance = UserQueue.get(params.id)
		def trackedItemInstance = TrackedItem.read(params.trackedItem?.id)
		if (userQueueInstance && trackedItemInstance) {

			userQueueInstance.removeFromTrackedItems(trackedItemInstance)
			
			if (!userQueueInstance.hasErrors() && userQueueInstance.save(flush: true)) {
				flash.message = "${trackedItemInstance.batch.primaryInstrument}, ID: ${trackedItemInstance.id} removed from ${userQueueInstance.username}'s queue."
				redirect(action: "show", id: userQueueInstance.id)
			}
			else {
				render(view: "show", model: [userQueueInstance: userQueueInstance])
			}
		} else if (userQueueInstance) {
			flash.message = "Unknown Tracked Item ID."
			redirect(action: "show", id: userQueueInstance.id)
		} else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userQueue.label', default: 'User Queue'), params.id])}"
			redirect(action: "list")
		}
	}

}
