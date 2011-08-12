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
      
        def result = this.controller.list()
        assertNotNull("result was null", result)
        def list = new XmlSlurper().parseText(this.controller.response.contentAsString)
        def allNodes = list.node
        assertEquals "wrong node list size", 1, list.size()
        def firstNode = list.node[0]
        assertEquals("incorrect tag name", "node", firstNode.name())
        assertEquals("incorrect value for name ", "node1", firstNode."name".text())
        assertEquals("incorrect value for osFamily", "unix", firstNode.osFamily.text())

    }


    void testListByTag() {

        Node node1 = new Node(name: 'node1', osFamily:'unix') 
        Node node2 = new Node(name: 'node2', osFamily:'unix') 
        Node node3 = new Node(name: 'node3', osFamily:'unix') 
        Node node4 = new Node(name: 'node4', osFamily:'unix') 

        node1.addToTags(new Tag(name: 'roleA'))
        node2.addToTags(new Tag(name: 'roleA'))
        node3.addToTags(new Tag(name: 'roleB'))
        node4.addToTags(new Tag(name: 'roleC'))
        node1.save(); node2.save(); node3.save(); node4.save();

        this.controller.params.tags = "roleA"
        def content = this.controller.list()
        assertNotNull("content was null.", content)
        def root = new XmlSlurper().parseText(this.controller.response.contentAsString)

        def allNodes = root.node
        assertEquals "wrong node list size.", 2, allNodes.size()

        def nodeNames = root.node.list().sort{ it.name }.'name'*.text()
        assertTrue "nodes did not include node1.", nodeNames.contains("node1")
        assertEquals "roleA", root.node[0].tags.tag.name.text()
        assertEquals "roleA", root.node[1].tags.tag.name.text()
    }

   void testShow() {

        mockDomain(Node, [
                       new Node(name: "testShow", osFamily: "unix")
                   ])
        this.controller.params.id = 1
        def result = this.controller.show()
        assertNotNull "result was null", (result)
        def node = new XmlSlurper().parseText(this.controller.response.contentAsString)

        assertEquals("incorrect value for name", "testShow", node.name.text())
        assertEquals("incorrect value for osFamily", "unix", node.osFamily.text())

    }

   void testDelete() {

        mockDomain(Node, [
                       new Node(name: "testDelete", osFamily: "unix")
                   ])
        this.controller.params.id = 1
        def content = this.controller.delete()
        assertNotNull "result was null", (content)
        def results = new XmlSlurper().parseText(this.controller.response.contentAsString)
        def result = results.result[0]
        assertEquals("incorrect value for result", 
                     "Node removed", result.text())
        assertEquals("incorrect value for node id",
                     "1", result.references.node."@id".text())

    }

    void testListTags() {


        Node node1 = new Node(name: 'node1', osFamily:'unix') 
        Tag tag1 =  new Tag(name: 'roleA') 

        mockDomain(Node, [node1])
        mockDomain(Tag, [tag1])
        node1.addToTags(tag1)
        node1.save()
        assertNotNull "instance did not validate", node1.save()
        this.controller.params.id = 1        
        def result = this.controller.listTags()
        assertNotNull("result was null", result)
        assertNotNull "Null content in response", this.controller.response.contentAsString
        assertTrue "empty content in response", "" != this.controller.response.contentAsString
        def root = new XmlSlurper().parseText(this.controller.response.contentAsString)


        println "TEST: contentAsString:"+this.controller.response.contentAsString
        def allTags = root.tag
        assertEquals "wrong tag list size", 1, allTags.size()
        def firstTag = root.tag[0]
        assertEquals "incorrect value for name", "roleA", firstTag."name".text()
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
