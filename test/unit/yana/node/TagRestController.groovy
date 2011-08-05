package yana.node

import grails.test.*

class TagRestControllerTests extends ControllerUnitTestCase {

    protected void setUp() {
        super.setUp()

    }

    protected void tearDown() {
        super.tearDown()
    }

    void testList() {
        def nodeInstance = new Node(name: 'node1')
        def tag1 = new Tag(name: 'tag1')
        nodeInstance.addToTags(tag1)
        tagInstance.save(flush:true)

        def result = this.controller.list()

        println "TEST: result.size(): " + result.size()
    }
}
