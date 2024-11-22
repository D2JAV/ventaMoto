/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.controller;

import DB.DB_Marca;
import app.clases.Marca;
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
public class MarcaList implements Initializable {

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
    private TableView<Marca> tabla;
    @FXML
    private Button btnSeleccionar;

    /**
     * Initializes the controller class.
     */
    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Marca", "nombre", "0"}
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    Marca marcaSelect = null;

    public void ini() {

        btnSeleccionar.setDisable(true);
        new Funciones().agregarColumnasAncho(columnas, tabla);
        refrescarTabla();
        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Marca>() {
            @Override
            public void changed(ObservableValue<? extends Marca> observable, Marca oldValue, Marca newValue) {
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

        String marca = new Ventanas().inputText("");

        if (marca != null) {
            new DB_Marca().insertarMarca(marca);
            refrescarTabla();
        }
    }

    public void refrescarTabla() {
        tabla.getItems().clear();
        ArrayList<Marca> list = new DB.DB_Marca().listarMarcas();
        tabla.getItems().addAll(list);
    }

    @FXML
    void seleccionar(ActionEvent event) {
        this.marcaSelect = tabla.getSelectionModel().getSelectedItem();
        this.stage.close();
    }

    public Marca getMarcaSelect() {
        return marcaSelect;
    }

    public void setMarcaSelect(Marca marcaSelect) {
        this.marcaSelect = marcaSelect;
    }

}
