package com.uogamse.sequre

import com.uogames.sequre.encoder.RC2
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class RC2Test {

    @Test
    fun rC2() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = RC2.encrypt(keyBytes, data, RC2.TYPE.RC2)
        val decoded = RC2.decryptAsString(keyBytes, encoded.iv, encoded.data, RC2.TYPE.RC2)
        assertEquals(data, decoded)
    }

    @Test
    fun rC2_ECB_NO_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = RC2.encrypt(keyBytes, data, RC2.TYPE.RC2_ECB_NO_PADDING)
        val decoded = RC2.decryptAsString(keyBytes, encoded.iv, encoded.data, RC2.TYPE.RC2_ECB_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun rC2_ECB_PKCS5_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = RC2.encrypt(keyBytes, data, RC2.TYPE.RC2_ECB_PKCS5_PADDING)
        val decoded = RC2.decryptAsString(keyBytes, encoded.iv, encoded.data, RC2.TYPE.RC2_ECB_PKCS5_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun rC2_CBC_NO_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = RC2.encrypt(keyBytes, data, RC2.TYPE.RC2_CBC_NO_PADDING)
        val decoded = RC2.decryptAsString(keyBytes, encoded.iv, encoded.data, RC2.TYPE.RC2_CBC_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun rC2_CBC_PKCS5_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = RC2.encrypt(keyBytes, data, RC2.TYPE.RC2_CBC_PKCS5_PADDING)
        val decoded = RC2.decryptAsString(keyBytes, encoded.iv, encoded.data, RC2.TYPE.RC2_CBC_PKCS5_PADDING)
        assertEquals(data, decoded)
    }

}