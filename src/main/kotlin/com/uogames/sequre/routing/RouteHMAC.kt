package com.uogames.sequre.routing

import com.auth0.jwt.JWT
import com.uogames.sequre.token.hmac.JWTHMACBuilder
import com.uogames.sequre.token.hmac.JWTHMACData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

fun Route.hmac() {

    post("/hmac256") {
        runCatching {
            val data = call.receive<JWTHMACData>()
            call.respond(mapOf("token" to JWTHMACBuilder.createHMAC256(data)))
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, mapOf("detail" to it.message))
        }
    }

    post("/hmac256/verify") {
        runCatching {
            val data = call.receive<Map<String,String>>()
            val token = data["token"] ?: throw Exception("token required")
            val secret = data["secret"] ?: throw Exception("secret required")
            call.respond(JWTHMACBuilder.verifyHMAC256(token, secret))
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, mapOf("detail" to it.message))
        }
    }

    post("/hmac384") {
        runCatching {
            val data = call.receive<JWTHMACData>()
            call.respond(mapOf("token" to JWTHMACBuilder.createHMAC384(data)))
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, mapOf("detail" to it.message))
        }
    }

    post("/hmac384/verify") {
        runCatching {
            val data = call.receive<Map<String,String>>()
            val token = data["token"] ?: throw Exception("token required")
            val secret = data["secret"] ?: throw Exception("secret required")
            call.respond(JWTHMACBuilder.verifyHMAC384(token, secret))
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, mapOf("detail" to it.message))
        }
    }

    post("/hmac512") {
        runCatching {
            val data = call.receive<JWTHMACData>()
            call.respond(mapOf("token" to JWTHMACBuilder.createHMAC512(data)))
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, mapOf("detail" to it.message))
        }
    }

    post("/hmac512/verify") {
        runCatching {
            val data = call.receive<Map<String,String>>()
            val token = data["token"] ?: throw Exception("token required")
            val secret = data["secret"] ?: throw Exception("secret required")
            call.respond(JWTHMACBuilder.verifyHMAC512(token, secret))
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, mapOf("detail" to it.message))
        }
    }


}