package co.edu.uniquindio.utilidades

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert.AlertType.*

class Mensaje {
    companion object {
        @JvmStatic
        private fun mensaje(ex: Exception, tipo: AlertType, titulo: String) {
            mensaje(tipo, titulo, ex.javaClass.simpleName, ex.message)
        }

        @JvmStatic
        private fun mensaje(tipo: AlertType, titulo: String, cabecera: String, contenido: String?) {
            val alertaAcerca = Alert(tipo)

            alertaAcerca.title = titulo
            alertaAcerca.headerText = cabecera
            alertaAcerca.contentText = contenido

            alertaAcerca.showAndWait()
        }

        @JvmStatic
        fun informacion(cabecera: String, contenido: String?) {
            mensaje(INFORMATION, "Informacion", cabecera, contenido)
        }

        @JvmStatic
        fun advertencia(ex: Exception) {
            mensaje(ex, WARNING, "Advertencia")
        }

        @JvmStatic
        fun error(ex: Exception) {
            mensaje(ex, ERROR, "Error")
        }
    }
}