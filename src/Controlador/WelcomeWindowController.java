/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class WelcomeWindowController implements Initializable {

    private SimpleDateFormat formatter;
    
    private FXMLLoader loader;
    @FXML
    private Button BAbrir;
    @FXML
    private Button BCrear;
    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private Label LHora;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        formatter = new SimpleDateFormat("HH:mm:ss");
        LHora.setText(formatter.format(new Date(System.currentTimeMillis())));
        cambiarHora();
    }

    @FXML
    private void abrir(ActionEvent event) {
        cargarYAbrir("/Vista/openWindow.fxml", "abrir");

    }

    @FXML
    private void crear(ActionEvent event) {
        cargarYAbrir("/Vista/createWindow.fxml", "crear");

    }

    private void cargarYAbrir(String rutacarga, String titulo) {
        try {
            loader = new FXMLLoader(WelcomeWindowController.class.getResource(rutacarga));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

            Stage stageclose = (Stage) panelPrincipal.getScene().getWindow();
            stageclose.hide();

        } catch (IOException ex) {
            Logger.getLogger(WelcomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cambiarHora() {

        new Thread(() -> {

            try {
                while (true) {
                    Thread.sleep(1000);
                    Platform.runLater(() -> LHora.setText(formatter.format(new Date(System.currentTimeMillis()))));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> LHora.setText("Texto actualizado desde un hilo externo"));
        }).start();
    }

}
