package com.uogames.sequre.encoder

import org.bouncycastle.jce.provider.BouncyCastleProvider
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object DES {

    enum class TYPE(val transformer: String, val size: Int) {
        DES_CBC_NO_PADDING("DES/CBC/NoPadding", 8),
        DES_CBC_PKCS_5_PADDING("DES/CBC/PKCS5Padding", 8),
        DES_ECB_NO_PADDING("DES/ECB/PKCS5Padding", 8),
        DES_ECB_PKCS_5_PADDING("DES/ECB/NoPadding", 8),
    }

    class DesResult(
        val data: ByteArray,
        val iv: ByteArray?
    )

    private val provider = BouncyCastleProvider()

    private fun getCipher(type: String) = Cipher.getInstance(type, provider)


    fun desEncrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): DesResult {
        val spec = SecretKeySpec(keyBytes, "DES")
        val cipher = getCipher(type.transformer)
        cipher.init(Cipher.ENCRYPT_MODE, spec)
        val iv = cipher.iv
        return DesResult(
            data = cipher.doFinal(ivBytes),
            iv = iv
        )
    }

    fun desEncrypt(
        keyBytes: ByteArray,
        data: String,
        type: TYPE
    ): DesResult {
        if (keyBytes.size != type.size) throw Exception("This algorithm require ${type.size} bytes in key")
        val ivBytes = data.padEnd((8 - data.length % 8) + data.length, ' ').toByteArray()
        return desEncrypt(keyBytes, ivBytes, type)
    }

    fun desDecrypt(
        keyBytes: ByteArray,
        iv: ByteArray?,
        ivBytes: ByteArray,
        type: TYPE
    ): ByteArray {
        val spec = SecretKeySpec(keyBytes, "DES")
        val cipher = getCipher(type.transformer)
        when {
            iv != null -> cipher.init(Cipher.DECRYPT_MODE, spec, IvParameterSpec(iv))
            else -> cipher.init(Cipher.DECRYPT_MODE, spec)
        }
        return cipher.doFinal(ivBytes)
    }

    fun desDecryptAsString(
        keyBytes: ByteArray,
        iv: ByteArray?,
        data: ByteArray,
        type: TYPE
    ): String {
        return desDecrypt(keyBytes, iv, data, type).decodeToString().trim()
    }

}

