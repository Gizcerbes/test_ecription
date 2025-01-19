package com.uogamse.sequre

import com.uogames.sequre.encoder.CAST128
import com.uogames.sequre.encoder.DES
import com.uogames.sequre.encoder.IDEA
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class CAST128Test {

    @Test
    fun iDEA() {
        val keyBytes = ByteArray(Random.nextInt(100) + 1) { it.toByte() }
        val data = "Hello world"
        val encoded = CAST128.encrypt(keyBytes, data, CAST128.TYPE.CAST128)
        val decoded = CAST128.decryptAsString(keyBytes, encoded.iv, encoded.data, CAST128.TYPE.CAST128)
        assertEquals(data, decoded)
    }

}