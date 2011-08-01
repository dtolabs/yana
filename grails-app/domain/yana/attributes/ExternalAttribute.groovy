package yana.attributes

class ExternalAttribute extends BaseAttribute {
    static belongsTo = [ attributes:Attributes ]

   def static fromMap(Map map) {
        println "DEBUG: inside ExternalAttribute#fromMap: map="+map
        def attrInstance
        // check if this attr exists
        if (ExternalAttribute.exists(map.id)) { 
            attrInstance= ExternalAttribute.get(map.id)
        } else {
            // create a new one
            attrInstance = new ExternalAttribute()            
        }
        // populate the Attribute properties from the map
        attrInstance.properties = map
        
        return attrInstance
    }
}
