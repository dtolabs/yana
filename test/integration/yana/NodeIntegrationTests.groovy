package yana

import grails.test.*
import yana.node.Node

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

}
