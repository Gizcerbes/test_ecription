package com.uogames.sequre.encoder

import com.uogames.sequre.encoder.CAST128.Result
import com.uogames.sequre.encoder.CAST128.TYPE
import org.bouncycastle.jce.provider.BouncyCastleProvider
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object RC2 {

    enum class TYPE(val transformer: String) {
        RC2("RC2"),
        RC2_ECB_NO_PADDING("RC2/ECB/NoPadding"),
        RC2_ECB_PKCS5_PADDING("RC2/ECB/PKCS5Padding"),
        RC2_CBC_NO_PADDING("RC2/CBC/NoPadding"),
        RC2_CBC_PKCS5_PADDING("RC2/CBC/PKCS5Padding")
    }

    class Result(
        val data: ByteArray,
        val iv: ByteArray?
    )

    private val provider = BouncyCastleProvider()

    private fun getCipher(type: String) = Cipher.getInstance(type, provider)

    fun encrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): Result {
        val spec = SecretKeySpec(keyBytes, "RC2")
        val cipher = getCipher(type.transformer)
        cipher.init(Cipher.ENCRYPT_MODE, spec)
        return Result(
            data = cipher.doFinal(ivBytes),
            iv = cipher.iv
        )
    }

    fun encrypt(
        keyBytes: ByteArray,
        data: String,
        type: TYPE
    ): Result {
        val ivBytes = data.padEnd((8 - data.length % 8) + data.length, ' ').toByteArray()
        return encrypt(keyBytes, ivBytes, type)
    }

    fun decrypt(
        keyBytes: ByteArray,
        iv: ByteArray?,
        data: ByteArray,
        type: TYPE
    ):ByteArray{
        val spec = SecretKeySpec(keyBytes, "RC2")
        val cipher = getCipher(type.transformer)
        when {
            iv != null -> cipher.init(Cipher.DECRYPT_MODE, spec, IvParameterSpec(iv))
            else -> cipher.init(Cipher.DECRYPT_MODE, spec)
        }
        return cipher.doFinal(data)
    }

    fun decryptAsString(
        keyBytes: ByteArray,
        iv: ByteArray?,
        data: ByteArray,
        type: TYPE
    ): String {
        return decrypt(keyBytes, iv, data, type).decodeToString().trim()
    }


}