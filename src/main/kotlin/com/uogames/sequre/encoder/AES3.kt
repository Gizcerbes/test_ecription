package com.uogames.sequre.encoder

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES3 {

    enum class TYPE(
        val transformer: String,
        val ivSize: Int = 16,
        val requiresPadding: Boolean = false,
        val tagSize: Int = 0
    ) {
        AES_CBC_NO_PADDING("AES/CBC/NoPadding", ivSize = 16, requiresPadding = true),
        AES_CBC_PKCS_5_PADDING("AES/CBC/PKCS5Padding", ivSize = 16, requiresPadding = false),
        AES_ECB_NO_PADDING("AES/ECB/NoPadding", ivSize = 0, requiresPadding = true),
        AES_ECB_PKCS_5_PADDING("AES/ECB/PKCS5Padding", ivSize = 0, requiresPadding = false),
        AES_GCM_NO_PADDING(
            "AES/GCM/NoPadding",
            ivSize = 12,
            requiresPadding = false,
            tagSize = 128
        ),
        AES_CTR_NO_PADDING("AES/CTR/NoPadding", ivSize = 16, requiresPadding = false);

        fun generateIV(): ByteArray = ByteArray(ivSize).apply { SecureRandom().nextBytes(this) }

    }

    private const val AES_BLOCK_SIZE = 16
    private const val PAD_BYTE = (-1).toByte()

    private val provider = BouncyCastleProvider()

    private fun getCipher(type: String) = Cipher.getInstance(type, provider)

    private fun validateKeySize(keyBytes: ByteArray) {
        require(keyBytes.size in listOf(16, 24, 32)) {
            "keyBytes must have size of 16, 24 or 32 bytes"
        }
    }

    private fun initCipher(cipher: Cipher, mode: Int, keyBytes: ByteArray, type: TYPE, iv: ByteArray) {
        val keySpec = SecretKeySpec(keyBytes, "AES")
        when (type) {
            TYPE.AES_ECB_NO_PADDING, TYPE.AES_ECB_PKCS_5_PADDING -> cipher.init(mode, keySpec)
            TYPE.AES_GCM_NO_PADDING -> {
                val ivSpec = GCMParameterSpec(type.tagSize, iv)
                cipher.init(mode, keySpec, ivSpec)
            }

            else -> cipher.init(mode, keySpec, IvParameterSpec(iv))
        }
    }

    fun aesEncrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        type: TYPE
    ): ByteArray {
        validateKeySize(keyBytes)
        val cipher = getCipher(type.transformer)

        val processedData = if (type.requiresPadding) padData(data, AES_BLOCK_SIZE) else data

        initCipher(cipher, Cipher.ENCRYPT_MODE, keyBytes, type, iv)
        val encryptedData = cipher.doFinal(processedData)
        return encryptedData
    }

    fun aesEncrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        data: String,
        type: TYPE
    ): ByteArray {
        return aesEncrypt(keyBytes, iv, data.toByteArray(), type)
    }

    fun aesDecrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        encryptedData: ByteArray,
        type: TYPE
    ): ByteArray {
        validateKeySize(keyBytes)
        val cipher = getCipher(type.transformer)
        initCipher(cipher, Cipher.DECRYPT_MODE, keyBytes, type, iv)
        return cipher.doFinal(encryptedData).let {
            var pad = 0
            while (it[it.size - pad - 1] == PAD_BYTE) pad++
            it.copyOfRange(0, it.size - pad)
        }
    }

    fun aesDecryptAsString(
        keyBytes: ByteArray,
        iv: ByteArray,
        encryptedData: ByteArray,
        type: TYPE
    ): String {
        return aesDecrypt(keyBytes, iv, encryptedData, type).decodeToString()
    }

    private fun padData(data: ByteArray, blockSize: Int): ByteArray {
        val paddingSize = (blockSize - data.size % blockSize) % blockSize
        return if (paddingSize == 0) data else data + ByteArray(paddingSize) { PAD_BYTE }
    }
}