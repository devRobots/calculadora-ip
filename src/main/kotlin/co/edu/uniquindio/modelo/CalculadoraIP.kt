package co.edu.uniquindio.modelo

import kotlin.math.pow

class CalculadoraIP(ipFormat: String) {
    /**
     * Direccion Ip del Host
     */
    private val direccionIp: String

    /**
     * Mascara de la red en formato simplificado
     */
    private val mascara: Int

    init {
        val ipSplited = ipFormat.split("/")
        direccionIp = ipSplited[0]
        mascara = ipSplited[1].toInt()
    }

    /**
     * Metodo para calcular el numero de bits de la red
     *
     * @return Int Numero de bits de la red
     */
    fun obtenerNumeroBitsRed() = mascara

    /**
     * Metodo para calcular el numero de bits de los hosts
     *
     * @return Int Numero de bits de los hosts
     */
    fun obtenerNumeroBitsHosts() = 32 - mascara

    /**
     * Metodo para calcular la cantidad de hosts diponibles en
     * una red a partir de la mascara
     *
     * @return Int Cantidad de hosts disponibles en la red
     */
    fun obtenerCantidadHosts(): Int {
        val bitsHosts = obtenerNumeroBitsHosts()
        val hosts = 2.0.pow(bitsHosts).toInt()
        return hosts - 2
    }

    /**
     * Metodo para extraer la direccion de red a partir de la mascara
     * y la direccion ip proporcionada
     *
     * @return String La direccion de red
     */
    fun obtenerDireccionRed(): String {
        var redBin = ""

        val ipBin = direccionIpABinario(direccionIp)
        val mascaraBin = mascaraABinario()

        for (i in ipBin.indices) redBin += if (mascaraBin[i] == '1') {
            ipBin[i]
        } else {
            '0'
        }

        return binarioADireccionIP(redBin)
    }

    /**
     * Metodo para extraer la direccion de broadcast a partir de la
     * mascara y la direccion ip proporcionada
     *
     * @return String La direccion de broadcast de la red
     */
    fun obtenerDireccionBroadcast(): String {
        var broadcastBin = ""

        val ipBin = direccionIpABinario(direccionIp)

        for (i in ipBin.indices) broadcastBin += if (i < mascara) {
            ipBin[i]
        } else {
            '1'
        }

        return binarioADireccionIP(broadcastBin)
    }

    /**
     * Metodo para convertir la version simplificada de la mascara
     * a formato decimal
     *
     * @return String La mascara en formato decimal
     */
    fun obtenerMascaraDecimal(): String {
        val bin = mascaraABinario()
        return binarioADireccionIP(bin)
    }

    /**
     * Metodo para convertir la version simplificada de la mascara
     * en formato binario
     *
     * @return String La mascara en formato binario
     */
    private fun mascaraABinario(): String {
        var binario = ""
        var bitsRestantes = mascara

        for (i in 0..31) binario += if (bitsRestantes > 0) {
            bitsRestantes--
            "1"
        } else {
            "0"
        }

        return binario
    }

    /**
     * Metodo para convertir un binario en direccion ip
     * en formato decimal
     *
     * @param binario La cadena binaria que se quiere convertir
     *
     * @return String La direccion IP en formato decimal
     */
    private fun binarioADireccionIP(binario: String): String {
        var mascaraDecimal = ""

        for (i in 0..3) {
            val bin = binario.substring((i * 8) + 0, (i * 8) + 8)
            mascaraDecimal += SistemasNumericos.binToDec(bin.toInt(2))
            if (i < 3) {
                mascaraDecimal += '.'
            }
        }

        return mascaraDecimal
    }

    /**
     * Metodo para convertir una dirección IP a binario
     *
     * @param ip La direccion ip que se quiere convertir a binario
     *
     * @return Int La representacion binaria de la direccion IP
     */
    private fun direccionIpABinario(ip: String): String {
        var ipBin = ""
        val segmentos = ip.split(".")

        for (segmento in segmentos) {
            val aux = segmento.toInt()
            var bin =  SistemasNumericos.toBinString(aux)

            while (bin.length < 8) {
                bin = "0$bin"
            }

            ipBin += bin
        }
        return ipBin
    }
}
