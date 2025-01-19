package com.uogames.sequre.encoder

import org.bouncycastle.jce.provider.BouncyCastleProvider
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES {

    enum class TYPE(val transformer: String) {
        AES_CBC_NO_PADDING("AES/CBC/NoPadding"),
        AES_CBC_PKCS_5_PADDING("AES/CBC/PKCS5Padding"),
        AES_ECB_NO_PADDING("AES/ECB/NoPadding"),
        AES_ECB_PKCS_5_PADDING("AES/ECB/PKCS5Padding"),
    }

    private val provider = BouncyCastleProvider()

    private fun getCipher(type:String) = Cipher.getInstance(type, provider)

    fun aesEncrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): ByteArray {
        when (keyBytes.size) {
            16, 24, 32 -> {}
            else -> throw Exception("keyBytes must have size of 16, 24 or 32 bytes")
        }
        val spec = SecretKeySpec(keyBytes, "AES")
        val cipher = getCipher(type.transformer)
        when (type) {
            TYPE.AES_ECB_NO_PADDING, TYPE.AES_ECB_PKCS_5_PADDING -> cipher.init(Cipher.ENCRYPT_MODE, spec)
            else -> cipher.init(Cipher.ENCRYPT_MODE, spec, IvParameterSpec(ByteArray(16)))
        }
        return cipher.doFinal(ivBytes)
    }

    fun aesEncrypt(
        keyBytes: ByteArray,
        data: String,
        type: TYPE
    ): ByteArray {
        val ivBytes = data.padEnd((8 - data.length % 8) + data.length, ' ').toByteArray()
        return aesEncrypt(keyBytes, ivBytes, type)
    }

    fun aesDecrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): ByteArray {
        when (keyBytes.size) {
            16, 24, 32 -> {}
            else -> throw Exception("keyBytes must have size of 16, 24 or 32 bytes")
        }
        val spec = SecretKeySpec(keyBytes, "AES")
        val cipher = getCipher(type.transformer)
        when (type) {
            TYPE.AES_ECB_NO_PADDING, TYPE.AES_ECB_PKCS_5_PADDING -> cipher.init(Cipher.DECRYPT_MODE, spec)
            else -> cipher.init(Cipher.DECRYPT_MODE, spec, IvParameterSpec(ByteArray(16)))
        }
        return cipher.doFinal(ivBytes)
    }

    fun aesDecryptAsString(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): String {
        return aesDecrypt(keyBytes, ivBytes, type).decodeToString().trim()
    }





}