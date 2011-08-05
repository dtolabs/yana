package yana.node

import grails.test.*

class NodeRestControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testList() {

        mockDomain(Node, [
                       new Node(name: "node1", osFamily: "unix")
                   ])
        def model = this.controller.list()
        assertNotNull("model was null", model)
        def list = new XmlSlurper().parseText(this.controller.response.contentAsString)
        def allNodes = list.node
        assertEquals "wrong node list size", 1, list.size()
        def firstNode = list.node[0]
        assertEquals("incorrect tag name", "node", firstNode.name())
        assertEquals("incorrect value for name ", "node1", firstNode."name".text())
        assertEquals("incorrect value for osFamily", "unix", firstNode.osFamily.text())

    }

   void testShow() {

        mockDomain(Node, [
                       new Node(name: "testShow", osFamily: "unix")
                   ])
        this.controller.params.id = 1
        def model = this.controller.show()
        assertNotNull "model was null", (model)
        def node = new XmlSlurper().parseText(this.controller.response.contentAsString)

        assertEquals("incorrect value for name", "testShow", node.name.text())
        assertEquals("incorrect value for osFamily", "unix", node.osFamily.text())

    }

   void testDelete() {

        mockDomain(Node, [
                       new Node(name: "testDelete", osFamily: "unix")
                   ])
        this.controller.params.id = 1
        def model = this.controller.delete()
        assertNotNull "model was null", (model)
        def results = new XmlSlurper().parseText(this.controller.response.contentAsString)
        def result = results.result[0]
        assertEquals("incorrect value for result", 
                     "Node removed. id: 1", result.text())

    }


    void testRundeckXml() {

        mockDomain(Node, [
                       new Node(name: "node1", osFamily: "unix")
                   ])
        def list = Node.list()
        assertNotNull list
        assertEquals 1, list.size()
        def result = this.controller.render(this.controller.generateRundeckXml(list))
        assertNotNull("result was null", result)
        assertNotNull "Null content in response", this.controller.response.contentAsString
        assertTrue "empty content in response", "" != this.controller.response.contentAsString
        def root = new XmlSlurper().parseText(this.controller.response.contentAsString)

        assertEquals "project", root.name()
        def allNodes = root.node
        assertEquals "wrong node list size", 1, list.size()
        def firstNode = root.node[0]
        assertEquals("incorrect tag name", "node", firstNode.name())
        assertEquals("incorrect value for name", "node1", firstNode."@name".text())
        assertEquals("incorrect value for osFamily", "unix", firstNode."@osFamily".text())
    }
}
