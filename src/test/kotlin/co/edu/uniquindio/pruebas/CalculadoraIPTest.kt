package co.edu.uniquindio.pruebas

import co.edu.uniquindio.modelo.CalculadoraIP
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Yesid Shair Rosas Toro
 * @author Samara Smith Rincon Montaña
 * @author Juan David Usma Alzate
 *
 * @version 1.0
 *
 * CalculadoraIPTest
 *
 * Pruebas Unitarias de la clase CalculadoraIP
 */
class CalculadoraIPTest {
    @Test
    fun obtenerDireccionesHosts() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerDireccionesHosts()

        val expected = 6
        assertEquals(expected, output?.size)
    }
    @Test
    fun obtenerRangoDireccionesHost() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerRangoDireccionesHost()

        val expected1 = "102.168.57.129"
        assertEquals(output?.get(0), expected1)

        val expected2 = "102.168.57.134"
        assertEquals(output?.get(1), expected2)
    }
    @Test
    fun obtenerNumeroBitsRed() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerNumeroBitsRed()

        val expected = 29
        assertEquals(expected, output)
    }
    @Test
    fun obtenerNumeroBitsHosts() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerNumeroBitsHosts()

        val expected = 3
        assertEquals(expected, output)
    }
    @Test
    fun obtenerCantidadHosts() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerCantidadHosts()

        val expected = 6
        assertEquals(expected, output)
    }
    @Test
    fun obtenerDireccionRed() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerDireccionRed()

        val expected = "102.168.57.128"
        assertEquals(expected, output)
    }
    @Test
    fun obtenerDireccionBroadcast() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerDireccionBroadcast()

        val expected = "102.168.57.135"
        assertEquals(expected, output)
    }
    @Test
    fun obtenerMascaraDecimal() {
        val input = "102.168.57.134/29"
        val output = CalculadoraIP(input).obtenerMascaraDecimal()

        val expected = "255.255.255.248"
        assertEquals(expected, output)
    }
}