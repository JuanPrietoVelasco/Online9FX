/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.AlquilerVehiculos;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author juans
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private void cerrarPrograma(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void mostrarAcercaDe(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AcercaDe.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Acerca de");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("No se puede cargar la nueva ventana");
        }
    }

    @FXML
    void leerTxt(ActionEvent event) throws FileNotFoundException {
        AlquilerVehiculos.leerDatos("");
        System.out.println("Datos cargados correctamente");
    }
    
    @FXML
    void crearCopiaSegFX(ActionEvent event) {
        AlquilerVehiculos.crearCopiaSeg();
    }
    
    @FXML
    void leerCopiaSeg(ActionEvent event) {
        AlquilerVehiculos.vaciarArrays();
        AlquilerVehiculos.cargarCopiaSeg();
    }
    
    @FXML
    void crearXML(ActionEvent event) {
        AlquilerVehiculos.guardarDatosXML();
    }
    
    @FXML
    void leerXML(ActionEvent event) {
        AlquilerVehiculos.vaciarArrays();
        AlquilerVehiculos.leerDatosXML();
    }
    
    @FXML
    void anadirCliente(ActionEvent event) {
        AlquilerVehiculos.anadirCliente();
    }
    
    @FXML
    void borrarCliente(ActionEvent event) {
        AlquilerVehiculos.borrarCliente();
    }
    
    @FXML
    void listarClientes(ActionEvent event) {
        AlquilerVehiculos.listarClientes();
    }
    
    @FXML
    void anadirVehiculo(ActionEvent event) {
        AlquilerVehiculos.anadirVehiculo();
    }

    @FXML
    void borrarVehiculo(ActionEvent event) {
        AlquilerVehiculos.borrarVehiculo();
    }
    
     @FXML
    void listarVehiculos(ActionEvent event) {
        AlquilerVehiculos.listarVehiculos();
    }
    
     @FXML
    void anadirAlquiler(ActionEvent event) {
        AlquilerVehiculos.nuevoAlquiler();
    }
    
     @FXML
    void ceerrarAlquiler(ActionEvent event) {
        AlquilerVehiculos.cerrarAlquiler();
    }
    
     @FXML
    void listarAlquileres(ActionEvent event) {
        AlquilerVehiculos.listarAlquileres();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
