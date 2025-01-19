package com.uogamse.sequre

import com.uogames.sequre.encoder.DESede
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DESedeTest {

    @Test
    fun dESEDE_CBC_NO_PADDING(){
        val keyBytes = ByteArray(DESede.TYPE.DESEDE_CBC_NO_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DESede.desedeEncrypt(keyBytes, data, DESede.TYPE.DESEDE_CBC_NO_PADDING)
        val decoded = DESede.desedeDecryptAsString(keyBytes, encoded.iv, encoded.data, DESede.TYPE.DESEDE_CBC_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun dESEDE_CBC_PKCS_5_PADDING(){
        val keyBytes = ByteArray(DESede.TYPE.DESEDE_CBC_PKCS_5_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DESede.desedeEncrypt(keyBytes, data, DESede.TYPE.DESEDE_CBC_PKCS_5_PADDING)
        val decoded = DESede.desedeDecryptAsString(keyBytes, encoded.iv, encoded.data, DESede.TYPE.DESEDE_CBC_PKCS_5_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun dESEDE_ECB_NO_PADDING(){
        val keyBytes = ByteArray(DESede.TYPE.DESEDE_ECB_NO_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DESede.desedeEncrypt(keyBytes, data, DESede.TYPE.DESEDE_ECB_NO_PADDING)
        val decoded = DESede.desedeDecryptAsString(keyBytes, encoded.iv, encoded.data, DESede.TYPE.DESEDE_ECB_NO_PADDING)
        assertEquals(data, decoded)
    }

    @Test
    fun dESEDE_ECB_PKCS_5_PADDING(){
        val keyBytes = ByteArray(DESede.TYPE.DESEDE_ECB_PKCS_5_PADDING.size) { it.toByte() }
        val data = "Hello world"
        val encoded = DESede.desedeEncrypt(keyBytes, data, DESede.TYPE.DESEDE_ECB_PKCS_5_PADDING)
        val decoded = DESede.desedeDecryptAsString(keyBytes, encoded.iv, encoded.data, DESede.TYPE.DESEDE_ECB_PKCS_5_PADDING)
        assertEquals(data, decoded)
    }

}