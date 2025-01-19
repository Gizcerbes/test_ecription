package com.uogames.sequre

import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Encoder {


    enum class AES(val transformer: String) {
        AES_CBC_NO_PADDING("AES/CBC/NoPadding"),
        AES_CBC_PKCS_5_PADDING("AES/CBC/PKCS5Padding"),
        AES_ECB_NO_PADDING("AES/ECB/NoPadding"),
        AES_ECB_PKCS_5_PADDING("AES/ECB/PKCS5Padding"),
    }

    enum class DES(val transformer: String, val size: Int) {
        DES_CBC_NO_PADDING("DES/CBC/NoPadding", 8),
        DES_CBC_PKCS_5_PADDING("DES/CBC/PKCS5Padding", 8),
        DES_ECB_NO_PADDING("DES/ECB/PKCS5Padding", 8),
        DES_ECB_PKCS_5_PADDING("DES/ECB/NoPadding", 8),
    }

    class DesResult(
        val data: ByteArray,
        val iv: ByteArray?
    )

    enum class DESede(val transformer: String, val size: Int) {
        DESEDE_CBC_NO_PADDING("DESede/CBC/NoPadding", 24),
        DESEDE_CBC_PKCS_5_PADDING("DESede/CBC/PKCS5Padding", 24),
        DESEDE_ECB_NO_PADDING("DESede/ECB/NoPadding", 24),
        DESEDE_ECB_PKCS_5_PADDING("DESede/ECB/PKCS5Padding", 24)
    }

    fun aesEncrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: AES
    ): ByteArray {
        when (keyBytes.size) {
            16, 24, 32 -> {}
            else -> throw Exception("keyBytes must have size of 16, 24 or 32 bytes")
        }
        val spec = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance(type.transformer)
        when (type) {
            AES.AES_ECB_NO_PADDING, AES.AES_ECB_PKCS_5_PADDING -> cipher.init(Cipher.ENCRYPT_MODE, spec)
            else -> cipher.init(Cipher.ENCRYPT_MODE, spec, IvParameterSpec(ByteArray(16)))
        }
        return cipher.doFinal(ivBytes)
    }

    fun aesEncrypt(
        keyBytes: ByteArray,
        data: String,
        type: AES
    ): ByteArray {
        val ivBytes = data.padEnd((8 - data.length % 8) + data.length, ' ').toByteArray()
        return aesEncrypt(keyBytes, ivBytes, type)
    }

    fun aesDecrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: AES
    ): ByteArray {
        when (keyBytes.size) {
            16, 24, 32 -> {}
            else -> throw Exception("keyBytes must have size of 16, 24 or 32 bytes")
        }
        val spec = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance(type.transformer)
        when (type) {
            AES.AES_ECB_NO_PADDING, AES.AES_ECB_PKCS_5_PADDING -> cipher.init(Cipher.DECRYPT_MODE, spec)
            else -> cipher.init(Cipher.DECRYPT_MODE, spec, IvParameterSpec(ByteArray(16)))
        }
        return cipher.doFinal(ivBytes)
    }

    fun aesDecryptAsString(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: AES
    ): String {
        return aesDecrypt(keyBytes, ivBytes, type).decodeToString().trim()
    }

    fun desEncrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: DES
    ): DesResult {
        val spec = SecretKeySpec(keyBytes, "DES")
        val cipher = Cipher.getInstance(type.transformer)
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
        type: DES
    ): DesResult {
        if (keyBytes.size != type.size) throw Exception("This algorithm require ${type.size} bytes in key")
        val ivBytes = data.padEnd((8 - data.length % 8) + data.length, ' ').toByteArray()
        return desEncrypt(keyBytes, ivBytes, type)
    }

    fun desDecrypt(
        keyBytes: ByteArray,
        iv: ByteArray?,
        ivBytes: ByteArray,
        type: DES
    ): ByteArray {
        val spec = SecretKeySpec(keyBytes, "DES")
        val cipher = Cipher.getInstance(type.transformer)
        when {
            iv != null -> cipher.init(Cipher.DECRYPT_MODE, spec, IvParameterSpec(iv ))
            else -> cipher.init(Cipher.DECRYPT_MODE, spec)
        }
        return cipher.doFinal(ivBytes)
    }

    fun desDecryptAsString(
        keyBytes: ByteArray,
        iv: ByteArray?,
        data: ByteArray,
        type: DES
    ): String {
        return desDecrypt(keyBytes, iv, data, type).decodeToString().trim()
    }

    fun desedeEncrypt(
        keyBytes: ByteArray,
        ivBytes: ByteArray,
        type: DESede
    ): DesResult {
        val spec = SecretKeySpec(keyBytes, "DESede")
        val cipher = Cipher.getInstance(type.transformer)
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
        type: DESede
    ): DesResult {
        if (keyBytes.size != type.size) throw Exception("This algorithm require ${type.size} bytes in key")
        val ivBytes = data.padEnd((8 - data.length % 8) + data.length, ' ').toByteArray()
        return desedeEncrypt(keyBytes, ivBytes, type)
    }

    fun desedeDecrypt(
        keyBytes: ByteArray,
        iv: ByteArray?,
        ivBytes: ByteArray,
        type: DESede
    ): ByteArray {
        val spec = SecretKeySpec(keyBytes, "DESede")
        val cipher = Cipher.getInstance(type.transformer)
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
        type: DESede
    ): String {
        return desedeDecrypt(keyBytes, iv, data, type).decodeToString().trim()
    }




}
