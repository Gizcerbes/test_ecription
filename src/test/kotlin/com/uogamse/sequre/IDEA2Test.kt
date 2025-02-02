package com.uogamse.sequre

import com.uogames.sequre.encoder.IDEA2
import org.junit.jupiter.api.Test
import java.security.SecureRandom
import java.util.*
import kotlin.test.assertEquals

class IDEA2Test {

    @Test
    fun global() {
        IDEA2.TYPE.entries.forEach { typeTest(it) }
    }

    @Test
    fun iDEA() {
        val type = IDEA2.TYPE.IDEA
        typeTest(type)
    }

    @Test
    fun iDEA_ECB_NO_PADDING() {
        val type = IDEA2.TYPE.IDEA_ECB_NO_PADDING
        typeTest(type)
    }


    @Test
    fun iDEA_ECB_PKCS5_PADDING() {
        val type = IDEA2.TYPE.IDEA_ECB_PKCS5_PADDING
        typeTest(type)
    }

    @Test
    fun iDEA_CBC_NO_PADDING() {
        val type = IDEA2.TYPE.IDEA_CBC_NO_PADDING
        typeTest(type)
    }

    @Test
    fun iDEA_CBC_PKCS5_PADDING() {
        val type = IDEA2.TYPE.IDEA_CBC_PKCS5_PADDING
        typeTest(type)
    }

    @Test
    fun iDEA_CFB_NO_PADDING() {
        val type = IDEA2.TYPE.IDEA_CFB_NO_PADDING
        typeTest(type)
    }

    @Test
    fun iDEA_CFB_PKCS5_PADDING() {
        val type = IDEA2.TYPE.IDEA_CFB_PKCS5_PADDING
        typeTest(type)
    }

    @Test
    fun iDEA_OFB_NO_PADDING() {
        val type = IDEA2.TYPE.IDEA_OFB_NO_PADDING
        typeTest(type)
    }

    @Test
    fun iDEA_OFB_PKCS5_PADDING() {
        val type = IDEA2.TYPE.IDEA_OFB_PKCS5_PADDING
        typeTest(type)
    }


    private fun typeTest(type: IDEA2.TYPE) {
        val keyBytes = ByteArray(8 + Random().nextInt(500)).apply { SecureRandom().nextBytes(this) }
        val data = ByteArray(1000).apply { SecureRandom().nextBytes(this) }
        val iv = type.generateIV()
        val encoded = IDEA2.ideaEncrypt(keyBytes, iv, data, type)
        val decoded = IDEA2.ideaDecrypt(keyBytes, iv, encoded, type)
        assert(compare(data, decoded))
    }

    private fun compare(first: ByteArray, second: ByteArray): Boolean {
        if (first.size != second.size) return false
        val fi = first.iterator()
        val si = second.iterator()
        while (fi.hasNext()) if (fi.nextByte() != si.nextByte()) return false
        return true
    }


}