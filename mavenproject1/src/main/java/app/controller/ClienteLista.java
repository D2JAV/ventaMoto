package app.controller;

import DB.DB_Clientes; 
import app.clases.Clientes; 
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

public class ClienteLista {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Clientes> tabla;

    @FXML
    void agregar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ClienteAgregar.fxml"));
        fxmlLoader.load();

        ClienteAgregar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.clienteGenerado!= null) {
            refrescarTabla();
        }
    }

    public void refrescarTabla() {
        tabla.getItems().clear();
        tabla.getItems().addAll(new DB_Clientes().listar());
    }

    @FXML
    void eliminar(ActionEvent event) {
        Clientes clienteSelect = tabla.getSelectionModel().getSelectedItem();
        new DB_Clientes().eliminar(clienteSelect.getIdCliente());
        refrescarTabla();
    }

    @FXML
    void initialize() {
        assert btnEliminar != null : "fx:id=\"btnEliminar\" was not injected: check your FXML file 'ClienteLista.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'ClienteLista.fxml'.";

    }

    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Nombre", "nombre", "0"},
        {"Apellido", "apellido", "0"},
        {"Email", "email", "0"},
        {"Email", "telefono", "0"},
        {"Direccion", "direccion", "0"}
    };

    public void ini() {
        new Funciones().agregarColumnasAncho(columnas, tabla);
        refrescarTabla();
        btnEliminar.setDisable(true);

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clientes>() {
            @Override
            public void changed(ObservableValue<? extends Clientes> observable, Clientes oldValue, Clientes newValue) {
                if (newValue != null) {
                    btnEliminar.setDisable(false);
                } else {
                    btnEliminar.setDisable(true);
                }
            }
        });
    }
}
