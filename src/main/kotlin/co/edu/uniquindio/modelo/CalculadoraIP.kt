package co.edu.uniquindio.modelo

class CalculadoraIP(ipFormat : String) {
    private val direccionIp : String
    private val mascara : Int

    init {
        val ipSplited = ipFormat.split("/")
        direccionIp = ipSplited[0]
        mascara = ipSplited[1].toInt()
    }

    fun maskToIpv4() : String {
        var maskIpV4 = ""
        var bitsRestantes = mascara
        var bin = 0b0

        for (bit in 0..31) {
            bin *= 0b10
            if (bitsRestantes > 0) {
                bin += 0b1
                bitsRestantes--
            }

            if ((bit + 1) % 8 == 0) {
                maskIpV4 += SistemasNumericos.binToDec(bin)
                if (bit < 31) {
                    maskIpV4 += "."
                }
                bin = 0b0
            }
        }

        return maskIpV4
    }
}