package edu.umn.ncs.phone

import edu.umn.ncs.*
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class WorkQueueController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [workQueueInstanceList: WorkQueue.list(params), workQueueInstanceTotal: WorkQueue.count()]
    }

	@Secured(['ROLE_NCS_IT'])
    def create = {
        def workQueueInstance = new WorkQueue()
        workQueueInstance.properties = params
        return [workQueueInstance: workQueueInstance]
    }

	@Secured(['ROLE_NCS_IT'])
    def save = {
        def workQueueInstance = new WorkQueue(params)
        if (workQueueInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), workQueueInstance.id])}"
            redirect(action: "show", id: workQueueInstance.id)
        }
        else {
            render(view: "create", model: [workQueueInstance: workQueueInstance])
        }
    }

    def show = {
        def workQueueInstance = WorkQueue.get(params.id)
        if (!workQueueInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), params.id])}"
            redirect(action: "list")
        }
        else {
            [workQueueInstance: workQueueInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def edit = {
        def workQueueInstance = WorkQueue.get(params.id)
        if (!workQueueInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [workQueueInstance: workQueueInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def update = {
        def workQueueInstance = WorkQueue.get(params.id)
        if (workQueueInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (workQueueInstance.version > version) {
                    
                    workQueueInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'workQueue.label', default: 'WorkQueue')] as Object[], "Another user has updated this WorkQueue while you were editing")
                    render(view: "edit", model: [workQueueInstance: workQueueInstance])
                    return
                }
            }
            workQueueInstance.properties = params
            if (!workQueueInstance.hasErrors() && workQueueInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), workQueueInstance.id])}"
                redirect(action: "show", id: workQueueInstance.id)
            }
            else {
                render(view: "edit", model: [workQueueInstance: workQueueInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), params.id])}"
            redirect(action: "list")
        }
    }

	def removeItem = {
		def workQueueInstance = WorkQueue.get(params.id)
		def trackedItemInstance = TrackedItem.read(params.trackedItem?.id)
		if (workQueueInstance && trackedItemInstance) {

			workQueueInstance.removeFromTrackedItems(trackedItemInstance)
			
			if (!workQueueInstance.hasErrors() && workQueueInstance.save(flush: true)) {
				flash.message = "${trackedItemInstance.batch.primaryInstrument}, ID: ${trackedItemInstance.id} removed from ${workQueueInstance}"
				redirect(action: "show", id: workQueueInstance.id)
			}
			else {
				render(view: "show", model: [workQueueInstance: workQueueInstance])
			}
		} else if (workQueueInstance) {
			flash.message = "Unknown Tracked Item ID."
			redirect(action: "show", id: workQueueInstance.id)
		} else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), params.id])}"
			redirect(action: "list")
		}
	}
	
	@Secured(['ROLE_NCS_IT'])
    def delete = {
        def workQueueInstance = WorkQueue.get(params.id)
        if (workQueueInstance) {
            try {
                workQueueInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'workQueue.label', default: 'WorkQueue'), params.id])}"
            redirect(action: "list")
        }
    }
}
