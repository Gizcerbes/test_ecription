package com.uogamse.sequre

import com.uogames.sequre.encoder.AES3
import org.junit.jupiter.api.Test
import java.security.SecureRandom
import kotlin.test.assertEquals

class AES3Test {

    @Test
    fun global(){
        AES3.TYPE.entries.forEach { typeTest(it) }
    }

    @Test
    fun aES_CBC_NO_PADDING_Test() {
        val type = AES3.TYPE.AES_CBC_NO_PADDING
        val keyBytes = ByteArray(16) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = AES3.aesEncrypt(keyBytes, iv, data, type)
        val decoded = AES3.aesDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_CBC_PKCS_5_PADDING_Test() {
        val type = AES3.TYPE.AES_CBC_PKCS_5_PADDING
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello worldгнеаh gm mfmd mmdf mf mkdf kmdf  агшпе пилгп обрбм"
        val iv = type.generateIV()
        val encoded = AES3.aesEncrypt(keyBytes, iv, data, type)
        val decoded = AES3.aesDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_ECB_NO_PADDING_Test() {
        val type = AES3.TYPE.AES_ECB_NO_PADDING
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello world ioh iuhigiuguggoi iojoij op в sj"
        val iv = type.generateIV()
        val encoded = AES3.aesEncrypt(keyBytes, iv, data, type)
        val decoded = AES3.aesDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_ECB_PKCS_5_PADDING_Test() {
        val type = AES3.TYPE.AES_ECB_PKCS_5_PADDING
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello world шг п аег8еп шлпгшдгш гшпд"
        val iv = type.generateIV()
        val encoded = AES3.aesEncrypt(keyBytes, iv, data, type)
        val decoded = AES3.aesDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_AES_GCM_NO_PADDING_Test() {
        val type = AES3.TYPE.AES_GCM_NO_PADDING
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello world шг п аег8еп шлпгшдгш гшпд"
        val iv = type.generateIV()
        val encoded = AES3.aesEncrypt(keyBytes, iv, data, type)
        val decoded = AES3.aesDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_AES_CTR_NO_PADDING_Test() {
        val type = AES3.TYPE.AES_CTR_NO_PADDING
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello world шг п аег8еп шлпгшдгш гшпд"
        val iv = type.generateIV()
        val encoded = AES3.aesEncrypt(keyBytes, iv, data, type)
        val decoded = AES3.aesDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    private fun typeTest(type: AES3.TYPE){
        val keyBytes = ByteArray(24).apply { SecureRandom().nextBytes(this) }
        val data = ByteArray(1000).apply { SecureRandom().nextBytes(this) }
        val iv = type.generateIV()
        val encoded = AES3.aesEncrypt(keyBytes, iv, data, type)
        val decoded = AES3.aesDecrypt(keyBytes, iv, encoded, type)
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