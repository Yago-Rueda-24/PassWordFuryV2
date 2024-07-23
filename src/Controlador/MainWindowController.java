/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.excepciones.ExEntradaInvalida;
import Modelo.excepciones.EXEntradaRepetida;
import Modelo.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Guarda las entradas que se mostraran en la tabla de la aplicación
        this.entradas = FXCollections.observableArrayList();
        //Se crea la alerta que se usara en la ejecución del programa,siempre sera lo misma lo que se cambiara es su contenido
        this.alert = new Alert(Alert.AlertType.ERROR);
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
    private void showAlert(String title, String Description) {
        this.alert.setTitle(title);
        this.alert.setContentText(Description);
        this.alert.showAndWait();
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
}
