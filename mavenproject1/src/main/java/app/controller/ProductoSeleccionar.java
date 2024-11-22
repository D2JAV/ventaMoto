package app.controller;

import DB.DB_Producto; 
import app.clases.Producto;
import app.clases.ProductosTablaUno;
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

public class ProductoSeleccionar {

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
            aceptar(null);
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
    private Button btnSeleccionar;

    @FXML
    private TableView<Producto> tabla;

    @FXML
    private TextField txtBuscador;

    Producto productoSelect;

    @FXML
    void aceptar(ActionEvent event) {
        productoSelect = tabla.getSelectionModel().getSelectedItem();
        this.stage.close();
    }

    public Producto getProductoSelect() {
        return productoSelect;
    }

    public void setProductoSelect(Producto productoSelect) {
        this.productoSelect = productoSelect;
    }

    @FXML
    void buscar(ActionEvent event) {
        String busqueda = txtBuscador.getText();

        if (!busqueda.isEmpty()) {
            tabla.getItems().clear();
            tabla.getItems().addAll(new DB_Producto().buscar(busqueda));
        }

    }

    @FXML
    void refrescar(ActionEvent event) {
        refrescarTabla();
    }

    @FXML
    void initialize() {
        assert btnSeleccionar != null : "fx:id=\"btnSeleccionar\" was not injected: check your FXML file 'ProductoSeleccionar.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'ProductoSeleccionar.fxml'.";
        assert txtBuscador != null : "fx:id=\"txtBuscador\" was not injected: check your FXML file 'ProductoSeleccionar.fxml'.";

    }
    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Id", "formatIdProducto", "70"},
        {"Descripcion", "descripcion", "100"},
        {"Categoria", "categoria", "90"},
        {"Precio", "formatPrecio", "90"},
        {"Stock", "stock", "90"},
        {"Info", "formatDescricpion", "0"},};

    public void ini() {
        btnSeleccionar.setDisable(true);
        new Funciones().agregarColumnasAncho(columnas, tabla);

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observable, Producto oldValue, Producto newValue) {
                if (newValue != null) {
                    btnSeleccionar.setDisable(false);
                } else {
                    btnSeleccionar.setDisable(true);
                }
            }
        });

        refrescarTabla();
    }

    public void refrescarTabla() {
        txtBuscador.clear();
        tabla.getItems().clear();
        tabla.getItems().addAll(new DB_Producto().listar2());
    }

}
