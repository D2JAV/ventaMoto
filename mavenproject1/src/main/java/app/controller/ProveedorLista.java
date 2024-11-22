package app.controller;

import DB.ApiProveedorTipoProducto;
import DB.DB_Proveedores;
import app.clases.ProveedorTipoProducto;
import app.clases.Proveedores;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modulos.Funciones;
import modulos.Ventanas;

public class ProveedorLista {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Proveedores> tabla;
    @FXML
    private TableView<ProveedorTipoProducto> tabla2;

    String[][] columnas = {
        //cabecera-propiedad-ancho 

        {"Id", "formatIdProveedor", "0"},
        {"Nombre", "nombre", "0"},
        {"Contacto", "contacto", "0"},
        {"Direccion", "direccion", "0"},};

    String[][] columnas2 = {
        //cabecera-propiedad-ancho 

        {"Tipo Producto", "descripcion", "0"},};

    @FXML
    void agregar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProveedorAgregar.fxml"));
        fxmlLoader.load();

        ProveedorAgregar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.getProveedorGenerado() != null) {
            refrescarTabla();
        }
    }

    public void refrescarTabla() {
        tabla.getItems().clear();
        tabla.getItems().addAll(new DB_Proveedores().listar());
    }

    @FXML
    void eliminar(ActionEvent event) {
        Proveedores proveedorSelect = tabla.getSelectionModel().getSelectedItem();
        new DB_Proveedores().eliminar(proveedorSelect.getIdProveedor());
        refrescarTabla();
    }

    @FXML
    void initialize() {
        assert btnEliminar != null : "fx:id=\"btnEliminar\" was not injected: check your FXML file 'ProveedorLista.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'ProveedorLista.fxml'.";

    }

    public void ini() {
        new Funciones().agregarColumnasAncho(columnas, tabla);
        new Funciones().agregarColumnasAncho(columnas2, tabla2);
        refrescarTabla();
        btnEliminar.setDisable(true);

        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> {
            btnEliminar.setDisable(newValue == null);
            if (newValue != null) {
                refrescarTabla2(newValue.getIdProveedor());
            }
        }
        );
    }

    public void refrescarTabla2(int idProveedor) {
        tabla2.getItems().clear();
        tabla2.getItems().addAll(new ApiProveedorTipoProducto().listar(idProveedor));
        tabla2.setFixedCellSize(25); // Altura de cada celda (ajusta según tus necesidades)
        tabla2.prefHeightProperty().bind(
                tabla2.fixedCellSizeProperty().multiply(tabla2.getItems().size()).add(25 + 1) // +25 para la cabecera
        );
    }

}
