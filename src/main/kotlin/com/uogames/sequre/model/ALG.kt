package com.uogames.sequre.model

import com.google.gson.annotations.SerializedName
import com.uogames.sequre.Encoder

sealed interface ALG {
    data class HMAC(
        @SerializedName("secret") val secret: String,
        @SerializedName("claims") val claims: Map<String, String>,
        @SerializedName("expiresAt") val expiresAt: Long
    ) : ALG

    data class AES(
        @SerializedName("type") val type: Encoder.AES,
        @SerializedName("key") val key: String,
        @SerializedName("value") val value: String
    ) : ALG
}