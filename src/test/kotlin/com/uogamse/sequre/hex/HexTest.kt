package com.uogamse.sequre.hex

import com.uogames.sequre.Hex
import kotlin.test.Test
import kotlin.test.assertEquals

class HexTest {


    @Test
    fun tesGetLong() {

        repeat(5){
            val time = System.currentTimeMillis()
            repeat(10000000) {
                val randomLong = (Math.random() * if (Math.random() > 0.5) Long.MAX_VALUE else Long.MIN_VALUE).toLong()
                val hex = Hex(randomLong)
                assertEquals(randomLong, hex.getLong(0,7))

            }
            println("1 " + (System.currentTimeMillis() - time))

            val time2 = System.currentTimeMillis()
            repeat(10000000) {
                val randomLong = (Math.random() * if (Math.random() > 0.5) Long.MAX_VALUE else Long.MIN_VALUE).toLong()
                val hex = Hex(randomLong)
                assertEquals(randomLong, hex.getLong2(0,7))
            }
            println("2 " + (System.currentTimeMillis() - time2))
        }

    }

    @Test
    fun testGetInvertLong() {

        repeat(5){
            val time = System.currentTimeMillis()
            repeat(10000000) {
                val randomLong = (Math.random() * if (Math.random() > 0.5) Long.MAX_VALUE else Long.MIN_VALUE).toLong()
                val hex = Hex(randomLong)
                assertEquals(hex.getInvertLong(0,7), hex.getInvertLong2(0,7))
            }
            println("0 " + (System.currentTimeMillis() - time))
        }

        repeat(5){
            val time = System.currentTimeMillis()
            repeat(10000000) {
                val randomLong = (Math.random() * if (Math.random() > 0.5) Long.MAX_VALUE else Long.MIN_VALUE).toLong()
                val hex = Hex(randomLong)
                hex.getInvertLong(0,7)
            }
            println("1 " + (System.currentTimeMillis() - time))

            val time2 = System.currentTimeMillis()
            repeat(10000000) {
                val randomLong = (Math.random() * if (Math.random() > 0.5) Long.MAX_VALUE else Long.MIN_VALUE).toLong()
                val hex = Hex(randomLong)
                hex.getInvertLong2(0,7)
            }
            println("2 " + (System.currentTimeMillis() - time2))
        }

    }


}