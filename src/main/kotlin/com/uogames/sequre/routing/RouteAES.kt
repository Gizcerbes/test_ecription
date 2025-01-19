package com.uogames.sequre.routing

import com.uogames.sequre.Encoder
import com.uogames.sequre.model.ALG
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

fun Route.RouteAES() {

    route("/aes") {


        post("/encode") {
            runCatching {
                val params = call.receiveParameters()
                val type = params["type"]?.let {
                    runCatching { Encoder.AES.valueOf(it) }.getOrNull()
                } ?: return@post call.respond(HttpStatusCode.BadRequest)
                val key = params["key"]?.decodeBase64Bytes() ?: return@post call.respond(HttpStatusCode.BadRequest)
                val value = params["value"]?.toByteArray() ?: return@post call.respond(HttpStatusCode.BadRequest)
                val r = Encoder.aesEncrypt(
                    keyBytes = key,
                    ivBytes = value,
                    type = type
                )
                call.respond(r.encodeBase64())
            }.onFailure {
                call.respond(HttpStatusCode.BadRequest, it.message.orEmpty())
            }

        }

        post("/decode") {
            runCatching {
                val params = call.receiveParameters()
                val type = params["type"]?.let {
                    runCatching { Encoder.AES.valueOf(it) }.getOrNull()
                } ?: return@post call.respond(HttpStatusCode.BadRequest)
                val key = params["key"]?.decodeBase64Bytes() ?: return@post call.respond(HttpStatusCode.BadRequest)
                val value = params["value"]?.decodeBase64Bytes() ?: return@post call.respond(HttpStatusCode.BadRequest)
                val r = Encoder.aesDecrypt(
                    keyBytes = key,
                    ivBytes = value,
                    type = type
                )
                call.respond(String(r))
            }.onFailure {
                call.respond(HttpStatusCode.BadRequest, it.message.orEmpty())
            }
        }

        get {

            call.respond(ALG.AES(type = Encoder.AES.AES_CBC_PKCS_5_PADDING, key = "k", value = "v"))

        }


    }


}