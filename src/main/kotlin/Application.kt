import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.client.request.forms.formData
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ShutDownUrl
import main.kotlin.DatabaseFactory
import main.kotlin.api.auth
import main.kotlin.auth.AuthService
import main.kotlin.jwt.JwtConfig
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)


@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> call.request.path().startsWith("/") }
    }

    DatabaseFactory.init()
    val authService = AuthService()

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }
    install(ShutDownUrl.ApplicationCallFeature) {
        // The URL that will be intercepted
        shutDownUrl = "/shutdown"
        // A function that will be executed to get the exit code of the process
        exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
    }

    install(Authentication) {
        jwt("jwt") {
            verifier(JwtConfig.verifier)
            realm = "ktor.io"
            validate {
                if (it.payload.issuer.contains(realm)) JWTPrincipal(it.payload) else null
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            excludeFieldsWithoutExposeAnnotation()
        }
        formData {

        }
    }



    routing {
        route("/api") {
            auth(authService)

            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }

            install(StatusPages) {
                exception<InvalidCredentialsException> { exception ->
                    call.respond(HttpStatusCode.Unauthorized, mapOf("OK" to false, "error" to (exception.message
                            ?: "")))
                }
                exception<AuthenticationException> { cause ->
                    call.respond(HttpStatusCode.Unauthorized)
                }
                exception<AuthorizationException> { cause ->
                    call.respond(HttpStatusCode.Forbidden)
                }

            }
        }
    }
}

class AuthenticationException : RuntimeException()

class AuthorizationException : RuntimeException()

class InvalidCredentialsException(message: String) : RuntimeException(message)