package co.edu.uniquindio.excepciones

class CampoVacioExcepcion(mensaje : String) : Excepcion(mensaje) {
    init {
        nombre = "Campo Vacio"
    }
}

