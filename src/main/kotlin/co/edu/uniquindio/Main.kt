package co.edu.uniquindio

import co.edu.uniquindio.controlador.VentanaPrincipalController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {

    /**
     * Metodo start de JavaFX
     *
     * Inicializacion de la GUI
     */
    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(VentanaPrincipalController::class.java.getResource("../vista/VentanaPrincipal.fxml"))
        val parent: Parent = loader.load()

        val scene = Scene(parent)

        primaryStage?.scene = scene
        primaryStage?.title = "Compilador"
        primaryStage?.show()
    }

    /**
     * Metodo main
     */
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}