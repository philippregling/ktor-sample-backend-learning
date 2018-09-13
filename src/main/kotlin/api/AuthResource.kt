package main.kotlin.api

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import main.kotlin.auth.AuthService
import org.slf4j.LoggerFactory


fun Route.auth(authService: AuthService) {

    val logger = LoggerFactory.getLogger("AuthLogger")

    route("/auth") {
        post("/register") {
            call.respond(HttpStatusCode.Created)
        }

        post("/login") {


        }

        authenticate {
            get("/auth") {

            }
        }

//        get("/") {
//            call.respond(widgetService.getAllWidgets())
//        }
//
//        get("/{id}") {
//            val widget = widgetService.getWidget(call.parameters["id"]?.toInt()!!)
//            if (widget == null) call.respond(HttpStatusCode.NotFound)
//            else call.respond(widget)
//        }
//
//        post("/") {
//            val widget = call.receive<NewWidget>()
//            call.respond(HttpStatusCode.Created, widgetService.addWidget(widget))
//        }
//
//        put("/") {
//            val widget = call.receive<NewWidget>()
//            val updated = widgetService.updateWidget(widget)
//            if(updated == null) call.respond(HttpStatusCode.NotFound)
//            else call.respond(HttpStatusCode.OK, updated)
//        }
//
//        delete("/{id}") {
//            val removed = widgetService.deleteWidget(call.parameters["id"]?.toInt()!!)
//            if (removed) call.respond(HttpStatusCode.OK)
//            else call.respond(HttpStatusCode.NotFound)
//        }

    }

}