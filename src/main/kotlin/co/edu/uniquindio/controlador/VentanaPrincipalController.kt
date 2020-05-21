package co.edu.uniquindio.controlador

import co.edu.uniquindio.excepciones.CampoVacioExcepcion
import co.edu.uniquindio.modelo.CalculadoraIP
import co.edu.uniquindio.modelo.SistemasNumericos
import co.edu.uniquindio.utilidades.Mensaje
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.KeyEvent
import java.lang.Exception
import kotlin.system.exitProcess

/**
 * @author Yesid Shair Rosas Toro
 * @author Samara Smith Rincon Montaña
 * @author Juan David Usma Alzate
 *
 * @version 1.0
 *
 * VentanaPrincipalController
 */
class VentanaPrincipalController {
    /**
     * Elementos base de JavaFX
     */
    @FXML
    private lateinit var tabPanel: TabPane

    /**
     * Elementos de calculadora IP
     */
    @FXML
    private lateinit var txtDireccionIp: TextField
    @FXML
    private lateinit var lblDireccionRed: Label
    @FXML
    private lateinit var lblMascara: Label
    @FXML
    private lateinit var lblDireccionBroadcast: Label
    @FXML
    private lateinit var lblNumBitsRed: Label
    @FXML
    private lateinit var lblNumBitsHosts: Label
    @FXML
    private lateinit var lblCantHosts: Label
    @FXML
    private lateinit var lblRangoHosts: Label
    @FXML
    private lateinit var lstHosts: ListView<String>
    @FXML
    private lateinit var btnCalcularIP: Button

    /**
     * Elementos de conversion numerica
     */
    @FXML
    private lateinit var sistNumSeleccion: ToggleGroup
    @FXML
    private lateinit var txtDecimalNum: TextField
    @FXML
    private lateinit var txtBinarioNum: TextField
    @FXML
    private lateinit var txtHexadecimalNum: TextField

    /**
     * Metodo initialize de JavaFX
     *
     * Utilizado para definir acciones de los ToggleButtons
     */
    @FXML
    fun initialize() {
        sistNumSeleccion.selectedToggleProperty().addListener { _, _, newValue ->
            when ((newValue as RadioButton).text) {
                "Decimal" -> {
                    txtDecimalNum.disableProperty().set(false)
                    txtBinarioNum.disableProperty().set(true)
                    txtHexadecimalNum.disableProperty().set(true)
                }
                "Binario" -> {
                    txtDecimalNum.disableProperty().set(true)
                    txtBinarioNum.disableProperty().set(false)
                    txtHexadecimalNum.disableProperty().set(true)
                }
                "Hexadecimal" -> {
                    txtDecimalNum.disableProperty().set(true)
                    txtBinarioNum.disableProperty().set(true)
                    txtHexadecimalNum.disableProperty().set(false)
                }
            }
        }
    }

    /**
     * Realiza los calculos solicitados en el punto 2.1 y 2.2 del Proyecto
     */
    @FXML
    fun calcularIP() {
        try {
            val calculadora = CalculadoraIP(txtDireccionIp.text)

            lblDireccionRed.text = calculadora.obtenerDireccionRed()
            lblMascara.text = calculadora.obtenerMascaraDecimal()
            lblDireccionBroadcast.text = calculadora.obtenerDireccionBroadcast()

            lblCantHosts.text = calculadora.obtenerCantidadHosts().toString()
            lblNumBitsHosts.text = calculadora.obtenerNumeroBitsHosts().toString()
            lblNumBitsRed.text = calculadora.obtenerNumeroBitsRed().toString()

            lblRangoHosts.text = calculadora.obtenerRangoDireccionesHost()?.toString() ?: "No existe un rango valido"

            if (calculadora.obtenerRangoDireccionesHost() != null) {
                if (calculadora.obtenerCantidadHosts() > 1048574) {
                    if (Mensaje.preguntar("La mascara es demasiado pequeña, esto podria ocasionar que el proceso demore mas de lo esperado. ¿Desea continuar?")) {
                        Thread(Runnable {
                            kotlin.run {
                                synchronized(lstHosts.items) {
                                    lstHosts.items.clear()
                                    txtDireccionIp.disableProperty().set(true)
                                    btnCalcularIP.disableProperty().set(true)
                                    val lista = calculadora.obtenerDireccionesHosts()!!
                                    txtDireccionIp.disableProperty().set(false)
                                    btnCalcularIP.disableProperty().set(false)
                                    val observableList = FXCollections.observableList(lista)
                                    lstHosts.itemsProperty().set(observableList)
                                    lstHosts.refresh()
                                }
                            }
                        }).start()
                    }
                } else {
                    lstHosts.items.clear()
                    val lista = calculadora.obtenerDireccionesHosts()
                    val observableList = FXCollections.observableList(lista)
                    lstHosts.items = observableList
                    lstHosts.refresh()
                }
            } else {
                lstHosts.items.clear()
                lstHosts.refresh()
            }
        } catch (ex: Exception) {
            Mensaje.error(ex)
        }
    }


