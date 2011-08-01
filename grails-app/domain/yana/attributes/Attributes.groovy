package yana.attributes

class Attributes {

    static searchable = true

    String name

    static constraints = {
        name(unique:true, blank:false)
    }

    static hasMany = [ external : ExternalAttribute ]

    def String toString() {
        return name
    }

    def static fromMap(Map map) {
        println "DEBUG: Attributes#fromMap, map: "+map
        def attrsInstance
        // check if this node exists
        if (map?.id && Attributes.exists(map.id)) { 
            attrsInstance= Attributes.get(map.id)
        } else {
            // create a new one
            attrsInstance = new Attributes()            
        }
        // populate the Attributes properties from the map
        attrsInstance.name = map['name']

        // Add attributes
        if (map?.external instanceof Map
              && map.external.containsKey('externalAttribute')) {
           if (map.external.externalAttribute instanceof List) {
                 map.external.externalAttribute.each {
                   attrsInstance.addToExternal(ExternalAttribute.fromMap(it))
                 }
           } else if (map.external.externalAttribute instanceof Map) {
                 attrsInstance.addToExternal(
                    ExternalAttribute.fromMap(map.external.externalAttribute))
           }
        }
        return attrsInstance
    }
}
