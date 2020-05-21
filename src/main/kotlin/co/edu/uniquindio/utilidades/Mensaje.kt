package co.edu.uniquindio.utilidades

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert.AlertType.*
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import java.util.*

/**
 * @author Yesid Shair Rosas Toro
 * @author Samara Smith Rincon Montaña
 * @author Juan David Usma Alzate
 *
 * @version 1.0
 *
 * Mensaje
 */
class Mensaje {
    companion object {
        /**
         * Muestra una alerta de mensaje en la pantalla y espera
         *
         * @param tipo El tipo de alerta que se desea mostrar
         * @param titulo El titulo de la ventana de la alerta
         * @param cabecera La cabecera de la alerta
         * @param contenido El contenido de la alerta
         */
        @JvmStatic
        private fun mensaje(tipo: AlertType, titulo: String, cabecera: String, contenido: String?) {
            val alertaAcerca = Alert(tipo)

            alertaAcerca.title = titulo
            alertaAcerca.headerText = cabecera
            alertaAcerca.contentText = contenido

            alertaAcerca.showAndWait()
        }

        /**
         * Muestra una alerta de mensaje en la pantalla
         *
         * @param excepcion La excepcion de la que se quiere mostrar informacion
         * @param tipo El tipo de alerta que se desea mostrar
         * @param titulo El titulo de la ventana de la alerta
         */
        @JvmStatic
        private fun mensaje(excepcion: Exception, tipo: AlertType, titulo: String) {
            mensaje(tipo, titulo, excepcion.javaClass.simpleName, excepcion.message)
        }

        /**
         * Muestra un mensaje de informacion en la pantalla
         *
         * @param cabecera La cabecera del mensaje
         * @param contenido El contenido del mensaje
         */
        @JvmStatic
        fun informacion(cabecera: String, contenido: String?) {
            mensaje(INFORMATION, "Informacion", cabecera, contenido)
        }

        /**
         * Muestra un mensaje de advertencia en la pantalla
         *
         * @param excepcion La excepcion de la que se va a extraer la informacion
         */
        @JvmStatic
        fun advertencia(excepcion: Exception) {
            mensaje(excepcion, WARNING, "Advertencia")
        }

        /**
         * Muestra un mensaje de error en la pantalla
         *
         * @param excepcion La excepcion de la que se va a extraer la informacion
         */
        @JvmStatic
        fun error(excepcion: Exception) {
            mensaje(excepcion, ERROR, "Error")
        }

        /**
         * Muestra una ventana de pregunta en la pantalla
         *
         * @param pregunta La pregunta que se desea mostrar en pantalla
         *
         * @return Boolean El resultado booleano de la opcion pulsada
         */
        @JvmStatic
        fun preguntar(pregunta: String): Boolean {
            val alertaPregunta = Alert(CONFIRMATION)

            alertaPregunta.title = "Confirmacion"
            alertaPregunta.headerText = null
            alertaPregunta.contentText = pregunta

            val decision = alertaPregunta.showAndWait()

            return decision.get().buttonData == ButtonBar.ButtonData.OK_DONE
        }
    }
}