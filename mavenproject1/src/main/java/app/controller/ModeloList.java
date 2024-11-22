package app.controller;

import DB.DB_Modelo; 
import app.clases.Modelo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import modulos.Funciones;
import modulos.Ventanas;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ModeloList implements Initializable {

    Stage stage;
    double xOffest = 0;
    double yOffest = 0;

    @FXML
    void cerrar(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    void keryboardPressed(KeyEvent event) {
        String name = event.getCode().getName().toUpperCase();

        if (name.equals("ENTER")) {
            this.stage.close();

        } else if (name.equals("ESC")) {
            if (this.stage != null) {
                this.stage.close();
            }

        }
    }

    @FXML
    void mover(MouseEvent event) {
        this.stage.setX(event.getScreenX() - xOffest);
        this.stage.setY(event.getScreenY() - yOffest);
    }

    @FXML
    void presionar(MouseEvent event) {
        xOffest = event.getSceneX();
        yOffest = event.getSceneY();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Modelo> tabla;
    @FXML
    private Button btnSeleccionar;

    /**
     * Initializes the controller class.
     */
    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Modelo", "nombre", "0"}
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    Modelo modeloSelect = null;

    public void ini() {

        btnSeleccionar.setDisable(true);
        new Funciones().agregarColumnasAncho(columnas, tabla);
        refrescarTabla();
        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Modelo>() {
            @Override
            public void changed(ObservableValue<? extends Modelo> observable, Modelo oldValue, Modelo newValue) {
                if (newValue != null) {
                    btnSeleccionar.setDisable(false);
                } else {
                    btnSeleccionar.setDisable(true);
                }
            }
        }); 

    }

    @FXML
    void agregar(ActionEvent event) throws IOException {

        String modelo = new Ventanas().inputText("");

        if (modelo != null) {
            new DB_Modelo().insertarModelo(modelo);
            refrescarTabla();
        }
    }

    public void refrescarTabla() {
        tabla.getItems().clear();
        ArrayList<Modelo> list = new DB_Modelo().listar();
        tabla.getItems().addAll(list);
    }

    @FXML
    void seleccionar(ActionEvent event) {
        this.modeloSelect = tabla.getSelectionModel().getSelectedItem();
        this.stage.close();
    }

    public Modelo getModeloSelect() {
        return modeloSelect;
    }

    public void setMarcaSelect(Modelo modeloSelect) {
        this.modeloSelect = modeloSelect;
    }

}
