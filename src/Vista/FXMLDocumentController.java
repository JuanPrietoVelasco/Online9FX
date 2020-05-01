/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import static Controlador.Utilidades.*;
import Modelo.AlquilerVehiculos;
import Controlador.Main;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;


public class FXMLDocumentController implements Initializable {

    @FXML
    MenuBar myMenuBar;

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

    public void mostrarClientes(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("ClientesVista.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVehiculos(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("VehiculosVista.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarAlquileres(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AlquileresVista.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void leerTxt(ActionEvent event) throws FileNotFoundException {
        Controlador.Main.leerDatos("");
        System.out.println("Datos cargados correctamente"); //PARA LOG - QUITAR
        informacion("Datos cargados correctamente");
    }

    @FXML
    void crearCopiaSeg(ActionEvent event) {
        AlquilerVehiculos.crearCopiaSeg();
    }

    @FXML
    void leerCopiaSeg(ActionEvent event) {
        Controlador.Main.vaciarArrays();
        Controlador.Main.cargarCopiaSeg();
    }

    @FXML
    void crearXML(ActionEvent event) {
        Controlador.Main.guardarDatosXML();
    }

    @FXML
    void leerXML(ActionEvent event) {
        Controlador.Main.vaciarArrays();
        Controlador.Main.leerDatosXML();
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

    }

}
