package com.uogamse.sequre

import com.uogames.sequre.encoder.AES
import com.uogames.sequre.encoder.AES2
import io.ktor.util.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AES2Test {

    @Test
    fun aES_CBC_NO_PADDING_Test() {
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello world gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val encoded = AES2.aesEncrypt(keyBytes, data, AES2.TYPE.AES_CBC_NO_PADDING)
        val decoded = AES2.aesDecryptAsString(keyBytes, encoded, AES2.TYPE.AES_CBC_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_CBC_PKCS_5_PADDING_Test() {
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello worldгнеа агшпе пилгп обрбм"
        val encoded = AES2.aesEncrypt(keyBytes, data, AES2.TYPE.AES_CBC_PKCS_5_PADDING)
        val decoded = AES2.aesDecryptAsString(keyBytes, encoded, AES2.TYPE.AES_CBC_PKCS_5_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_ECB_NO_PADDING_Test() {
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello world ioh iuhigiuguggoi iojoij op в sj"
        val encoded = AES2.aesEncrypt(keyBytes, data, AES2.TYPE.AES_ECB_NO_PADDING)
        val decoded = AES2.aesDecryptAsString(keyBytes, encoded, AES2.TYPE.AES_ECB_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun aES_ECB_PKCS_5_PADDING_Test() {
        val keyBytes = ByteArray(24) { it.toByte() }
        val data = "Hello world шг п аег8еп шлпгшдгш гшпд"
        val encoded = AES2.aesEncrypt(keyBytes, data, AES2.TYPE.AES_ECB_PKCS_5_PADDING)
        val decoded = AES2.aesDecryptAsString(keyBytes, encoded, AES2.TYPE.AES_ECB_PKCS_5_PADDING)
        assertEquals(data, decoded)
    }

}