/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.Main.getVehiculo;
import static Controlador.Main.vehiculos;
import Modelo.*;
import Modelo.Enumerados.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class VehiculosVistaController implements Initializable {

    @FXML
    private ListView<String> listViewMatriculas;

    @FXML
    private TextField textFieldMatricula;

    @FXML
    private TextField textFieldMarca;

    @FXML
    private TextField textFieldModelo;

    @FXML
    private TextField textFieldCilindrada;

    @FXML
    private RadioButton radDeport;

    @FXML
    private RadioButton radFurgo;
    
    @FXML
    private RadioButton radFami;
    
    @FXML
    private ToggleGroup tipoVehiculo;

    @FXML
    private TextField txtFldNumPuertas;

    @FXML
    private TextField txtFldCombustible;

    @FXML
    private RadioButton radAutomat;

    @FXML
    private RadioButton radManual;

    @FXML
    private CheckBox chkDescapotable;
    
    @FXML
    private Label lblCombustible;
    @FXML
    private Label lblNumPuertas;
    @FXML
    private Label lblCombustible1;
    @FXML
    private TextField txtFldCombustible1;
    @FXML
    private Label lblNumPuertas1;
    @FXML
    private TextField txtFldNumPuertas1;
    @FXML
    private CheckBox chkSillaBebe;
    @FXML
    private Label lblNumPlazas;
    @FXML
    private TextField txtFldNumPlazas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tipoVehiculo = new ToggleGroup();
        this.radDeport.setToggleGroup(tipoVehiculo);
        this.radFami.setToggleGroup(tipoVehiculo);
        this.radFurgo.setToggleGroup(tipoVehiculo);
        cargarDatos();

    }

    private void cargarDatos() {

        for (int i = 0; i < vehiculos.size(); i++) {
            listViewMatriculas.getItems().add(vehiculos.get(i).getMatricula());
        }
    }

    @FXML
    private void mostrarDatosVehiculo() {

        String matricula = listViewMatriculas.getSelectionModel().getSelectedItem();
        String combustible = "";
        String cambio = "";
        Vehiculo v;

        if (matricula != null || !matricula.isEmpty()) {
            v = getVehiculo(matricula);
            textFieldMatricula.setText(v.getMatricula());
            textFieldMarca.setText(v.getMarca());
            textFieldModelo.setText(v.getModelo());
            textFieldCilindrada.setText(String.valueOf(v.getCilindrada()));

            if (v instanceof Deportivo) {
                radDeport.setDisable(false);
                radDeport.setSelected(true);

                Combustible c = ((Deportivo) v).getCombustible();

                combustible = c.toString();

                txtFldCombustible.setDisable(false);
                txtFldCombustible.setText(combustible);
                lblCombustible.setDisable(false);

                txtFldNumPuertas.setDisable(false);
                txtFldNumPuertas.setText(String.valueOf(((Deportivo) v).getNumeroPuertas()));
                lblNumPuertas.setDisable(false);

                chkDescapotable.setDisable(false);
                chkDescapotable.setSelected(true);

                CajaCambios cam = ((Deportivo) v).getCambio();

                if (cam == Enumerados.CajaCambios.AUTOMATICO) {
                    radAutomat.setDisable(false);
                    radAutomat.setSelected(true);
                } else {
                    radManual.setDisable(false);
                    radManual.setSelected(true);
                }

            }

            if (v instanceof Familiar) {
                radFami.setDisable(false);
                radFami.setSelected(true);

                Combustible c = ((Familiar) v).getCombustible();
                
                combustible = c.toString();

                txtFldCombustible1.setDisable(false);
                txtFldCombustible1.setText(combustible);
                lblCombustible1.setDisable(false);

                txtFldNumPuertas1.setDisable(false);
                txtFldNumPuertas1.setText(String.valueOf(((Familiar) v).getNumeroPuertas()));
                lblNumPuertas1.setDisable(false);

                chkSillaBebe.setDisable(false);
                chkSillaBebe.setSelected(true);

                txtFldNumPlazas.setDisable(false);
                txtFldNumPlazas.setText(String.valueOf(((Familiar) v).getNumPlazas()));
                lblNumPlazas.setDisable(false);

            }
        }

    }

    public void volver(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Vista/FXMLDocument.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
