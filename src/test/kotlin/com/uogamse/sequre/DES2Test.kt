package com.uogamse.sequre

import com.uogames.sequre.encoder.DES2
import org.junit.jupiter.api.Test
import java.security.SecureRandom
import kotlin.test.assertEquals

class DES2Test {

    @Test
    fun global(){
        DES2.TYPE.entries.forEach { typeTest(it) }
    }

    @Test
    fun dES_CBC_NO_PADDING() {
        val type = DES2.TYPE.DES_CBC_NO_PADDING
        val keyBytes = ByteArray(8) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DES2.desEncrypt(keyBytes, iv, data, type)
        val decoded = DES2.desDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun dES_CBC_PKCS_5_PADDING(){
        val type = DES2.TYPE.DES_CBC_PKCS_5_PADDING
        val keyBytes = ByteArray(8) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DES2.desEncrypt(keyBytes, iv, data, type)
        val decoded = DES2.desDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun dES_ECB_NO_PADDING(){
        val type = DES2.TYPE.DES_ECB_NO_PADDING
        val keyBytes = ByteArray(8) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DES2.desEncrypt(keyBytes, iv, data, type)
        val decoded = DES2.desDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun dES_ECB_PKCS_5_PADDING(){
        val type = DES2.TYPE.DES_ECB_PKCS_5_PADDING
        val keyBytes = ByteArray(8) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DES2.desEncrypt(keyBytes, iv, data, type)
        val decoded = DES2.desDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    private fun typeTest(type: DES2.TYPE){
        val keyBytes = ByteArray(8).apply { SecureRandom().nextBytes(this) }
        val data = ByteArray(1000).apply { SecureRandom().nextBytes(this) }
        val iv = type.generateIV()
        val encoded = DES2.desEncrypt(keyBytes, iv, data, type)
        val decoded = DES2.desDecrypt(keyBytes, iv, encoded, type)
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