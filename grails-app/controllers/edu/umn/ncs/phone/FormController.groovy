package edu.umn.ncs.phone

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_IT'])
class FormController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [formInstanceList: Form.list(params), formInstanceTotal: Form.count()]
    }

    def create = {
        def formInstance = new Form()
        formInstance.properties = params
        return [formInstance: formInstance]
    }

    def save = {
        def formInstance = new Form(params)
        if (formInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'form.label', default: 'Form'), formInstance.id])}"
            redirect(action: "show", id: formInstance.id)
        }
        else {
            render(view: "create", model: [formInstance: formInstance])
        }
    }

    def show = {
        def formInstance = Form.get(params.id)
        if (!formInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'form.label', default: 'Form'), params.id])}"
            redirect(action: "list")
        }
        else {
            [formInstance: formInstance]
        }
    }

    def edit = {
        def formInstance = Form.get(params.id)
        if (!formInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'form.label', default: 'Form'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [formInstance: formInstance]
        }
    }

    def update = {
        def formInstance = Form.get(params.id)
        if (formInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (formInstance.version > version) {
                    
                    formInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'form.label', default: 'Form')] as Object[], "Another user has updated this Form while you were editing")
                    render(view: "edit", model: [formInstance: formInstance])
                    return
                }
            }
            formInstance.properties = params
            if (!formInstance.hasErrors() && formInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'form.label', default: 'Form'), formInstance.id])}"
                redirect(action: "show", id: formInstance.id)
            }
            else {
                render(view: "edit", model: [formInstance: formInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'form.label', default: 'Form'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def formInstance = Form.get(params.id)
        if (formInstance) {
            try {
                formInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'form.label', default: 'Form'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'form.label', default: 'Form'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'form.label', default: 'Form'), params.id])}"
            redirect(action: "list")
        }
    }
}
