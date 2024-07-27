/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class DialogWindowController implements Initializable {

    private MainWindowController mconttroller;

    @FXML
    private Label TTitulo;
    @FXML
    private Label TDescripcion;
    @FXML
    private Button BCerrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) BCerrar.getScene().getWindow();
        stage.close();
        
    }

    public void iniciar(String titulo, String desc) {
        this.TTitulo.setText(titulo);
        this.TDescripcion.setText(desc);
    }

}
