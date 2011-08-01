package yana.attributes

import grails.converters.XML
import grails.converters.JSON

class AttributesRestController {
    def list = { 
        def list = Attributes.list()
        switch (params.format) {
            case "json":
                render list as JSON 
                break
            default:
                render list as XML  
        }
    }

    def show = {
        def obj = ExternalAttribute.get(params.id)
        if (obj) {
            switch (params.format) {
                case "json":
                    render obj as JSON 
                    break
                default:
                    render obj as XML  
            }
        } else {
            response.sendError(404)
        }
    }
}
