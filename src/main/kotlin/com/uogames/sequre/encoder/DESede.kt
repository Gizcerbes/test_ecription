package com.uogames.sequre.encoder


import org.bouncycastle.jce.provider.BouncyCastleProvider
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object DESede {

    enum class TYPE(val transformer: String, val size: Int) {
        DESEDE_CBC_NO_PADDING("DESede/CBC/NoPadding", 24),
        DESEDE_CBC_PKCS_5_PADDING("DESede/CBC/PKCS5Padding", 24),
        DESEDE_ECB_NO_PADDING("DESede/ECB/NoPadding", 24),
        DESEDE_ECB_PKCS_5_PADDING("DESede/ECB/PKCS5Padding", 24)
    }

    class DesResult(
        val data: ByteArray,
        val iv: ByteArray?
    )

    private val provider = BouncyCastleProvider()

    private fun getCipher(type:String) = Cipher.getInstance(type, provider)

    fun desedeEncrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: TYPE
    ): DesResult {
        if (keyBytes.size != type.size) throw Exception("This algorithm require ${type.size} bytes in key")
        val spec = SecretKeySpec(keyBytes, "DESede")
        val cipher = getCipher(type.transformer)
        cipher.init(Cipher.ENCRYPT_MODE, spec)
        val iv = cipher.iv
        return DesResult(
            data = cipher.doFinal(ivBytes),
            iv = iv
        )
    }

    fun desedeEncrypt(
        keyBytes: ByteArray,
        data: String,
        type: TYPE
    ): DesResult {
        val ivBytes = data.padEnd((8 - data.length % 8) + data.length, ' ').toByteArray()
        return desedeEncrypt(keyBytes, ivBytes, type)
    }

    fun desedeDecrypt(
        keyBytes: ByteArray,
        iv: ByteArray?,
        ivBytes: ByteArray,
        type: TYPE
    ): ByteArray {
        if (keyBytes.size != type.size) throw Exception("This algorithm require ${type.size} bytes in key")
        val spec = SecretKeySpec(keyBytes, "DESede")
        val cipher = getCipher(type.transformer)
        when {
            iv != null -> cipher.init(Cipher.DECRYPT_MODE, spec, IvParameterSpec(iv ))
            else -> cipher.init(Cipher.DECRYPT_MODE, spec)
        }
        return cipher.doFinal(ivBytes)
    }

    fun desedeDecryptAsString(
        keyBytes: ByteArray,
        iv: ByteArray?,
        data: ByteArray,
        type: TYPE
    ): String {
        return desedeDecrypt(keyBytes, iv, data, type).decodeToString().trim()
    }

}