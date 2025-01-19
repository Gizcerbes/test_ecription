package com.uogames.sequre

import com.uogames.sequre.routing.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)


fun Application.module() {

    install(ContentNegotiation){ gson() }

    routing {

        get("/version") {
            call.respond("1.0")
        }

        RouteHmac256()
        RouteHmac384()
        RouteHmac512()
        RouteAES()

        jwtWell()
        hmac()

    }

}