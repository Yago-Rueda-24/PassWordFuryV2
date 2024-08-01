/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.excepciones.ExEntradaInvalida;
import Modelo.excepciones.EXEntradaRepetida;
import Modelo.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class MainWindowController implements Initializable {

    private ObservableList entradas;
    private Alert alert;
    private ArrayList entryElements;
    private Generador generator;
    private SafeBox sf;
    @FXML
    private Button Banadir;
    @FXML
    private Button BBorrar;
    @FXML
    private MenuBar menubar;
    @FXML
    private TableView<?> tabla;
    @FXML
    private TableColumn columnAPP;
    @FXML
    private TableColumn columnUser;
    @FXML
    private TableColumn columnPAssword;
    @FXML
    private TextField TApp;
    @FXML
    private TextField TUser;
    @FXML
    private TextField TPassword;
    @FXML
    private Label Lapp;
    @FXML
    private Label LUser;
    @FXML
    private Label Lpassword;
    @FXML
    private Pane PaneEntry;
    @FXML
    private Button BGuardar;
    @FXML
    private Button BCancelar;
    @FXML
    private Button BGenerar;
    @FXML
    private MenuItem a;
    @FXML
    private MenuItem sad;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Crea una nueva safebox(Esto es de prueba)
            this.sf = new SafeBox("a", "a");
        } catch (Exception ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Asigna el array de entradas al array de entradas de la safebox
        this.entradas = sf.getEntradas();
        //Se crean y guardan los campos que se usan para añadir una nueva entrada
        this.entryElements = new ArrayList();
        this.entryElements.add(Lapp);
        this.entryElements.add(LUser);
        this.entryElements.add(Lpassword);
        this.entryElements.add(TApp);
        this.entryElements.add(TUser);
        this.entryElements.add(TPassword);
        //Indican a la tabla que atributo del objeto entrada va en cada columna
        this.columnAPP.setCellValueFactory(new PropertyValueFactory("app"));
        this.columnUser.setCellValueFactory(new PropertyValueFactory("user"));
        this.columnPAssword.setCellValueFactory(new PropertyValueFactory("password"));
        //Estetica de la aplicación
        TApp.setPromptText("Introduce app");
        TUser.setPromptText("Introduce usuario");
        TPassword.setPromptText("Introduce contraseña");
        //Instancia el generador de contraseñas
        generator = Generador.getInstance();

        //Oculta todos los elementos de entrada de datos
        showEntry(false);
    }

    @FXML
    private void delete(ActionEvent event) {
        Entrada delete = (Entrada) tabla.getSelectionModel().getSelectedItem();
        if (delete != null) {
            this.entradas.remove(delete);
            this.tabla.refresh();
        } else {
            showAlert("Error", "El campo seleccionado esta vacio");
        }

    }

    @FXML
    private void add(ActionEvent event) {

        showEntry(true);

    }

    @FXML
    private void seleccionar(MouseEvent event) {
    }

    /**
     * Modifica el titulo y contenido de la alerta asociada al controlador y la
     * muestra
     *
     * @param title Titulo que llevara la alerta
     * @param Description Descripcion del mensaje/Error que llevara la alerta
     */
    private void showAlert(String title, String description) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/DialogWindow.fxml"));
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

    /**
     *
     * @param visible
     */
    private void showEntry(boolean visible) {

        PaneEntry.setVisible(visible);
        if (!visible) {
            this.TApp.setText("");
            this.TUser.setText("");
            this.TPassword.setText("");
        }

    }

    @FXML
    private void save(ActionEvent event) {
        try {
            if (TApp.getText().equals("") || TUser.getText().equals("") || TPassword.getText().equals("")) {
                throw new ExEntradaInvalida("La entrada que quieres añadir no tiene todos los campos completos");
            }
            for (Object e : entradas) {
                Entrada aux = (Entrada) e;
                if (aux.getApp().equals(this.TApp.getText())) {
                    throw new EXEntradaRepetida("La entrada que quieres añadir ya se encuentra en la boveda");
                }
            }
            Entrada aux = new Entrada(TApp.getText(), TUser.getText(), TPassword.getText());
            this.entradas.add(aux);
            this.tabla.setItems(entradas);

        } catch (ExEntradaInvalida | EXEntradaRepetida ex) {
            showAlert("Error", ex.getLocalizedMessage());

        }
        showEntry(false);
    }

    @FXML
    private void cancel(ActionEvent event) {
        showEntry(false);
    }

    @FXML
    private void generar(ActionEvent event) {
        this.TPassword.setText(generator.generate());
    }

    @FXML
    private void hloi(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/secondWindow.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la segunda ventana
            SecondWindowController controller = loader.getController();

            // Configurar la segunda ventana
            Stage stage = new Stage();
            stage.setTitle("Second Window");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void asdasd(ActionEvent event) {
        Stage stage = (Stage) this.BGuardar.getScene().getWindow();
        stage.close();
    }
}
