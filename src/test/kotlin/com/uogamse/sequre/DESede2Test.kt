package com.uogamse.sequre

import com.uogames.sequre.encoder.AES3
import com.uogames.sequre.encoder.DES2
import com.uogames.sequre.encoder.DESede
import com.uogames.sequre.encoder.DESede2
import org.junit.jupiter.api.Test
import java.security.SecureRandom
import kotlin.test.assertEquals

class DESede2Test {

    @Test
    fun global(){
        DESede2.TYPE.entries.forEach { typeTest(it) }
    }

    @Test
    fun dESEDE_CBC_NO_PADDING(){
        val type = DESede2.TYPE.DESede_CBC_NO_PADDING
        val keyBytes = ByteArray(16) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DESede2.desedeEncrypt(keyBytes, iv, data, type)
        val decoded = DESede2.desedeDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun dESEDE_CBC_PKCS_5_PADDING(){
        val type = DESede2.TYPE.DESede_CBC_PKCS_5_PADDING
        val keyBytes = ByteArray(16) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DESede2.desedeEncrypt(keyBytes, iv, data, type)
        val decoded = DESede2.desedeDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun dESEDE_ECB_NO_PADDING(){
        val type = DESede2.TYPE.DESede_ECB_NO_PADDING
        val keyBytes = ByteArray(16) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DESede2.desedeEncrypt(keyBytes, iv, data, type)
        val decoded = DESede2.desedeDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    @Test
    fun dESEDE_ECB_PKCS_5_PADDING(){
        val type = DESede2.TYPE.DESede_ECB_PKCS_5_PADDING
        val keyBytes = ByteArray(16) { it.toByte() }
        val data = "Hello wojj  uyguy uuygrld gf gfg fg fggrtgrb thntynbvgb gbb tbtb ty"
        val iv = type.generateIV()
        val encoded = DESede2.desedeEncrypt(keyBytes, iv, data, type)
        val decoded = DESede2.desedeDecryptAsString(keyBytes, iv, encoded, type)
        assertEquals(data, decoded)
    }

    private fun typeTest(type: DESede2.TYPE){
        val keyBytes = ByteArray(24).apply { SecureRandom().nextBytes(this) }
        val data = ByteArray(1000).apply { SecureRandom().nextBytes(this) }
        val iv = type.generateIV()
        val encoded = DESede2.desedeEncrypt(keyBytes, iv, data, type)
        val decoded = DESede2.desedeDecrypt(keyBytes, iv, encoded, type)
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