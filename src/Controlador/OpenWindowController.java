/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.SafeBox;
import Modelo.excepciones.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class OpenWindowController implements Initializable {

    private String ruta;
    private SafeBox sf;
    private StringProperty strproperty;
    @FXML
    private TextField Tubi;
    @FXML
    private Button BUbi;
    @FXML
    private Button BAbrir;
    @FXML
    private TextField Tpassword;
    @FXML
    private Button BCancelar;
    @FXML
    private Button BMostrar;
    @FXML
    private PasswordField TOcultPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tubi.setPromptText("Elija la ubicación de la boveda usando el boton");
        Tpassword.setPromptText("Inserte la contraseña de la safebox");
        Tpassword.setVisible(false);
        strproperty = new SimpleStringProperty();
        Tpassword.textProperty().bindBidirectional(strproperty);
        TOcultPassword.textProperty().bindBidirectional(strproperty);
    }

    @FXML
    private void selectUbi(ActionEvent event) {
        FileChooser filec = new FileChooser();
        ObjectInputStream in = null;
        try {
            File filein = filec.showOpenDialog(null);
            if (filein != null) {
                String rutaux = filein.getAbsolutePath();
                if (!rutaux.substring(rutaux.length() - 4).equals(".bov")) {
                    Tubi.setText("");
                    throw new IOException("El archivo seleccionado no tiene la extensión .bov");

                }
                in = new ObjectInputStream(new FileInputStream(rutaux));
                this.sf = (SafeBox) in.readObject();
                this.ruta = rutaux;
                Tubi.setText(this.ruta);
                in.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            showAlert("Error de formato", ex.getMessage());
        }

    }

    @FXML
    private void OpenSafe(ActionEvent event) {

        try {
            if (Tubi.getText().equals("") || Tpassword.getText().equals("")) {
                Tubi.setText("");
                Tpassword.setText("");
                throw new EXCampoIncompleto("Alguno de los campos esta incompleto , no se puede proceder con la operación");
            }
            if (!sf.unlock(Tpassword.getText())) {
                Tpassword.setText("");
                throw new ExAperturaSafeBox("La contraseña que ha introducido es incorrecta , no se puede abrir la boveda ");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/mainWindow.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la segunda ventana
            MainWindowController mainwindowc = loader.getController();
            mainwindowc.setSafeBox(this.sf, Tubi.getText());

            // Configurar la segunda ventana
            Stage stage = new Stage();
            stage.setTitle("Boveda");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

            Stage stageclose = (Stage) Tpassword.getScene().getWindow();
            stageclose.hide();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EXCampoIncompleto ex) {
            showAlert("Campos Incompletos", ex.getMessage());
        } catch (ExAperturaSafeBox ex) {
            showAlert("Contraseña Erronea", ex.getMessage());
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

            Stage stageclose = (Stage) this.BAbrir.getScene().getWindow();
            stageclose.close();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void mostrarPassWord(ActionEvent event) {
        if (this.BMostrar.getText().equals("Ocultar")) {
            this.BMostrar.setText("Mostrar");
            this.Tpassword.setVisible(false);
            this.TOcultPassword.setVisible(true);

        } else {
            this.BMostrar.setText("Ocultar");
            this.Tpassword.setVisible(true);
            this.TOcultPassword.setVisible(false);
        }

    }

}
