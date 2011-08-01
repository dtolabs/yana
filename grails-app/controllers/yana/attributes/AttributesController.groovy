package yana.attributes

class AttributesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [attributesInstanceList: Attributes.list(params), attributesInstanceTotal: Attributes.count()]
    }

    def create = {
        def attributesInstance = new Attributes()
        attributesInstance.properties = params
        return [attributesInstance: attributesInstance]
    }

    def save = {
        def attributesInstance = new Attributes(params)
        if (attributesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'attributes.label', default: 'Attributes'), attributesInstance.id])}"
            redirect(action: "show", id: attributesInstance.id)
        }
        else {
            render(view: "create", model: [attributesInstance: attributesInstance])
        }
    }

    def show = {
        def attributesInstance = Attributes.get(params.id)
        if (!attributesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'attributes.label', default: 'Attributes'), params.id])}"
            redirect(action: "list")
        }
        else {
            [attributesInstance: attributesInstance]
        }
    }

    def edit = {
        def attributesInstance = Attributes.get(params.id)
        if (!attributesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'attributes.label', default: 'Attributes'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [attributesInstance: attributesInstance]
        }
    }

    def update = {
        def attributesInstance = Attributes.get(params.id)
        if (attributesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (attributesInstance.version > version) {
                    
                    attributesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'attributes.label', default: 'Attributes')] as Object[], "Another user has updated this Attributes while you were editing")
                    render(view: "edit", model: [attributesInstance: attributesInstance])
                    return
                }
            }
            attributesInstance.properties = params
            if (!attributesInstance.hasErrors() && attributesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'attributes.label', default: 'Attributes'), attributesInstance.id])}"
                redirect(action: "show", id: attributesInstance.id)
            }
            else {
                render(view: "edit", model: [attributesInstance: attributesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'attributes.label', default: 'Attributes'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def attributesInstance = Attributes.get(params.id)
        if (attributesInstance) {
            try {
                attributesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'attributes.label', default: 'Attributes'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'attributes.label', default: 'Attributes'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'attributes.label', default: 'Attributes'), params.id])}"
            redirect(action: "list")
        }
    }
}
