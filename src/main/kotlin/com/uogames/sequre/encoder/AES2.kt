package com.uogames.sequre.encoder

import org.bouncycastle.jce.provider.BouncyCastleProvider
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES2 {

    enum class TYPE(val transformer: String) {
        AES_CBC_NO_PADDING("AES/CBC/NoPadding"),
        AES_CBC_PKCS_5_PADDING("AES/CBC/PKCS5Padding"),
        AES_ECB_NO_PADDING("AES/ECB/NoPadding"),
        AES_ECB_PKCS_5_PADDING("AES/ECB/PKCS5Padding"),
    }

    private val provider = BouncyCastleProvider()

    private fun getCipher(type: String) = Cipher.getInstance(type, provider)

    private fun initCipher(
        keyBytes: ByteArray,
        type: TYPE,
        mode: Int
    ): Cipher {
        val spec = SecretKeySpec(keyBytes, "AES")
        val cipher = getCipher(type.transformer)
        when (type) {
            TYPE.AES_ECB_NO_PADDING, TYPE.AES_ECB_PKCS_5_PADDING -> cipher.init(mode, spec)
            else -> cipher.init(mode, spec, IvParameterSpec(ByteArray(16)))
        }
        return cipher
    }

    fun aesEncrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): ByteArray {
        return initCipher(keyBytes, type, Cipher.ENCRYPT_MODE).doFinal(ivBytes)
    }

    fun aesEncrypt(
        keyBytes: ByteArray,
        data: String,
        type: TYPE
    ): ByteArray {
        val bytes = data.toByteArray().toList()
        val iv = ArrayList<Byte>().apply {
            addAll(bytes)
            while (size %16 != 0) add(' '.code.toByte())
        }.toByteArray()
        return aesEncrypt(keyBytes, iv, type)
    }

    fun aesDecrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): ByteArray {
        return initCipher(keyBytes, type, Cipher.DECRYPT_MODE).doFinal(ivBytes)
    }

    fun aesDecryptAsString(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): String {
        return aesDecrypt(keyBytes, ivBytes, type).decodeToString().trim()
    }
}