package edu.umn.ncs.phone

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_IT'])
class NonParticipationReasonController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [nonParticipationReasonInstanceList: NonParticipationReason.list(params), nonParticipationReasonInstanceTotal: NonParticipationReason.count()]
    }

    def create = {
        def nonParticipationReasonInstance = new NonParticipationReason()
        nonParticipationReasonInstance.properties = params
        return [nonParticipationReasonInstance: nonParticipationReasonInstance]
    }

    def save = {
        def nonParticipationReasonInstance = new NonParticipationReason(params)
        if (nonParticipationReasonInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), nonParticipationReasonInstance.id])}"
            redirect(action: "show", id: nonParticipationReasonInstance.id)
        }
        else {
            render(view: "create", model: [nonParticipationReasonInstance: nonParticipationReasonInstance])
        }
    }

    def show = {
        def nonParticipationReasonInstance = NonParticipationReason.get(params.id)
        if (!nonParticipationReasonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), params.id])}"
            redirect(action: "list")
        }
        else {
            [nonParticipationReasonInstance: nonParticipationReasonInstance]
        }
    }

    def edit = {
        def nonParticipationReasonInstance = NonParticipationReason.get(params.id)
        if (!nonParticipationReasonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [nonParticipationReasonInstance: nonParticipationReasonInstance]
        }
    }

    def update = {
        def nonParticipationReasonInstance = NonParticipationReason.get(params.id)
        if (nonParticipationReasonInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (nonParticipationReasonInstance.version > version) {
                    
                    nonParticipationReasonInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason')] as Object[], "Another user has updated this NonParticipationReason while you were editing")
                    render(view: "edit", model: [nonParticipationReasonInstance: nonParticipationReasonInstance])
                    return
                }
            }
            nonParticipationReasonInstance.properties = params
            if (!nonParticipationReasonInstance.hasErrors() && nonParticipationReasonInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), nonParticipationReasonInstance.id])}"
                redirect(action: "show", id: nonParticipationReasonInstance.id)
            }
            else {
                render(view: "edit", model: [nonParticipationReasonInstance: nonParticipationReasonInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def nonParticipationReasonInstance = NonParticipationReason.get(params.id)
        if (nonParticipationReasonInstance) {
            try {
                nonParticipationReasonInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'nonParticipationReason.label', default: 'NonParticipationReason'), params.id])}"
            redirect(action: "list")
        }
    }
}
