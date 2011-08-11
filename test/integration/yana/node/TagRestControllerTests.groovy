package yana.node

import grails.test.*

class TagRestControllerTests extends ControllerUnitTestCase {


    protected void setUp() {
        super.setUp()

    }

    protected void tearDown() {
        super.tearDown()
    }

    void testRenderTag() {
        Node node1 = new Node(name: 'node1', osFamily:'unix') 
        Node node3 = new Node(name: 'node3', osFamily:'unix') 
        node1.addToTags(new Tag(name: 'roleX'))
        node3.addToTags(new Tag(name: 'roleX'))
        def list = [node1,node3]

        def output = this.controller.renderTag("roleX",list)
        println "TEST: output="+output
        def root = new XmlSlurper().parseText(output)
        assertEquals("incorrect value for name.", "roleX", root.name.text())
        assertEquals("incorrect count.", "2", root.nodes."@count".text())

        def nodes = root.nodes.node.findAll{ it.name =~ 'node.*' }.collect{ it.text() }
        assertTrue "tagged node not found.", nodes.contains("node1")
        assertTrue "tagged node not found.", nodes.contains("node3")
    }

    void testShow() {
        Node node1 = new Node(name: 'node1', osFamily:'unix') 
        Node node2 = new Node(name: 'node2', osFamily:'unix') 
        Node node3 = new Node(name: 'node3', osFamily:'unix') 
        node1.addToTags(new Tag(name: 'roleX'))
        node2.addToTags(new Tag(name: 'roleA'))
        node3.addToTags(new Tag(name: 'roleX'))
        node1.save(); node2.save(); node3.save();

        this.controller.params.name = "roleX"
        def result = this.controller.show()
        assertNotNull "result was null.", result

        def root = new XmlSlurper().parseText(this.controller.response.contentAsString)
        assertEquals("incorrect value for name.", "roleX", root.name.text())
        assertEquals("incorrect count.", "2", root.nodes."@count".text())

        def nodes = root.nodes.node.findAll{ it.name =~ 'node.*' }.collect{ it.text() }
        assertTrue "tagged node not found.", nodes.contains("node1")
        assertTrue "tagged node not found.", nodes.contains("node3")
        assertTrue "untagged node was found.", !nodes.contains('node2')
    }


    void testSave() {
        Node node1 = new Node(name: 'node1', osFamily:'unix') 
        Node node2 = new Node(name: 'other', osFamily:'unix') 
        Node node3 = new Node(name: 'node3', osFamily:'unix') 
        node2.addToTags(new Tag(name: 'roleA'))
        node1.save(); node2.save(); node3.save();

        this.controller.params.name = "roleX"
        this.controller.params.nodeName = "node%"
        def result = this.controller.save()
        assertNotNull "result was null.", result

        def root = new XmlSlurper().parseText(this.controller.response.contentAsString)
        println "TEST: this.controller.response.contentAsString:"+this.controller.response.contentAsString
        assertEquals("incorrect value for name.", "roleX", root.name.text())
        assertEquals("incorrect count.", "2", root.nodes."@count".text())

        def nodes = root.nodes.node.findAll{ it.name =~ 'node.*' }.collect{ it.text() }
        assertTrue "tagged node not found.", nodes.contains("node1")
        assertTrue "tagged node not found.", nodes.contains("node3")
        assertTrue "untagged node was found.", !nodes.contains('node2')
    }

    void testRemove() {
        Node node1 = new Node(name: 'node1', osFamily:'unix') 
        Node node2 = new Node(name: 'other', osFamily:'unix') 
        Node node3 = new Node(name: 'node3', osFamily:'unix') 
        node1.addToTags(new Tag(name: 'roleX'))
        node2.addToTags(new Tag(name: 'roleA'))
        node3.addToTags(new Tag(name: 'roleX'))
        node1.save(); node2.save(); node3.save();

        this.controller.params.name = "roleX"
        this.controller.params.nodeName = "node%"
        def result = this.controller.remove()
        assertNotNull "result was null.", result

        def root = new XmlSlurper().parseText(this.controller.response.contentAsString)
        println "TEST: this.controller.response.contentAsString:"+this.controller.response.contentAsString
        assertEquals("incorrect value for name.", "roleX", root.name.text())
        assertEquals("incorrect count.", "2", root.nodes."@count".text())

        def nodes = root.nodes.node.findAll{ it.name =~ 'node.*' }.collect{ it.text() }
        assertTrue "tagged node not found.", nodes.contains("node1")
        assertTrue "tagged node not found.", nodes.contains("node3")
        assertTrue "untagged node was found.", !nodes.contains('node2')
    }
}
