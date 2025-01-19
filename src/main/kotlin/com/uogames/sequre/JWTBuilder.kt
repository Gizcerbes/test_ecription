package com.uogames.sequre

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.gson.Gson
import com.uogames.sequre.model.ALG
import io.ktor.util.*
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import java.util.concurrent.TimeUnit


object JWTBuilder {

    fun createSHMAC256(algorithm: ALG.HMAC) = JWT
        .create().apply {
            withExpiresAt(Date(algorithm.expiresAt))
            withClaim("claims", algorithm.claims)
        }.sign(Algorithm.HMAC256(algorithm.secret.encodeBase64()))


    fun checkSHMAC256(token: String, secret: String): Boolean = try {
        JWT.require(Algorithm.HMAC256(secret.encodeBase64())).build().verify(token)
        true
    } catch (e: Exception) {
        false
    }

    fun createBHMAC256(claims: HashMap<String, *>, expiresAt: Long, secret: ByteArray) = JWT
        .create().apply {
            withExpiresAt(Date(expiresAt))
            withClaim("claims", claims)
        }.sign(Algorithm.HMAC256(secret))

    fun checkBHMAC256(token: String, secret: ByteArray): Boolean = try {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token)
        true
    } catch (e: Exception) {
        false
    }


    fun createSHMAC384(algorithm: ALG.HMAC) = JWT
        .create().apply {
            withExpiresAt(Date(algorithm.expiresAt))
            withClaim("claims", algorithm.claims)
        }.sign(Algorithm.HMAC384(algorithm.secret.encodeBase64()))


    fun checkSHMAC384(token: String, secret: String): Boolean = try {
        JWT.require(Algorithm.HMAC384(secret.encodeBase64())).build().verify(token)
        true
    } catch (e: Exception) {
        false
    }

    fun createBHMAC384(claims: HashMap<String, *>, expiresAt: Long, secret: ByteArray) = JWT
        .create().apply {
            withExpiresAt(Date(expiresAt))
            withClaim("claims", claims)
        }.sign(Algorithm.HMAC384(secret))

    fun checkBHMAC384(token: String, secret: ByteArray): Boolean = try {
        JWT.require(Algorithm.HMAC384(secret)).build().verify(token)
        true
    } catch (e: Exception) {
        false
    }

    fun createSHMAC512(algorithm: ALG.HMAC) = JWT
        .create().apply {
            withExpiresAt(Date(algorithm.expiresAt))
            withClaim("claims", algorithm.claims)
        }.sign(Algorithm.HMAC512(algorithm.secret.encodeBase64()))


    fun checkSHMAC512(token: String, secret: String): Boolean = try {
        JWT.require(Algorithm.HMAC512(secret.encodeBase64())).build().verify(token)
        true
    } catch (e: Exception) {
        false
    }

    fun createBHMAC512(claims: HashMap<String, *>, expiresAt: Long, secret: ByteArray) = JWT
        .create().apply {
            withExpiresAt(Date(expiresAt))
            withClaim("claims", claims)
        }.sign(Algorithm.HMAC512(secret))

    fun checkBHMAC512(token: String, secret: ByteArray): Boolean = try {
        JWT.require(Algorithm.HMAC512(secret)).build().verify(token)
        true
    } catch (e: Exception) {
        false
    }

}

