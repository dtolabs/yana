package yana

import grails.test.*
import yana.node.Node
import yana.node.Tag

class NodeIntegrationTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp();
    }

    protected void tearDown() {
        super.tearDown();
    }

    void testFirstSaveEver() {

        def nodeInstance = new Node(name: 'node1', osFamily: 'unix')
        assertNotNull("node instance did not validate", nodeInstance.save())

        def StringBuffer sb = new StringBuffer()
        nodeInstance.errors.allErrors.each {sb<<it.toString()}
        assertNotNull("nodeInstance.id was null", nodeInstance.id)

        def foundNode = Node.get(nodeInstance.id)
        assertEquals 'node1', foundNode.name

    }

    void testTagsString() {
        def nodeInstance = new Node(name: 'node1', osFamily: 'unix')

        nodeInstance.addToTags(new Tag(name: 'app'))
        nodeInstance.addToTags(new Tag(name: 'web'))

        assertEquals "app,web", nodeInstance.tagsString(",")
    }

    void testFindAllTagsByName() {
        def node1 = new Node(name: 'node1', osFamily: 'unix')
        def node2 = new Node(name: 'node2', osFamily: 'unix')
        def node3 = new Node(name: 'node3', osFamily: 'unix')
        node1.addToTags(new Tag(name: 'web'))
        node2.addToTags(new Tag(name: 'web'))
        node3.addToTags(new Tag(name: 'app'))
        node1.save(); node2.save(); node3.save();

        def list = Node.findAllTagsByName("web")
        assertEquals "incorrect result size", 2, list.size()
        
    }

    void testFindAllByNameAndTagsByName() {
        def web1 = new Node(name: 'web1', osFamily: 'unix')
        def web2 = new Node(name: 'web2', osFamily: 'unix')
        def app1 = new Node(name: 'app1', osFamily: 'unix')
        web1.addToTags(new Tag(name: 'web'))
        web2.addToTags(new Tag(name: 'web'))
        app1.addToTags(new Tag(name: 'app'))
        web1.save(); web2.save(); app1.save();

        def list = Node.findAllByNameLikeAndTagsByName("web%","web")
        println "TEST: list="+list
        assertEquals "incorrect result size", 2, list.size()
        assertTrue "tagged node not found: web1.", list.contains(web1)
        assertTrue "tagged node not found: web2.", list.contains(web2)
    }


    void testFindTagByName() {
        def web1 = new Node(name: 'web1', osFamily: 'unix')
        def web2 = new Node(name: 'web2', osFamily: 'unix')
        def app1 = new Node(name: 'app1', osFamily: 'unix')
        web1.addToTags(new Tag(name: 'web'))
        web1.addToTags(new Tag(name: 'app'))
        web2.addToTags(new Tag(name: 'web'))
        app1.addToTags(new Tag(name: 'app'))
        web1.save(); web2.save(); app1.save();

        def found = web1.findTagByName("web")
        println "TEST: found="+found
        assertEquals "incorrect result size", 1, found.size()
        def Tag tag = found.asList()[0]
        assertEquals "tag name did not match", "web", tag.name
    }
}
