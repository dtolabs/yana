package yana.node

import yana.attributes.Attributes

class Node {

    static searchable = true

    String name
    String description
    String osName
    String osFamily

    String hostname
    // Date dateCreated
    // Date dateModified

    static constraints = {
        name(unique:true, blank:false)
        description(blank:true, nullable:true)
        hostname(blank:true, nullable:true)
        osName(blank:true, nullable:true)
        osFamily(blank:true, inList:['unix','windows'])
        tags(nullable:true)
    }

    static hasMany = [ 
        tags : Tag ,
        attributes : yana.node.Attribute , 
        externalAttributes : Attributes
    ]


    def String toString() {
        return name
    }

    def static fromMap(Map map) {
        def nodeInstance
        // check if this node exists
        if (map?.id && Node.exists(map.id)) { 
            nodeInstance= Node.get(map.id)
        } else {
            // create a new one
            nodeInstance = new Node()            
        }
        // populate the Node properties from the map
        nodeInstance.name        = map['name']
        nodeInstance.description = map['description']
        nodeInstance.hostname = map['hostname']
        nodeInstance.osName      = map['osName']
        nodeInstance.osFamily    = map['osFamily']

        // Add tags
        if (map?.tags instanceof Map
              && map.tags.containsKey('tag')) {
           if (map.tags.tag instanceof List) {
                 map.tags.tag.each {
                   def tagInstance = Tag.fromMap(it)
                   tagInstance.save(flush:false)
                   nodeInstance.addToTags(tagInstance)
                 }
           } else if (map.tags.tag instanceof Map) {
               def tagInstance = Tag.fromMap(map.tags.tag)
               tagInstance.save(flush:false)
               nodeInstance.addToTags(tagInstance)                    
           }
        }

        // Add attributes
        if (map?.attributes instanceof Map
              && map.attributes.containsKey('attribute')) {
           if (map.attributes.attribute instanceof List) {
                 map.attributes.attribute.each {
                   def attrInstance = Attribute.fromMap(it)
                   attrInstance.save(flush:false)
                   nodeInstance.addToAttributes(attrInstance)
                 }
           } else if (map.attributes.attribute instanceof Map) {
               def attrInstance = Attribute.fromMap(map.attributes.attribute)
               attrInstance.save(flush:false)
               nodeInstance.addToAttributes(attrInstance)

           }
        }

        // Add externalAttributes
        if (map?.externalAttributes instanceof Map
              && map.externalAttributes.containsKey('attributes')) {
           if (map.externalAttributes.attributes instanceof List) {
                 map.externalAttributes.attributes.each {
                   def attrsInstance = Attributes.fromMap(it)
                   attrsInstance.save(flush:false)
                   nodeInstance.addToExternalAttributes(attrsInstance)
                 }
           } else if (map.externalAttributes.attributes instanceof Map) {
               def attrsInstance = Attributes.fromMap(map.externalAttributes.attributes)
               attrsInstance.save(flush:false)
               nodeInstance.addToExternalAttributes(attrsInstance)                    
           }
        }

        return nodeInstance
    }

   // Generate tags as a delimited string
   def tagsString(delimiter) {
       def list = []
       this.tags.each {tag->
           list << tag.name
       }
       return list.sort().join(delimiter)
   }

   // A dynamic (like?) find method
   static Set<Node> findAllTagsByName(String name)  {
       println "DEBUG: inside findAllTagsByName. name="+name
       return Node.withCriteria {
            tags {
                eq('name',name)
            }
       }
   }

   // A dynamic (like?) find method
   static Set<Node> findAllByNameLikeAndTagsByName(String nameLike, String tagName)  {
       println "DEBUG: inside findAllByNameLikeAndTagsByName. name="+tagName
       return Node.withCriteria {
            ilike('name',nameLike)
            tags {
                eq('name',tagName)
            }
       }
   }

   def Set<Tag> findTagByName(String tagName) {
     println "DEBUG: inside findTagByName. name="+tagName
     return Tag.withCriteria {
        eq('name',tagName)
        node() {
          eq('id',this.id)
        }
     }
   }
}

