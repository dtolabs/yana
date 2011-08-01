package yana.node
import yana.attributes.BaseAttribute

class Attribute extends BaseAttribute {
    static belongsTo = [ node : Node ]


    def static fromMap(Map map) {
        println "DEBUG: inside Attr#fromMap: map="+map
        def attrInstance
        // check if this attr exists
        if (Attribute.exists(map.id)) { 
            attrInstance= Attribute.get(map.id)
        } else {
            // create a new one
            attrInstance = new Attribute()            
        }
        // populate the Attribute properties from the map
        attrInstance.properties = map
        
        return attrInstance
    }
}