    /**
     * Valida la pulsacion de los numeros ingresados en la pestaña de conversiones numericas
     */
    @FXML
    fun baseNumericaPulsada(event: KeyEvent?) {
        val c = event!!.character.toUpperCase()[0]
        val rangBin = '0'.rangeTo('1')
        val rangHex = 'A'.rangeTo('F')

        val flag = when (event.source) {
            txtDecimalNum -> txtDecimalNum.length > 8 || !Character.isDigit(c)
            txtBinarioNum -> txtBinarioNum.length > 30 || c !in rangBin
            txtHexadecimalNum -> txtHexadecimalNum.length > 6 || !(Character.isDigit(c) || c in rangHex)
            else -> false
        }

        if (flag) event.consume()
    }

    /**
     * Valida y realiza las conversiones numericas desde el sistema numerico seleccionado
     */
    @FXML
    fun conversionNumerica() {
        when ((sistNumSeleccion.selectedToggle as RadioButton).text) {
            "Decimal" -> {
                val decString = txtDecimalNum.text
                if (decString.isNotBlank()) {
                    val dec = Integer.parseInt(decString)
                    txtBinarioNum.text = SistemasNumericos.toBinString(dec)
                    txtHexadecimalNum.text = SistemasNumericos.toHexString(dec).toUpperCase()
                } else {
                    Mensaje.advertencia(CampoVacioExcepcion("Por favor llene el campo de texto"))
                }
            }
            "Binario" -> {
                val binString = txtBinarioNum.text
                if (binString.isNotBlank()) {
                    val bin = Integer.parseInt(binString, 2)
                    val dec = SistemasNumericos.binToDec(bin)
                    txtDecimalNum.text = dec.toString()
                    txtHexadecimalNum.text = SistemasNumericos.toHexString(dec).toUpperCase()
                } else {
                    Mensaje.advertencia(CampoVacioExcepcion("Por favor llene el campo de texto"))
                }
            }
            "Hexadecimal" -> {
                val hexString = txtHexadecimalNum.text
                if (hexString.isNotBlank()) {
                    val hex = Integer.parseInt(hexString, 16)
                    val dec = SistemasNumericos.hexToDec(hex)
                    txtDecimalNum.text = dec.toString()
                    txtBinarioNum.text = SistemasNumericos.toBinString(dec)
                } else {
                    Mensaje.advertencia(CampoVacioExcepcion("Por favor llene el campo de texto"))
                }
            }
        }
    }

    /**
     * Cierra la aplicacion
     */
    @FXML
    fun cerrar() {
        exitProcess(0)
    }

    /**
     * Limpia los campos del panel actual
     */
    @FXML
    fun limpiar() {
        if (tabPanel.selectionModel.selectedIndex == 0) {
            txtDireccionIp.clear()

            lblDireccionRed.text =          "XXX.XXX.XXX.XXX"
            lblMascara.text =               "XXX.XXX.XXX.XXX"
            lblDireccionBroadcast.text =    "XXX.XXX.XXX.XXX"

            lblCantHosts.text =             "-"
            lblNumBitsHosts.text =          "-"
            lblNumBitsRed.text =            "-"

            lblRangoHosts.text =            "[,]"

            val observableList = FXCollections.emptyObservableList<String>()
            lstHosts.items = observableList
            lstHosts.refresh()
        } else {
            txtDecimalNum.clear()
            txtBinarioNum.clear()
            txtHexadecimalNum.clear()
        }
    }

    /**
     * Muestra informacion de la aplicacion
     */
    @FXML
    fun acerca() {
        val titulo = "Calculadora IP"
        val contenido = "Creado por:\n\n* Yesid Rosas\n* Juan David Usma\n* Samara Rincon"
        Mensaje.informacion(titulo, contenido)
    }
}