/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.excepciones.ExEntradaInvalida;
import Modelo.excepciones.EXEntradaRepetida;
import Modelo.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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

    private String ruta;
    private ObservableList entradasTabla;
    private ArrayList<Entrada> entradasBoveda;
    private ObjectOutputStream out;
    private Alert alert;
    /**
     * Se usa para el frontend de la aplicación no tiene ningun uso más
     */
    private ArrayList entryElements;
    private Generador generator;
    private SafeBox sf;
    private boolean booladd;
    private boolean boolmodify;
    /**
     * Se usa para señalar las opciones que tiene el combobox
     */
    private ObservableList opcombobox;

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
    private MenuItem MBCerrar;
    @FXML
    private TextField TBuscar;
    @FXML
    private ComboBox<String> ComboOpciones;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.entradasTabla = FXCollections.observableArrayList();
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
        //Inicialización del combobox
        opcombobox = FXCollections.observableArrayList("app", "usuario", "password");
        ComboOpciones.setItems(opcombobox);
        ComboOpciones.setValue("app");

        booladd = false;
        boolmodify = false;
        showEntry(false);

    }

    @FXML
    private void delete(ActionEvent event) {
        Entrada delete = (Entrada) tabla.getSelectionModel().getSelectedItem();
        if (delete != null) {
            this.sf.remove(delete);
            this.entradasTabla.remove(delete);
            storeData();
            this.tabla.setItems(entradasTabla);
            showEntry(false);
        } else {
            showAlert("Error", "El campo seleccionado esta vacio");
        }

    }

    @FXML
    private void add(ActionEvent event) {
        booladd = true;
        boolmodify = false;
        this.TApp.setText("");
        this.TUser.setText("");
        this.TPassword.setText("");
        showEntry(true);

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

            if (this.booladd) {
                Entrada aux = new Entrada(TApp.getText(), TUser.getText(), TPassword.getText());
                this.sf.add(aux);//El add de la safebox ya comprueba repetidos
                this.entradasTabla.add(aux);
                storeData();
                this.tabla.setItems(entradasTabla);
            }
            if (this.boolmodify) {
                Entrada neweentry = new Entrada(TApp.getText(), TUser.getText(), TPassword.getText());
                Entrada oldentry = (Entrada) tabla.getSelectionModel().getSelectedItem();
                if (oldentry != null) {
                    this.sf.modify(oldentry, neweentry);
                    this.entradasTabla.remove(oldentry);
                    this.entradasTabla.add(neweentry);
                    storeData();
                    this.tabla.setItems(entradasTabla);
                }
            }

        } catch (ExEntradaInvalida | EXEntradaRepetida ex) {
            showAlert("Error", ex.getLocalizedMessage());

        } finally {
            showEntry(false);
            booladd = false;
            boolmodify = false;
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
        showEntry(false);
    }

    @FXML
    private void generar(ActionEvent event) {
        this.TPassword.setText(generator.generate());
    }

    /**
     * Establece la safebox y la ruta de esta para que el controlador pueda
     * trabajar con los recursos
     *
     * @param sf La safebox que se carga
     * @param ruta La ruta en la que se almacena la safebox que se cargara
     */
    public void setSafeBox(SafeBox sf, String ruta) {
        this.sf = sf;
        this.entradasBoveda = sf.getEntradas();
        this.ruta = ruta;
        if (!this.entradasBoveda.isEmpty()) {
            for (Entrada e : entradasBoveda) {
                this.entradasTabla.add(e);
            }
            this.tabla.setItems(entradasTabla);
        }

    }

    public void storeData() {
        try {
            out = new ObjectOutputStream(new FileOutputStream(ruta));
            out.writeObject(sf);
            out.close();
            out = null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        this.sf = null;
        this.ruta = null;
        System.exit(0);
    }

    @FXML
    private void buscar() {
        if (TBuscar.getText().equals("")) {
            entradasTabla.removeAll(entradasBoveda);
            List<Entrada> entrycollection = entradasBoveda.stream().collect(Collectors.toList());
            entradasTabla.addAll(entrycollection);
            this.tabla.setItems(entradasTabla);
        } else {
            entradasTabla.removeAll(entradasBoveda);
            List<Entrada> entrycollection;
            switch (ComboOpciones.getValue()) {
                case "app":
                    entrycollection = entradasBoveda.stream().filter(entrada -> entrada.getApp().contains(TBuscar.getText())).collect(Collectors.toList());
                    break;
                case "usuario":
                    entrycollection = entradasBoveda.stream().filter(entrada -> entrada.getUser().contains(TBuscar.getText())).collect(Collectors.toList());
                    break;
                case "password":
                    entrycollection = entradasBoveda.stream().filter(entrada -> entrada.getPassword().contains(TBuscar.getText())).collect(Collectors.toList());
                    break;
                default:
                    throw new AssertionError();
            }
            entradasTabla.addAll(entrycollection);
            this.tabla.setItems(entradasTabla);
        }
    }

    @FXML
    private void modify(MouseEvent event) {
        Entrada delete = (Entrada) tabla.getSelectionModel().getSelectedItem();
        if (delete != null) {
            this.booladd = false;
            this.boolmodify = true;
            TApp.setText(delete.getApp());
            TUser.setText(delete.getUser());
            TPassword.setText(delete.getPassword());
            showEntry(true);
        }
    }

}
