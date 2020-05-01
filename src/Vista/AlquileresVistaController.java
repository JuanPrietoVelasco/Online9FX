/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import static Controlador.Main.alquileres;
import Modelo.Alquiler;
import Modelo.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author juans
 */
public class AlquileresVistaController implements Initializable {

    @FXML
    private TableView<Alquiler> tablaAlquileres;
    @FXML
    private TableColumn<Alquiler, String> ColDNI;
    @FXML
    private TableColumn<Alquiler, String> ColMat;
    @FXML
    private TableColumn<Alquiler, Boolean> ColAct;
    @FXML
    private TableColumn<Alquiler, Integer> ColDias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (alquileres != null) {
            ColDNI.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("saludo"));
            ColMat.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("despedida"));

            tablaAlquileres.setItems(getAlquileres());
        }
        
        for(Alquiler a : alquileres){
            System.out.println(a);
        }

    }

    public ObservableList<Alquiler> getAlquileres() {

        ObservableList<Alquiler> alquileresOL = FXCollections.observableArrayList();

        for (int i = 0; i < alquileres.size(); i++) {
            alquileresOL.add(alquileres.get(i));
        }

        return alquileresOL;
    }

}
