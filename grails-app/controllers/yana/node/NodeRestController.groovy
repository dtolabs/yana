package yana.node

import groovy.xml.MarkupBuilder

import grails.converters.XML
import grails.converters.JSON

import yana.XmlParserUtil
import yana.attributes.*

class NodeRestController {
    //
    // List the nodes. Url: GET /api/nodes
    //
    def list = { 
        def list = Node.list()
        println "DEBUG: params.format="+params.format
        switch (params.format) {
            case "json":
                render list as JSON 
                break
            case "rundeck-xml":
                render generateRundeckXml(list)
                break;
            default:
                render list as XML  
        }
    }

    //
    // Generates a Rundeck XML formatted view of the Nodes
    //
    def generateRundeckXml(list) {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.project() {
            list.each{ Node nodeInstance->
                node( name:nodeInstance.name, 
                      description:nodeInstance.description,
                      osName:nodeInstance.osName,
                      hostname:nodeInstance.hostname,
                      osFamily:nodeInstance.osFamily,
                      tags: nodeInstance.tagsString(",")
                      ) {
                    nodeInstance.attributes.each{ Attribute attr->
                        attribute(name:attr.name, value:attr.value)
                    }
                    nodeInstance.externalAttributes.each{ Attributes attrs->
                        attrs.external.each{ ExternalAttribute eattr->
                            // prefix with Attributes' name
                            attribute(name:attrs.name+"."+eattr.name, 
                                      value:eattr.value)
                        }
                    }
                    
                }
            }
        }
        return writer.toString()
    }

    //
    // Get the node. Url: GET /api/nodes/{id}
    //
    def show = {
        Node obj = Node.get(params.id)
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

    //
    // Add a node. Url: POST /api/nodes
    //
    def add = {
        println "DEBUG: HEY, inside method: nodeRest#add ..."

        def parser = new XmlParser()
        def xmlnode = parser.parse(request.getInputStream())
        def nodeMap = XmlParserUtil.toObject(xmlnode)
        println "DEBUG: XmlParserUtil.toObject(xmlnode)= "+ nodeMap

        if (Node.exists(nodeMap.id)) {
            // Forbidden. The node already exists. 
            render status:403, contentType:"text/xml", encoding:"utf-8", {
                errors {
		    error {
                        message("Node already exists")
		        references {
		            node(id:nodeMap.id)
		        }
		    }
                }
            }
        } else {
            // Create a new node instance
            def nodeInstance = Node.fromMap(nodeMap)
            nodeInstance.save(flush:true)

            if (nodeInstance && !nodeInstance.hasErrors()) {
                render status:201, contentType:"text/xml", encoding:"utf-8", {
                    results {
                        result {
			    message("Created new node")
                            references {
                                node(id:nodeInstance.id)
                            }
			}
                    }
                }
            } else {
                // Bad request.
                render status:400, contentType:"text/xml", encoding:"utf-8", {
                    errors {
                        nodeInstance?.errors?.fieldErrors?.each { err ->
                            error {
                                field(err.field)
                                message(g.message(error: err))
                            }
                        }
                    }
                }
            }
        }
    }

    //
    // Save a node. Url: POST /api/nodes/{id}
    //
    def save = {
        println "DEBUG: HEY, inside method: nodeRest#save ..."


        def nodeInstance = Node.get(params.id)
        if (nodeInstance) {
            // Save the node updates

            println "DEBUG: params['node']="+params['node']

            nodeInstance.properties = params['node']
            if (!nodeInstance.hasErrors() && nodeInstance.save(flush:true)) {
                render status:200,  contentType:"text/xml", encoding:"utf-8", {
                    results {
                        result {
			    message("saved node data")
                        }
                    }
                }
           } else {
                render status:403, contentType:"text/xml", encoding:"utf-8", {
                    errors {
                        nodeInstance?.errors?.fieldErrors?.each { err ->
                            error {
                                field(err.field)
                                message(g.message(error: err))
                            }
                        }
                    }
                }
           }          
        } else {
            // Not Found.
            render status:404, contentType:"text/xml", encoding:"utf-8", {
                errors {
                    error {
                        message("Node not found")
                    	references {
		            node(id:params.id)
		        }
                    }
                }
            }
        }

        if (nodeInstance && !nodeInstance.hasErrors()) {
            render status:202, contentType: "text/xml", encoding: "utf-8", {
                results {
                    result {
                        message("Node info saved")
                    }
                }
            }
        } else {
            // Bad request.
            render status:400, contentType:"text/xml", encoding:"utf-8", {
                errors {
                    nodeInstance?.errors?.fieldErrors?.each { err ->
                        error {
                            field(err.field)
                            message(g.message(error: err))
                        }
                    }
                }
            }
        }
    }

    //
    // Delete a node. Url: DELETE /api/nodes/{id}
    //
    def delete = {
        println "DEBUG: inside method: nodeRest#delete"
        println "DEBUG: checking for id: " + params.id
        def nodeInstance = Node.get(params.id)
        if (nodeInstance) {
            nodeInstance.delete(flush: true)
            // Ok.
            render status:200, contentType:"text/xml", encoding:"utf-8", {
                results {
                    result {
		        message("Node removed")
                        references {
                            node(id:params.id)
                        }
		    }
                }
            }
        } else {
            // Accepted.
            render status:202,  contentType:"text/xml", encoding:"utf-8", {
                results {
                    result {
		        message("Node not found")
                        references {
                            node(id:params.id)
                        }
		    }
                }
            }
        }
    }


}
