package main.kotlin.api

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.util.toMap
import main.kotlin.auth.AuthService
import main.kotlin.jwt.JwtConfig
import main.kotlin.model.Message
import main.kotlin.model.ServerError
import main.kotlin.model.auth.AuthUser
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.LoggerFactory


fun Route.auth(authService: AuthService) {

    val logger = LoggerFactory.getLogger("AuthLogger")

    route("/auth") {
        post("/register") { _ ->
            val fields = call.receiveParameters().toMap()
            val username = fields["username"]?.first()
            val password = fields["password"]?.first()
            val hashed = BCrypt.hashpw(password, BCrypt.gensalt())
            if (username != null && password != null) {
                val registeredUser = authService.addUser(username, hashed)
                registeredUser?.let {
                    call.respond(HttpStatusCode.Created, AuthUser(JwtConfig.makeToken(registeredUser), registeredUser))
                    return@post
                }
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }


        }

        post("/login") {
            val fields = call.receiveParameters().toMap()
            val username = fields["username"]?.first()
            val password = fields["password"]?.first()
            val matchingUser = authService.getUser(userName = username)
            if (matchingUser != null) {
                val hashMatches = BCrypt.checkpw(password, matchingUser.passwordHash)
                if (hashMatches) {
                    call.respond(HttpStatusCode.OK, AuthUser(JwtConfig.makeToken(matchingUser), matchingUser))
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, ServerError(1001, "User not found"))
            }

        }

        authenticate("jwt") {
            get("/auth") {
                call.respond(Message("VALID TOKEN"))
            }
        }

    }

}