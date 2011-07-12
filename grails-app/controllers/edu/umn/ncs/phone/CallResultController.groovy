package edu.umn.ncs.phone

import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_IT'])
class CallResultController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [callResultInstanceList: CallResult.list(params), callResultInstanceTotal: CallResult.count()]
    }

    def create = {
        def callResultInstance = new CallResult()
        callResultInstance.properties = params
        return [callResultInstance: callResultInstance]
    }

    def save = {
        def callResultInstance = new CallResult(params)
        if (callResultInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'callResult.label', default: 'CallResult'), callResultInstance.id])}"
            redirect(action: "show", id: callResultInstance.id)
        }
        else {
            render(view: "create", model: [callResultInstance: callResultInstance])
        }
    }

    def show = {
        def callResultInstance = CallResult.get(params.id)
        if (!callResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResult.label', default: 'CallResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            [callResultInstance: callResultInstance]
        }
    }

    def edit = {
        def callResultInstance = CallResult.get(params.id)
        if (!callResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResult.label', default: 'CallResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [callResultInstance: callResultInstance]
        }
    }

    def update = {
        def callResultInstance = CallResult.get(params.id)
        if (callResultInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (callResultInstance.version > version) {
                    
                    callResultInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'callResult.label', default: 'CallResult')] as Object[], "Another user has updated this CallResult while you were editing")
                    render(view: "edit", model: [callResultInstance: callResultInstance])
                    return
                }
            }
            callResultInstance.properties = params
            if (!callResultInstance.hasErrors() && callResultInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'callResult.label', default: 'CallResult'), callResultInstance.id])}"
                redirect(action: "show", id: callResultInstance.id)
            }
            else {
                render(view: "edit", model: [callResultInstance: callResultInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResult.label', default: 'CallResult'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def callResultInstance = CallResult.get(params.id)
        if (callResultInstance) {
            try {
                callResultInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'callResult.label', default: 'CallResult'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'callResult.label', default: 'CallResult'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResult.label', default: 'CallResult'), params.id])}"
            redirect(action: "list")
        }
    }
}
