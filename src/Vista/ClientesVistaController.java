/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import static Controlador.Main.clientes;
import Modelo.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juans
 */
public class ClientesVistaController implements Initializable {

    @FXML
    private TableView<Cliente> tableView;

    @FXML
    private TableColumn<Cliente, String> dniColumna;

    @FXML
    private TableColumn<Cliente, String> nombreColumna;

    @FXML
    private TableColumn<Cliente, String> direccionColumna;

    @FXML
    private TableColumn<Cliente, String> localidadColumna;

    @FXML
    private TableColumn<Cliente, String> codigopostalColumna;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Configurar las columnas para la tabla
        dniColumna.setCellValueFactory(new PropertyValueFactory<Cliente, String>("dni"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        direccionColumna.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        localidadColumna.setCellValueFactory(new PropertyValueFactory<Cliente, String>("localidad"));
        codigopostalColumna.setCellValueFactory(new PropertyValueFactory<Cliente, String>("codigoPostal"));

        //Cargar datos en la tabla
        tableView.setItems(getClientes());

    }

    public ObservableList<Cliente> getClientes() {

        ObservableList<Cliente> clientesOL = FXCollections.observableArrayList();

        for (int i = 0; i < clientes.size(); i++) {
            clientesOL.add(clientes.get(i));
        }

        return clientesOL;
    }
    
    public void volverAtras(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene (parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
