/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.Main.vehiculos;
import Modelo.Vehiculo;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class VehiculosVistaController implements Initializable {
    
    @FXML
    private ListView listViewMatriculas;

    @FXML
    private TextField textFieldMatricula;

    @FXML
    private TextField textFieldMarca;

    @FXML
    private TextField textFieldModelo;

    @FXML
    private TextField textFieldCilindrada;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        obtenerMatriculas();
       
        
    }
    
    private void obtenerMatriculas(){
        
        ArrayList<String> matriculas = new ArrayList<>();
        
        if(!vehiculos.isEmpty()){
            
            for(Vehiculo v : vehiculos){
                listViewMatriculas.getItems().add(v.getMatricula());
            }
              
        }    
    }
    
    @FXML private void matriculaSeleccionada(MouseEvent arg0){
        
        String matricula = "";
        
        //Object obj = listViewMatriculas.getItems();
        
        //matricula = String.format("%s%n", (String) obj);
        
        //this.textFieldMatricula.setText(matricula);
        
        System.out.println("Has hecho click");
        
    }
    
}
