package com.uogames.sequre.encoder

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object DESede2 {

    enum class TYPE(
        val transformer: String,
        val ivSize: Int = 8,
        val requiresPadding: Boolean = false,
    ) {
        DESede_CBC_NO_PADDING("DESede/CBC/NoPadding", ivSize = 8, requiresPadding = true),
        DESede_CBC_PKCS_5_PADDING("DESede/CBC/PKCS5Padding", ivSize = 8, requiresPadding = false),
        DESede_ECB_NO_PADDING("DESede/ECB/NoPadding", ivSize = 0, requiresPadding = true),
        DESede_ECB_PKCS_5_PADDING("DESede/ECB/PKCS5Padding", ivSize = 0, requiresPadding = false);

        fun generateIV(): ByteArray = ByteArray(ivSize).apply { SecureRandom().nextBytes(this) }
    }

    private const val DESede_BLOCK_SIZE = 8
    private const val PAD_BYTE = (-1).toByte()

    private val provider = BouncyCastleProvider()

    private fun getCipher(type: String) = Cipher.getInstance(type, provider)

    private fun validateKeySize(keyBytes: ByteArray) {
        require(keyBytes.size in listOf(16, 24)) {
            "keyBytes must have size of 16 or 24 bytes (112 or 168 bits)"
        }
    }

    private fun initCipher(cipher: Cipher, mode: Int, keyBytes: ByteArray, type: TYPE, iv: ByteArray) {
        val keySpec = SecretKeySpec(keyBytes, "DESede")
        when (type) {
            TYPE.DESede_ECB_NO_PADDING, TYPE.DESede_ECB_PKCS_5_PADDING -> cipher.init(mode, keySpec)
            else -> cipher.init(mode, keySpec, IvParameterSpec(iv))
        }
    }

    fun desedeEncrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        type: TYPE
    ): ByteArray {
        validateKeySize(keyBytes)
        val cipher = getCipher(type.transformer)

        val processedData = if (type.requiresPadding) padData(data, DESede_BLOCK_SIZE) else data

        initCipher(cipher, Cipher.ENCRYPT_MODE, keyBytes, type, iv)
        return cipher.doFinal(processedData)
    }

    fun desedeEncrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        data: String,
        type: TYPE
    ): ByteArray {
        return desedeEncrypt(keyBytes, iv, data.toByteArray(), type)
    }

    fun desedeDecrypt(
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

    fun desedeDecryptAsString(
        keyBytes: ByteArray,
        iv: ByteArray,
        encryptedData: ByteArray,
        type: TYPE
    ): String {
        return desedeDecrypt(keyBytes, iv, encryptedData, type).decodeToString()
    }

    private fun padData(data: ByteArray, blockSize: Int): ByteArray {
        val paddingSize = (blockSize - data.size % blockSize) % blockSize
        return if (paddingSize == 0) data else data + ByteArray(paddingSize) { PAD_BYTE }
    }

}