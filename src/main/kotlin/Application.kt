import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.client.request.forms.formData
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.websocket.WebSockets
import main.kotlin.api.auth
import main.kotlin.model.SimpleJWT
import main.kotlin.service.AuthService
import main.kotlin.service.DatabaseFactory

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(WebSockets)
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }

    DatabaseFactory.init()
    val authService = AuthService()


    val simpleJWT = SimpleJWT("my-super-secret-for-jwt")
    install(Authentication) {
        jwt {
            verifier(simpleJWT.verifier)
            validate {
                UserIdPrincipal(it.payload.getClaim("name").asString())
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
        formData {

        }
    }

    install(Routing) {
        auth(authService)
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = io.ktor.http.ContentType.Text.Plain)
        }
    }
//
//        install(StatusPages) {
//            exception<InvalidCredentialsException> { exception ->
//                call.respond(HttpStatusCode.Unauthorized, mapOf("OK" to false, "error" to (exception.message ?: "")))
//            }
//            exception<AuthenticationException> { cause ->
//                call.respond(HttpStatusCode.Unauthorized)
//            }
//            exception<AuthorizationException> { cause ->
//                call.respond(HttpStatusCode.Forbidden)
//            }
//
//        }
//
//
//        authenticate {
//            get("/protected/route/basic") {
//                val principal = call.principal<UserIdPrincipal>()!!
//                call.respondText("Hello ${principal.name}")
//            }
//        }
//
//        post("/login-register") {
//            val post = call.receive<LoginRegister>()
//            val user = users.getOrPut(post.user) { User(post.user, post.password) }
//            if (user.password != post.password) throw InvalidCredentialsException("Invalid credentials")
//            call.respond(mapOf("token" to simpleJWT.sign(user.name)))
//        }
//
//        route("/gson") {
//            authenticate {
//                get {
//                    call.respond(mapOf("snippets" to synchronized(snippets) { snippets.toList() }))
//                }
//            }
//            authenticate {
//                post {
//                    val post = call.receive<PostSnippet>()
//                    val principal = call.principal<UserIdPrincipal>() ?: error("No principal")
//                    snippets += Snippet(principal.name, post.snippet.text)
//                    call.respond(mapOf("OK" to true))
//                }
//            }
//        }
//
//        post("/form/data") {
//            val post = call.receiveText()
//            call.respond(post)
//        }
//    }
}


class AuthenticationException : RuntimeException()

class AuthorizationException : RuntimeException()

class InvalidCredentialsException(message: String) : RuntimeException(message)