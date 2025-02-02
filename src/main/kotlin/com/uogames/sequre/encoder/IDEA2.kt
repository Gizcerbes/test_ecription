package com.uogames.sequre.encoder

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object IDEA2 {

    enum class TYPE(
        val transformer: String,
        val ivSize: Int = 8,
        val requiresPadding: Boolean = false
    ) {

        IDEA("IDEA", ivSize = 0, requiresPadding = false),

        IDEA_ECB_NO_PADDING("IDEA/ECB/NoPadding", ivSize = 0, requiresPadding = true),
        IDEA_ECB_PKCS5_PADDING("IDEA/ECB/PKCS5Padding", ivSize = 0, requiresPadding = false),

        IDEA_CBC_NO_PADDING("IDEA/CBC/NoPadding", ivSize = 8, requiresPadding = true),
        IDEA_CBC_PKCS5_PADDING("IDEA/CBC/PKCS5Padding", ivSize = 8, requiresPadding = false),

        IDEA_CFB_NO_PADDING("IDEA/CFB/NoPadding", ivSize = 8, requiresPadding = true),
        IDEA_CFB_PKCS5_PADDING("IDEA/CFB/PKCS5Padding", ivSize = 8, requiresPadding = false),

        IDEA_OFB_NO_PADDING("IDEA/OFB/NoPadding", ivSize = 8, requiresPadding = true),
        IDEA_OFB_PKCS5_PADDING("IDEA/OFB/PKCS5Padding", ivSize = 8, requiresPadding = false);

        fun generateIV(): ByteArray = ByteArray(ivSize).apply { SecureRandom().nextBytes(this) }

    }

    private const val IDEA_BLOCK_SIZE = 8
    private const val PAD_BYTE = (-1).toByte()

    private val provider = BouncyCastleProvider()

    private fun getCipher(type: String): Cipher {
        return Cipher.getInstance(type, provider)
    }

    private fun initCipher(cipher: Cipher, mode: Int, keyBytes: ByteArray, type: TYPE, iv: ByteArray) {
        val keySpec = SecretKeySpec(keyBytes, "IDEA")
        when {
            type.ivSize == 0 -> cipher.init(mode, keySpec)
            else -> cipher.init(mode, keySpec, IvParameterSpec(iv))
        }
    }

    fun ideaEncrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        data: ByteArray,
        type: TYPE
    ): ByteArray {
        val cipher = getCipher(type.transformer)

        val processedData = if (type.requiresPadding) padData(data, IDEA_BLOCK_SIZE) else data

        initCipher(cipher, Cipher.ENCRYPT_MODE, keyBytes, type, iv)
        return cipher.doFinal(processedData)
    }

    fun ideaEncrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        data: String,
        type: TYPE
    ): ByteArray {
        return ideaEncrypt(keyBytes, iv, data.toByteArray(), type)
    }

    fun ideaDecrypt(
        keyBytes: ByteArray,
        iv: ByteArray,
        encryptedData: ByteArray,
        type: TYPE
    ): ByteArray {
        val cipher = getCipher(type.transformer)
        initCipher(cipher, Cipher.DECRYPT_MODE, keyBytes, type, iv)
        return cipher.doFinal(encryptedData).let {
            var pad = 0
            while (it[it.size - pad - 1] == PAD_BYTE) pad++
            it.copyOfRange(0, it.size - pad)
        }
    }

    fun ideaDecryptAsString(
        keyBytes: ByteArray,
        iv: ByteArray,
        encryptedData: ByteArray,
        type: TYPE
    ): String {
        return ideaDecrypt(keyBytes, iv, encryptedData, type).decodeToString()
    }

    private fun padData(data: ByteArray, blockSize: Int): ByteArray {
        val paddingSize = (blockSize - data.size % blockSize) % blockSize
        return if (paddingSize == 0) data else data + ByteArray(paddingSize) { PAD_BYTE }
    }
}