fun main() {

//    val json = Gson().toJson(mapOf("map-key" to "value"))
//    val payload = Gson().toJson(mapOf("key" to "value"))
//
//    val t = JWT.create()
//        .withExpiresAt(Date()) // exp=1724339889
//        .withClaim("cl", "cl") // cl="cl"
//        .withArrayClaim("arcl", arrayOf("1", "2")) // arcl=["1","2"]
//        .withAudience("au1", "au2") // aud=["au1","au2"]
//        .withIssuedAt(Date()) // iat=1724339889
//        .withIssuer("ISSUER") // iss="ISSUER"
//        .withJWTId("jwt-id-1234") // jti="jwt-id-1234"
//        .withKeyId("key-id-1234")
//        .withNotBefore(Date()) // nbf=1724339991
//        .withNullClaim("n-cl") // n-cl=Null claim
//        .withSubject("Subject") // sub="Subject"
//        .withHeader(json)
//        .withPayload(payload) // key="value"
//        .withPayload(json)
//        .sign(Algorithm.HMAC256("secret"))
//
//    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjB9.PNUa_yp5eYhYpt1PNWhZRm0AmPaaXcXiuqX9-ZjKbBM"
//
//    val t2 = "eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJhZDk3M2JjZi1jOThhLTRhYjYtYmNhOC1kMWQ2OWNiMDJjNzQifQ.eyJleHAiOjE3MjQzNDIxMjIsImlhdCI6MTcyNDM0MDMyMiwianRpIjoiMjEyNzhiNDItOTlmOC00OTVmLWI3NzktNjhhY2Y3YTVmZmE1IiwiaXNzIjoiaHR0cDovL2tleWNsb2FrX193ZWI6ODA4MC9yZWFsbXMvY3VzdG9tZXJzIiwiYXVkIjoiaHR0cDovL2tleWNsb2FrX193ZWI6ODA4MC9yZWFsbXMvY3VzdG9tZXJzIiwic3ViIjoiZWM0OGZmNGUtZjY4Zi00NjhlLWJlZWEtN2I4MDJmNGQ2ZDZlIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6InNob3Atb2YtbG92ZS1hcGkiLCJzaWQiOiJmY2VlNWNhOC1hM2VhLTQzZGUtYTcyMS1jMTI3YTA4ZmQ3OWMiLCJzY29wZSI6Im9wZW5pZCBhY3IgZW1haWwgcHJvZmlsZSByb2xlcyBiYXNpYyB3ZWItb3JpZ2lucyIsInJldXNlX2lkIjoiZjk4MDEyMTUtMjIyYS00ZDQxLWE4NDYtYjMyMTEyMWY1Zjc1In0.fH3kB5ZS32zbvSggZp6n41wMMqp0qLLQQuXi6d7PJjyAV7Dw2E8TV9Is-7-U4E2seLQm9yx9dNf5JFmikbcA7A"
//
//
//    JWT.decode(t).apply {
//        claims.forEach { println(it) }
//        println(header.decodeBase64String())
//    }
//
//
//    println()
//
//    JWT.decode(t2).apply {
//        claims.forEach { println(it) }
//        println(header.decodeBase64String())
//    }


    //////////////////////////////////

// println(System.currentTimeMillis())

//    val res = JWTBuilder.create("secret", "my supper id", 1000L)
//    println(res)
//    val m = mapOf("a" to "b")
//    println(Gson().toJson(m))
//    println(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 3)

//    Algorithm.HMAC384("")
//    Algorithm.HMAC384(byteArrayOf())
//    Algorithm.HMAC512("")
//    Algorithm.HMAC512(byteArrayOf())


////
    val privateKeyString =  "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAtfJaLrzXILUg1U3N1KV8yJr92GHn5OtYZR7qWk1Mc4cy4JGjklYup7weMjBD9f3bBVoIsiUVX6xNcYIr0Ie0AQIDAQABAkEAg+FBquToDeYcAWBe1EaLVyC45HG60zwfG1S4S3IB+y4INz1FHuZppDjBh09jptQNd+kSMlG1LkAc/3znKTPJ7QIhANpyB0OfTK44lpH4ScJmCxjZV52mIrQcmnS3QzkxWQCDAiEA1Tn7qyoh+0rOO/9vJHP8U/beo51SiQMw0880a1UaiisCIQDNwY46EbhGeiLJR1cidr+JHl86rRwPDsolmeEF5AdzRQIgK3KXL3d0WSoS//K6iOkBX3KMRzaFXNnDl0U/XyeGMuUCIHaXv+n+Brz5BDnRbWS+2vkgIe9bUNlkiArpjWvX+2we"
    val issuer = "http://localhost:8079/"
//    val audience = "http://0.0.0.0:8080/hello"
//
    val jwkProvider = JwkProviderBuilder(issuer)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()



    val publicKey = jwkProvider.get("6f8856ed-9189-488f-9011-0ff4b6c08edc").publicKey
    val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
    val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)
    val pub = privateKey.encoded.encodeBase64()
    println(pub)
    val token = JWT.create()
        //.withAudience(audience)
        //.withIssuer(issuer)
        .withClaim("username", "123")
        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
        .sign(Algorithm.RSA256(pub as RSAPublicKey, privateKey as RSAPrivateKey))

    println(token)

    JWT.require(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey)).build().verify(token)

}