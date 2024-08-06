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
import javafx.scene.control.TextField;
import Modelo.SafeBox;
import Modelo.excepciones.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class CreateWindowController implements Initializable {

    private SafeBox sf;
    private String ruta;

    @FXML
    private TextField TPassword;
    @FXML
    private TextField TRepetir;
    @FXML
    private TextField Tubicacion;
    @FXML
    private Button BUbicacion;
    @FXML
    private Button BCrear;
    @FXML
    private Button Bcancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TPassword.setPromptText("Contraseña de la boveda");
        TRepetir.setPromptText("Repite la contraseña");
        Tubicacion.setPromptText("Selecione la ubicación con el boton");
        this.sf = null;
        this.ruta = null;
    }

    @FXML
    private void selectUbi(ActionEvent event) throws IOException {

        FileChooser filechooser = new FileChooser();
        File file = filechooser.showSaveDialog(null);
        if (file != null) {

            String ruta = file.getAbsolutePath();
            if (!ruta.substring(ruta.length() - 4).equals(".bov")) {
                ruta = ruta + ".bov";
            }
            this.ruta = ruta;
            Tubicacion.setText(this.ruta);
        }
    }

    @FXML
    private void createSafeBox(ActionEvent event) {
        /*
        Hay un error porque el observable list no se puede serializar , hay que cambiar la safebox
        
         */
        try {
            if (TPassword.getText().equals("")) {
                throw new EXErrorCreacion("El campo de contraseña esta vacio");
            }
            if (TRepetir.getText().equals("")) {
                throw new EXErrorCreacion("El campo de repetir contraseña esta vacio");
            }
            if (Tubicacion.getText().equals("")) {
                throw new EXErrorCreacion("No ha seleccionado una ubicación valida");
            }
            sf = new SafeBox(TPassword.getText(), TRepetir.getText());
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.ruta));
                out.writeObject(sf);
                out.close();
                abrirBoveda();

            } catch (IOException ex) {
                Logger.getLogger(CreateWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (EXErrorCreacion ex) {
            showAlert("Error", ex.getMessage());
        }

    }

    private void showAlert(String title, String description) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/dialogWindow.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la segunda ventana
            DialogWindowController controller = loader.getController();
            controller.iniciar(title, description);

            // Configurar la segunda ventana
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void abrirBoveda() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/mainWindow.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la segunda ventana
            MainWindowController mainwindowc = loader.getController();
            mainwindowc.setSafeBox(this.sf, Tubicacion.getText());

            // Configurar la segunda ventana
            Stage stage = new Stage();
            stage.setTitle("Boveda");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

            Stage stageclose = (Stage) TPassword.getScene().getWindow();
            stageclose.hide();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    @FXML
    private void closeWindow(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/welcomeWindow.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la segunda ventana
            WelcomeWindowController cont = loader.getController();

            // Configurar la segunda ventana
            Stage stage = new Stage();
            stage.setTitle("Bienvenida");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

            Stage stageclose = (Stage) this.BCrear.getScene().getWindow();
            stageclose.close();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
