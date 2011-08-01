package yana.attributes

class ExternalAttributeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [externalAttributeInstanceList: ExternalAttribute.list(params), externalAttributeInstanceTotal: ExternalAttribute.count()]
    }

    def create = {
        def externalAttributeInstance = new ExternalAttribute()
        externalAttributeInstance.properties = params
        return [externalAttributeInstance: externalAttributeInstance]
    }

    def save = {
        def externalAttributeInstance = new ExternalAttribute(params)
        if (externalAttributeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), externalAttributeInstance.id])}"
            redirect(action: "show", id: externalAttributeInstance.id)
        }
        else {
            render(view: "create", model: [externalAttributeInstance: externalAttributeInstance])
        }
    }

    def show = {
        def externalAttributeInstance = ExternalAttribute.get(params.id)
        if (!externalAttributeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), params.id])}"
            redirect(action: "list")
        }
        else {
            [externalAttributeInstance: externalAttributeInstance]
        }
    }

    def edit = {
        def externalAttributeInstance = ExternalAttribute.get(params.id)
        if (!externalAttributeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [externalAttributeInstance: externalAttributeInstance]
        }
    }

    def update = {
        def externalAttributeInstance = ExternalAttribute.get(params.id)
        if (externalAttributeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (externalAttributeInstance.version > version) {
                    
                    externalAttributeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'externalAttribute.label', default: 'ExternalAttribute')] as Object[], "Another user has updated this ExternalAttribute while you were editing")
                    render(view: "edit", model: [externalAttributeInstance: externalAttributeInstance])
                    return
                }
            }
            externalAttributeInstance.properties = params
            if (!externalAttributeInstance.hasErrors() && externalAttributeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), externalAttributeInstance.id])}"
                redirect(action: "show", id: externalAttributeInstance.id)
            }
            else {
                render(view: "edit", model: [externalAttributeInstance: externalAttributeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def externalAttributeInstance = ExternalAttribute.get(params.id)
        if (externalAttributeInstance) {
            try {
                externalAttributeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'externalAttribute.label', default: 'ExternalAttribute'), params.id])}"
            redirect(action: "list")
        }
    }
}
