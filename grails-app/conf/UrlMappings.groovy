
class UrlMappings {

    static mappings = {

        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

        "/login/$action?"(controller: "login")
        "/logout/$action?"(controller: "logout")

        "/api/nodes"(controller: "nodeRest",  parseRequest:true) {
            action = [ GET: "list" , POST: "add" ]
        }

        "/api/nodes/$id"(controller: "nodeRest",  parseRequest:true) {
            action = [ GET: "show" , POST: "save" , DELETE: "delete" ]
        }

        "/api/attributes"(controller: "attributesRest") {
            action = [ GET: "list" ]
        }

        "/api/attributes/$id"(controller: "attributesRest") {
            action = [ GET: "show" ]
        }


        "/"(view:"/index")
        "500"(view:'/error')
    }
}
