package co.edu.uniquindio.pruebas

import kotlin.test.*
import org.junit.Test
import co.edu.uniquindio.modelo.SistemasNumericos

class SistemasNumericosTest {
    @Test
    fun toBinStringTest() {
        val input = 123
        val output: String = SistemasNumericos.toBinString(input)

        val expected = "1111011"
        assertEquals(expected, output)
    }
    @Test
    fun toHexStringTest() {
        val input = 123
        val output: String = SistemasNumericos.toHexString(input)

        val expected = "7b"
        assertEquals(expected, output)
    }
    @Test
    fun decToBinTest() {
        val input = 123
        val output: Int = SistemasNumericos.decToBin(input)

        val expected = 0b1111011
        assertEquals(expected, output)
    }
    @Test
    fun decToHexTest() {
        val input = 123
        val output: Int = SistemasNumericos.decToHex(input)

        val expected = 0x7b
        assertEquals(expected, output)
    }
    @Test
    fun binToDecTest() {
        val input = 0b111011011
        val output: Int = SistemasNumericos.binToDec(input)

        val expected = 475
        assertEquals(expected, output)
    }
    @Test
    fun binToHexTest() {
        val input = 0b111011011
        val output: Int = SistemasNumericos.binToHex(input)

        val expected = 0x1db
        assertEquals(expected, output)
    }
    @Test
    fun hexToDecTest() {
        val input = 0x5fba
        val output: Int = SistemasNumericos.hexToDec(input)

        val expected = 24506
        assertEquals(expected, output)
    }
    @Test
    fun hexToBinTest() {
        val input = 0x5fba
        val output: Int = SistemasNumericos.hexToBin(input)

        val expected = 0b101111110111010
        assertEquals(expected, output)
    }
}