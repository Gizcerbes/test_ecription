package com.uogames.sequre.token.hmac

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.google.gson.Gson
import io.ktor.util.*
import java.util.*

object JWTHMACBuilder {

    private fun getCreator(data: JWTHMACData): JWTCreator.Builder{
        val builder = JWT.create()
        data.header?.forEach { builder.withHeader(Gson().toJson(it)) }
        data.payload?.forEach { builder.withPayload(Gson().toJson(it)) }
        builder.withExpiresAt(Date(data.expiresAt))
        if (data.audience?.isNotEmpty() == true) builder.withAudience(*data.audience.toTypedArray())
        data.issuedAt?.also { builder.withIssuedAt(Date(it)) }
        data.issuer?.also { builder.withIssuer(it) }
        data.jwtId?.also { builder.withJWTId(it) }
        data.keyID?.also { builder.withKeyId(it) }
        data.notBefore?.also { builder.withNotBefore(Date(it)) }
        data.subject?.also { builder.withSubject(it) }
        return builder
    }

    fun createHMAC256(data: JWTHMACData):String{
        val builder = getCreator(data)
        return builder.sign(Algorithm.HMAC256(data.secret.encodeBase64()))
    }

    fun verifyHMAC256(token:String, secret:String): Boolean = try {
        JWT.require(Algorithm.HMAC256(secret.encodeBase64())).build().verify(token)
        true
    } catch (e: Exception){
        false
    }

    fun createHMAC384(data: JWTHMACData):String{
        val builder = getCreator(data)
        return builder.sign(Algorithm.HMAC384(data.secret.encodeBase64()))
    }

    fun verifyHMAC384(token:String, secret:String): Boolean = try {
        JWT.require(Algorithm.HMAC384(secret.encodeBase64())).build().verify(token)
        true
    } catch (e: Exception){
        false
    }

    fun createHMAC512(data: JWTHMACData):String{
        val builder = getCreator(data)
        return builder.sign(Algorithm.HMAC512(data.secret.encodeBase64()))
    }

    fun verifyHMAC512(token:String, secret:String): Boolean = try {
        JWT.require(Algorithm.HMAC512(secret.encodeBase64())).build().verify(token)
        true
    } catch (e: Exception){
        false
    }

}
