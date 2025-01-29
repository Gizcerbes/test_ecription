package com.uogamse.sequre

import com.uogames.sequre.encoder.IDEA
import io.ktor.util.*
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class IDEATest {

    @Test
    fun iDEA() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world gfg fg gfg hnhn nhgn nfgn g gfg fg"
        val encoded = IDEA.encrypt(keyBytes, data, IDEA.TYPE.IDEA)
        val decoded = IDEA.decryptAsString(keyBytes, encoded.iv, encoded.data, IDEA.TYPE.IDEA)
        assertEquals(data, decoded)
    }

    @Test
    fun iDEA_ECB_NO_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = IDEA.encrypt(keyBytes, data, IDEA.TYPE.IDEA_ECB_NO_PADDING)
        val decoded = IDEA.decryptAsString(keyBytes, encoded.iv, encoded.data, IDEA.TYPE.IDEA_ECB_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun iDEA_ECB_PKCS5_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = IDEA.encrypt(keyBytes, data, IDEA.TYPE.IDEA_ECB_PKCS5_PADDING)
        val decoded = IDEA.decryptAsString(keyBytes, encoded.iv, encoded.data, IDEA.TYPE.IDEA_ECB_PKCS5_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun iDEA_CBC_NO_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = IDEA.encrypt(keyBytes, data, IDEA.TYPE.IDEA_CBC_NO_PADDING)
        val decoded = IDEA.decryptAsString(keyBytes, encoded.iv, encoded.data, IDEA.TYPE.IDEA_CBC_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun iDEA_CBC_PKCS5_PADDING() {
        val keyBytes = ByteArray(Random.nextInt(200) + 1) { it.toByte() }
        val data = "Hello worldvcv frf df d fgdg f"
        val encoded = IDEA.encrypt(keyBytes, data, IDEA.TYPE.IDEA_CBC_PKCS5_PADDING)
        val decoded = IDEA.decryptAsString(keyBytes, encoded.iv, encoded.data, IDEA.TYPE.IDEA_CBC_PKCS5_PADDING)
        assertEquals(data, decoded)
    }


}