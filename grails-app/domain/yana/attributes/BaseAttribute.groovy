package yana.attributes

class BaseAttribute {
    String name
    String value
    String dataType

    static constraints = {
        name(blank:false)
        value(blank:true)
        dataType(inList: ['string']);// ['string','url',...]
    }

    def String toString() {
        return name
    }

}
