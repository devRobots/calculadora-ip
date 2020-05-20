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

    /**
     * Metodo para convertir la dirección IP a binario
     */
    fun convertirdirecciontoBin():String{
        var direccionBin=""
        var direccionSplit = direccionIp.split(".")

        for (i in direccionSplit){
            var aux = i.toInt()
            direccionBin += SistemasNumericos.decToBin(aux)
        }
        return direccionBin
    }

    fun deciABina(no: String): String {
        var n = no.toInt()
        var res = ""
        while (n > 0) {
            res = (n % 2).toString() + res
            n = n / 2
        }
        while (res.length < 8) {
            res = "0$res"
        }
        return res
    }

    fun binaADeci(n: String): String {
        var decimal = 0
        var binary = n.toInt()
        var power = 0
        while (binary != 0) {
            val lastDigit = binary % 10
            decimal += lastDigit * Math.pow(2.0, power.toDouble()).toInt()
            power++
            binary = binary / 10
        }
        return "" + decimal
    }

    fun mascaraBinCP(mascara: String): String? {
        var mascaraDec = ""
        val masca = mascara.toInt()
        var cont = 0
        for (i in 1 until masca + 1) {
            if (cont == 8 || cont == 16 || cont == 24) {
                mascaraDec += "."
            }
            mascaraDec += "1"
            cont++
        }
        while (mascaraDec.length < 35) {
            if (cont == 8 || cont == 16 || cont == 24) {
                mascaraDec += "."
            }
            mascaraDec += "0"
            cont++
        }
        return mascaraDec
    }

    fun mascaraBinSP(mascara: String): String {
        var mascaraDec = ""
        val masca = mascara.toInt()
        for (i in 1 until masca + 1) {
            mascaraDec += "1"
        }
        while (mascaraDec.length < 32) {
            mascaraDec += "0"
        }
        return mascaraDec
    }

    fun mascaraDec(mascaraBin: String): String? {
        var res = ""
        val lala = mascaraBin.split("\\.").toTypedArray()
        val le1 = binaADeci(lala[0])
        val le2 = binaADeci(lala[1])
        val le3 = binaADeci(lala[2])
        val le4 = binaADeci(lala[3])
        res += "$le1.$le2.$le3.$le4"
        return res
    }

    fun IpABinCP(Ip: String): String? {
        var IPbin = ""
        val lerolero = Ip.split("\\.").toTypedArray()
        val le1 = deciABina(lerolero[0])
        val le2 = deciABina(lerolero[1])
        val le3 = deciABina(lerolero[2])
        val le4 = deciABina(lerolero[3])
        IPbin += "$le1.$le2.$le3.$le4"
        return IPbin
    }

    fun IpABinSP(Ip: String): String {
        var IPbin = ""
        val lerolero = Ip.split("\\.").toTypedArray()
        val le1 = deciABina(lerolero[0])
        val le2 = deciABina(lerolero[1])
        val le3 = deciABina(lerolero[2])
        val le4 = deciABina(lerolero[3])
        IPbin += le1 + le2 + le3 + le4
        return IPbin
    }

    fun direccionRed(ip: String, mascara: String): String {
        var res = ""
        val ipla = IpABinSP(ip)
        val maskla = mascaraBinSP(mascara)
        for (i in 0 until maskla.length) {
            val ba = "" + ipla[i]
            val be = "" + maskla[i]
            res += ba.toInt() * be.toInt()
        }
        var resi = ""
        var cont = 0
        for (i in 0..31) {
            if (cont == 8 || cont == 16 || cont == 24) {
                resi += "."
            }
            resi += "" + res[i]
            cont++
        }
        var resu = ""
        val lala = resi.split("\\.").toTypedArray()
        val le1 = binaADeci(lala[0])
        val le2 = binaADeci(lala[1])
        val le3 = binaADeci(lala[2])
        val le4 = binaADeci(lala[3])
        resu += "$le1.$le2.$le3.$le4"
        return resu
    }

    fun direccionBroadcast(ip: String, mascara: String): String {
        val ipla = IpABinSP(ip)
        var broad = ""
        for (i in 0 until mascara.toInt()) {
            broad += "" + ipla[i]
        }
        while (broad.length < 32) {
            broad += "1"
        }
        var resi = ""
        var cont = 0
        for (i in 0..31) {
            if (cont == 8 || cont == 16 || cont == 24) {
                resi += "."
            }
            resi += "" + broad[i]
            cont++
        }
        var resu = ""
        val lala = resi.split("\\.").toTypedArray()
        val le1 = binaADeci(lala[0])
        val le2 = binaADeci(lala[1])
        val le3 = binaADeci(lala[2])
        val le4 = binaADeci(lala[3])
        resu += "$le1.$le2.$le3.$le4"
        return resu
    }

    fun rango(ip: String, mascara: String): String? {
        var algo = ""
        var menor = ""
        var mayor = ""
        val red = direccionRed(ip, mascara)
        val broad = direccionBroadcast(ip, mascara)
        val lala = red.split("\\.").toTypedArray()
        val lele = broad.split("\\.").toTypedArray()
        menor += if (lala[3].toInt() + 1 <= 255) {
            lala[0] + "." + lala[1] + "." + lala[2] + "." + (lala[3].toInt() + 1)
        } else {
            lala[0] + "." + lala[1] + "." + lala[2].toInt() + 1 + ".0"
        }
        mayor += if (lele[3].toInt() - 1 >= 0) {
            lele[0] + "." + lele[1] + "." + lele[2] + "." + (lele[3].toInt() - 1)
        } else {
            lele[0] + "." + lele[1] + "." + (lele[2].toInt() - 1) + ".255"
        }
        algo = "[$menor - $mayor]"
        return algo
    }
}
