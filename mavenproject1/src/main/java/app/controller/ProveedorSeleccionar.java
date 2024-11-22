package app.controller;

import DB.DB_Proveedores;
import app.clases.Proveedores;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modulos.Funciones;

public class ProveedorSeleccionar {

    Stage stage;
    double xOffest = 0;
    double yOffest = 0;

    @FXML
    void cerrar(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    void keryboardPressed(KeyEvent event) throws ParseException {
        String name = event.getCode().getName().toUpperCase();

        if (name.equals("ENTER")) {

            if (tabla.getSelectionModel().getSelectedItem() != null) {
                aceptar(null);
            }

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
    private TextField txtBuscador;
    @FXML
    private TableView<Proveedores> tabla;

    Proveedores proveedorSelect;

    public Proveedores getProveedorSelect() {
        return proveedorSelect;
    }

    public void setProveedorSelect(Proveedores proveedorSelect) {
        this.proveedorSelect = proveedorSelect;
    }

    @FXML
    void aceptar(ActionEvent event) {

        proveedorSelect = tabla.getSelectionModel().getSelectedItem();
        this.stage.close();
    }

    @FXML
    void buscar(ActionEvent event) {

        String nombre = txtBuscador.getText();
        tabla.getItems().clear();
        tabla.getItems().addAll(new DB_Proveedores().byName(nombre));
    }

    @FXML
    void initialize() {
        assert txtBuscador != null : "fx:id=\"buscador\" was not injected: check your FXML file 'ProveedorSeleccionar.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'ProveedorSeleccionar.fxml'.";

    }

    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Id", "formatIdProveedor", "50"},
        {"Nombre", "nombre", "0"}
    };

    @FXML
    private Button btnAceptar;

    public void ini() {

        new Funciones().agregarColumnasAncho(columnas, tabla);
        btnAceptar.setDisable(true);

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proveedores>() {
            @Override
            public void changed(ObservableValue<? extends Proveedores> observable, Proveedores oldValue, Proveedores newValue) {
                if (newValue != null) {
                    btnAceptar.setDisable(false);
                } else {
                    btnAceptar.setDisable(true);
                }
            }
        });

        refrescarTabla();

    }

    public void refrescarTabla() {

        tabla.getItems().clear();
        tabla.getItems().addAll(new DB_Proveedores().listar());
    }

}
