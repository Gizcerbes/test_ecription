package com.uogames.sequre.routing

import com.google.gson.Gson
import com.uogames.sequre.model.ALG
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.uogames.sequre.JWTBuilder

fun Route.RouteHmac512() {


    post("/SHMAC512") {
        val alg = call.receive<ALG.HMAC>()
        call.respond(JWTBuilder.createSHMAC512(alg))
    }

    post("/SHMAC512/verify") {
        runCatching {
            val param = call.receiveParameters()
            val token = param["token"] ?: return@post call.respond(HttpStatusCode.BadRequest, "require token")
            val secret = param["secret"] ?: return@post call.respond(HttpStatusCode.BadRequest, "require secret")
            call.respond(JWTBuilder.checkSHMAC512(token, secret))
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, it.message.orEmpty())
        }

    }

    post("/BHMAC512") {
        val claims =  call.request.headers["claims"]?.let { runCatching { Gson().fromJson(it, HashMap<String, Any>().javaClass) }.getOrNull() } ?: return@post call.respond("require claims")
        val expireAt =  call.request.headers["expire_at"]?.toLongOrNull() ?: return@post call.respond("require expire_at")
        val secret = call.receive<ByteArray>()
        if (secret.isEmpty()) return@post call.respond("require binary secret")
        call.respond(JWTBuilder.createBHMAC512(claims, expireAt, secret))
    }

    post("/BHMAC512/verify") {
        val token = call.request.headers["token"]?: return@post call.respond(HttpStatusCode.BadRequest, "require token")
        val secret = call.receive<ByteArray>()
        if (secret.isEmpty()) return@post call.respond("require binary secret")
        call.respond(JWTBuilder.checkBHMAC512(token, secret))
    }


}