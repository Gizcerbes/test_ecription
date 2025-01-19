package com.uogamse.sequre

import com.uogames.sequre.encoder.DES
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DESTest {

    @Test
    fun dES_CBC_NO_PADDING() {
        val keyBytes = ByteArray(DES.TYPE.DES_CBC_NO_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DES.desEncrypt(keyBytes, data, DES.TYPE.DES_CBC_NO_PADDING)
        val decoded = DES.desDecryptAsString(keyBytes, encoded.iv, encoded.data, DES.TYPE.DES_CBC_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun dES_CBC_PKCS_5_PADDING(){
        val keyBytes = ByteArray(DES.TYPE.DES_CBC_PKCS_5_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DES.desEncrypt(keyBytes, data, DES.TYPE.DES_CBC_PKCS_5_PADDING)
        val decoded = DES.desDecryptAsString(keyBytes, encoded.iv, encoded.data, DES.TYPE.DES_CBC_PKCS_5_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun dES_ECB_NO_PADDING(){
        val keyBytes = ByteArray(DES.TYPE.DES_ECB_NO_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DES.desEncrypt(keyBytes, data, DES.TYPE.DES_ECB_NO_PADDING)
        val decoded = DES.desDecryptAsString(keyBytes, encoded.iv, encoded.data, DES.TYPE.DES_ECB_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun dES_ECB_PKCS_5_PADDING(){
        val keyBytes = ByteArray(DES.TYPE.DES_ECB_PKCS_5_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DES.desEncrypt(keyBytes, data, DES.TYPE.DES_ECB_PKCS_5_PADDING)
        val decoded = DES.desDecryptAsString(keyBytes, encoded.iv, encoded.data, DES.TYPE.DES_ECB_PKCS_5_PADDING)
        assertEquals(data, decoded)
    }

}