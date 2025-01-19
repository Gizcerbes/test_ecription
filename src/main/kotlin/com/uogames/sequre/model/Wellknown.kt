package com.uogames.sequre.model


import com.google.gson.annotations.SerializedName

data class Wellknown(
    @SerializedName("keys")
    val keys: List<Key?>? = null
) {
    data class Key(
        @SerializedName("alg")
        val alg: String? = null,
        @SerializedName("e")
        val e: String? = null,
        @SerializedName("kid")
        val kid: String? = null,
        @SerializedName("kty")
        val kty: String? = null,
        @SerializedName("n")
        val n: String? = null,
        @SerializedName("use")
        val use: String? = null,
        @SerializedName("x5c")
        val x5c: List<String?>? = null,
        @SerializedName("x5t")
        val x5t: String? = null
    )
}