package com.uogames.sequre.token.hmac

import com.google.gson.annotations.SerializedName
import java.util.Date


data class JWTHMACData(
    @SerializedName("secret")
    val secret: String,
    @SerializedName("expires_at")
    val expiresAt: Long = Date().time + 1000 * 60,
    @SerializedName("audience")
    val audience: List<String>? = emptyList(),
    @SerializedName("issued_at")
    val issuedAt: Long? = null,
    @SerializedName("issuer")
    val issuer: String? = null,
    @SerializedName("jwt_id")
    val jwtId: String? = null,
    @SerializedName("key_id")
    val keyID: String? = null,
    @SerializedName("not_before")
    val notBefore: Long? = null,
    @SerializedName("subject")
    val subject: String? = null,
    @SerializedName("payload")
    val payload: List<Any>? = emptyList(),
    @SerializedName("header")
    val header: List<Any>? = emptyList()
)