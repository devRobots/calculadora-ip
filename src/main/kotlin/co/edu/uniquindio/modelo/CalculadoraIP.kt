package co.edu.uniquindio.modelo

import kotlin.math.pow
import co.edu.uniquindio.excepciones.*

/**
 * @author Yesid Shair Rosas Toro
 * @author Samara Smith Rincon Montaña
 * @author Juan David Usma Alzate
 *
 * @version 1.0
 *
 * CalculadoraIP
 */
class CalculadoraIP(ipFormat: String) {
    /**
     * Direccion Ip del Host
     */
    private lateinit var direccionIp: String

    /**
     * Mascara de la red en formato simplificado
     */
    private var mascara: Int = 24

    init {
        extraerIpYMascara(ipFormat)
    }

    /**
     * Metodo que extrae la direccion IP y la mascara a partir
     * de una cadena dada
     *
     * @param ipConMascara La direccion ip con mascara en formato simplificado
     *
     * @throws CampoVacioExcepcion Excepcion que es lanzada cuando un campo necesario no es rellenado
     * @throws DireccionIPInvalidaExcepcion Excepcion que es lanzada cuando se introduce una direccion IP incorrecta
     * @throws MascaraInvalidaExcepcion Excepcion que es lanzada cuando se introduce una mascara de red incorrecta
     */
    @Throws(CampoVacioExcepcion::class, DireccionIPInvalidaExcepcion::class, MascaraInvalidaExcepcion::class)
    private fun extraerIpYMascara(ipConMascara: String) {
        if(ipConMascara.isNotBlank()) {
            if (ipConMascara.contains("/")) {
                val ipSplited = ipConMascara.split("/")
                if (ipSplited[0].isNotBlank()) {
                    direccionIp = ipSplited[0]
                    if (ipSplited[1].isNotBlank()) {
                        try {
                            mascara = ipSplited[1].toInt()

                            if (mascara > 32 || mascara < 0) {
                                throw MascaraInvalidaExcepcion("La mascara debe estar dentro del rango [8,32]")
                            }
                        } catch (ex: Exception) {
                            throw MascaraInvalidaExcepcion("La mascara debe ser ingresada en formato simplificado")
                        }
                    } else {
                        throw MascaraInvalidaExcepcion("La mascara de la red ingresada no es valida")
                    }
                } else {
                    throw DireccionIPInvalidaExcepcion("La direccion IP ingresada no es valida")
                }
            } else {
                throw MascaraInvalidaExcepcion("Por favor, ingrese la mascara de red en formato simplificado")
            }
        } else {
            throw CampoVacioExcepcion("Ingrese la direccion IP y la mascara de red")
        }
    }

    /**
     * Metodo para obtener todas las direcciones de host disponibles
     * en la red
     *
     * @return ArrayList<String> Direcciones de host diponibles
     */
    fun obtenerDireccionesHosts(): ArrayList<String>? {
        val cantHosts = obtenerCantidadHosts()

        if (cantHosts > 0) {
            val hosts = ArrayList<String>()

            val bitsRed = direccionIpABinario(direccionIp).substring(0, mascara)
            val bitsHost = obtenerNumeroBitsHosts()

            for (host in 1..cantHosts) {
                var bin = SistemasNumericos.toBinString(SistemasNumericos.decToBin(host))

                while (bin.length < bitsHost) {
                    bin = "0$bin"
                }

                val direccionHost = binarioADireccionIP(bitsRed + bin)
                hosts.add(direccionHost)
            }

            return hosts
        }

        return null
    }

    /**
     * Metodo para obtener el rango de direcciones de host en la red
     *
     * @return Array<String> El rango de direcciones host
     */
    fun obtenerRangoDireccionesHost(): ArrayList<String>? {
        val cantHost = obtenerCantidadHosts()

        if (cantHost > 0) {
            val direccionRed = obtenerDireccionRed()
            var binMinHost = direccionIpABinario(direccionRed)
            binMinHost = binMinHost.substring(0, binMinHost.length - 1) + '1'
            val minHost = binarioADireccionIP(binMinHost)

            val direccionBroadcast = obtenerDireccionBroadcast()
            var binMaxHost = direccionIpABinario(direccionBroadcast)
            binMaxHost = binMaxHost.substring(0, binMaxHost.length - 1) + '0'
            val maxHost = binarioADireccionIP(binMaxHost)

            return arrayListOf(minHost, maxHost)
        }

        return null
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
        var hosts = 2
        if (bitsHosts > 1) {
            hosts = 2.0.pow(bitsHosts).toInt()
        }
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

        for (i in ipBin.indices) redBin += if (i < mascara) {
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
            var bin = SistemasNumericos.toBinString(aux)

            while (bin.length < 8) {
                bin = "0$bin"
            }

            ipBin += bin
        }
        return ipBin
    }
}
