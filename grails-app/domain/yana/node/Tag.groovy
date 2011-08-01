package yana.node

class Tag {
    String name

    static constraints = {
        name(blank:false)
    }

    static belongsTo = [ node : Node ]

    def String toString() {
        return name
    }

   def static fromMap(Map map) {
       println "DEBUG: inside Tag#fromMap: map="+map
        def tagInstance
        // check if this tag exists
        if (Tag.exists(map.id)) { 
            tagInstance= Tag.get(map.id)
        } else {
            // create a new one
            tagInstance = new Tag()            
        }
        // populate the Tag properties from the map
        tagInstance.properties = map
 
        return tagInstance
    }
}
