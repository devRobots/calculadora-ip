package co.edu.uniquindio.modelo

/**
 * @author Yesid Shair Rosas Toro
 * @author Samara Smith Rincon Montaña
 * @author Juan David Usma Alzate
 *
 * @version 1.0
 *
 * SistemasNumericos
 */
class SistemasNumericos {
    companion object {
        @JvmStatic
        fun toBinString(int: Int): String = Integer.toBinaryString(int)

        @JvmStatic
        fun toHexString(int: Int): String = Integer.toHexString(int)

        @JvmStatic
        fun decToBin(dec: Int): Int = Integer.parseInt(toBinString(dec), 2)

        @JvmStatic
        fun decToHex(dec: Int): Int = Integer.parseInt(toHexString(dec), 16)

        @JvmStatic
        fun binToDec(bin: Int): Int = bin.dec() + 1

        @JvmStatic
        fun binToHex(bin: Int): Int = decToHex(binToDec(bin))

        @JvmStatic
        fun hexToDec(hex: Int): Int = hex.dec() + 1

        @JvmStatic
        fun hexToBin(hex: Int): Int = decToBin(hexToDec(hex))
    }
}