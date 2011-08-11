package yana.node

import groovy.xml.MarkupBuilder

import grails.converters.XML
import grails.converters.JSON

import yana.node.Node

class TagRestController {


    //
    // Get the tag. Url: GET /api/nodes/tags/$name
    //
    def show = {
        println "DEBUG: inside show()..."
        def nodes = []
        if (params?.nodeName) {
            println "DEBUG: params.nodeName="+params.nodeName
            nodes = Node.findAllByLikeNameAndTagsByName(params.nodeName, 
                                                        params.name)
        } else {
            nodes = Node.findAllTagsByName(params.name)
        }

        if (nodes.size() >0) {
            println "DEBUG: there are some nodes"
            render renderTag(params.name,nodes)
        } else {
            println "DEBUG: no nodes were found matching the tag"
            response.sendError(404)
        }
    }

    //
    // Helper method to generate tag xml
    //
    static def renderTag(tagName, nodeList) {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.tag() {
            name "${tagName}"
            nodes(count: nodeList.size()) {
                nodeList.each{ Node nodeInstance->
                    node(id: nodeInstance.id) {
                        name(nodeInstance.name)
                    }
                }
            }
        }
        writer.toString()
    }

    //
    // Save a tag to matching Nodes
    //
    def save = {
        println "DEBUG: inside save..."
        println "DEBUG: looking for nodes like: "+params?.nodeName
        def nodes = Node.findAllByNameLike(params?.nodeName)
        if (nodes.size()>0) {
            println "DEBUG: number nodes found: "+nodes.size()
            nodes.each{Node nodeInstance->
                println "DEBUG: addToTags for node: " +nodeInstance
                nodeInstance.addToTags(new Tag(name: params.name))
                nodeInstance.save()
            }
            render renderTag(params.name,nodes)
        } else {
            println "DEBUG: no nodes were found matching: ${params.nodeName}"
            response.sendError(404)
        }

    }

    // 
    // Remove a tag from matching Nodes
    //
    def remove = {
        println "DEBUG: inside remove..."
        println "DEBUG: looking for nodes like: "+params?.nodeName
        // validate the parameter, nodeName 
        if (params?.nodeName) {

        }
        def foundNodes = Node.findAllByNameLikeAndTagsByName(params?.nodeName, 
                                                        params.name)
        println "DEBUG: foundNodes.size()=" + foundNodes.size()

        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        
        xml.tag() {
            name "${params.name}"
            nodes(count: foundNodes.size()) {
                foundNodes.each{Node nodeInstance->
                    def tags = nodeInstance.findTagByName(params.name)
                    tags.each {Tag tagInstance->
                        println "DEBUG: removing Tag: ${tagInstance} from node: " +nodeInstance+"..."
                        try {
                            nodeInstance.removeFromTags(tagInstance)
                            nodeInstance.save()
                            println "DEBUG: Tag removed from node: " + nodeInstance
                            node(id: nodeInstance.id) {
                                name "${nodeInstance.name}"
                            }
                        } catch (Exception e) {
                            println "DEBUB: Caught an error removing tag: "+ e
                            println "DEBUG: e instanceof "+ e.getClass().getName()
                        }
                    }
                }
            }
        }
        render writer.toString()
    }
}
