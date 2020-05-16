package co.edu.uniquindio.excepciones

import java.lang.Exception

open class Excepcion(mensaje : String) : Exception(mensaje) {
    lateinit var nombre : String
}