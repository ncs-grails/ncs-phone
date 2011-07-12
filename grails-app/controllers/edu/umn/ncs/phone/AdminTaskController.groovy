package edu.umn.ncs.phone

import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class AdminTaskController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
        def adminTaskInstance = new AdminTask()
        adminTaskInstance.properties = params
        return [adminTaskInstance: adminTaskInstance]
    }

    def save = {
        def adminTaskInstance = new AdminTask(params)
        if (adminTaskInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), adminTaskInstance.id])}"
            redirect(action: "show", id: adminTaskInstance.id)
        }
        else {
            render(view: "create", model: [adminTaskInstance: adminTaskInstance])
        }
    }

    def show = {
        def adminTaskInstance = AdminTask.get(params.id)
        if (!adminTaskInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), params.id])}"
            redirect(action: "list")
        }
        else {
            [adminTaskInstance: adminTaskInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def edit = {
        def adminTaskInstance = AdminTask.get(params.id)
        if (!adminTaskInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [adminTaskInstance: adminTaskInstance]
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def update = {
        def adminTaskInstance = AdminTask.get(params.id)
        if (adminTaskInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (adminTaskInstance.version > version) {
                    
                    adminTaskInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'adminTask.label', default: 'AdminTask')] as Object[], "Another user has updated this AdminTask while you were editing")
                    render(view: "edit", model: [adminTaskInstance: adminTaskInstance])
                    return
                }
            }
            adminTaskInstance.properties = params
            if (!adminTaskInstance.hasErrors() && adminTaskInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), adminTaskInstance.id])}"
                redirect(action: "show", id: adminTaskInstance.id)
            }
            else {
                render(view: "edit", model: [adminTaskInstance: adminTaskInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_NCS_IT'])
    def delete = {
        def adminTaskInstance = AdminTask.get(params.id)
        if (adminTaskInstance) {
            try {
                adminTaskInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'adminTask.label', default: 'AdminTask'), params.id])}"
            redirect(action: "list")
        }
    }
}
