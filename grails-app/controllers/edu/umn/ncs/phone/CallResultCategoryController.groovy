package edu.umn.ncs.phone

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_IT'])
class CallResultCategoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [callResultCategoryInstanceList: CallResultCategory.list(params), callResultCategoryInstanceTotal: CallResultCategory.count()]
    }

    def create = {
        def callResultCategoryInstance = new CallResultCategory()
        callResultCategoryInstance.properties = params
        return [callResultCategoryInstance: callResultCategoryInstance]
    }

    def save = {
        def callResultCategoryInstance = new CallResultCategory(params)
        if (callResultCategoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), callResultCategoryInstance.id])}"
            redirect(action: "show", id: callResultCategoryInstance.id)
        }
        else {
            render(view: "create", model: [callResultCategoryInstance: callResultCategoryInstance])
        }
    }

    def show = {
        def callResultCategoryInstance = CallResultCategory.get(params.id)
        if (!callResultCategoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), params.id])}"
            redirect(action: "list")
        }
        else {
            [callResultCategoryInstance: callResultCategoryInstance]
        }
    }

    def edit = {
        def callResultCategoryInstance = CallResultCategory.get(params.id)
        if (!callResultCategoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [callResultCategoryInstance: callResultCategoryInstance]
        }
    }

    def update = {
        def callResultCategoryInstance = CallResultCategory.get(params.id)
        if (callResultCategoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (callResultCategoryInstance.version > version) {
                    
                    callResultCategoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'callResultCategory.label', default: 'CallResultCategory')] as Object[], "Another user has updated this CallResultCategory while you were editing")
                    render(view: "edit", model: [callResultCategoryInstance: callResultCategoryInstance])
                    return
                }
            }
            callResultCategoryInstance.properties = params
            if (!callResultCategoryInstance.hasErrors() && callResultCategoryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), callResultCategoryInstance.id])}"
                redirect(action: "show", id: callResultCategoryInstance.id)
            }
            else {
                render(view: "edit", model: [callResultCategoryInstance: callResultCategoryInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def callResultCategoryInstance = CallResultCategory.get(params.id)
        if (callResultCategoryInstance) {
            try {
                callResultCategoryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'callResultCategory.label', default: 'CallResultCategory'), params.id])}"
            redirect(action: "list")
        }
    }
}
