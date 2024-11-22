package app.controller;

import DB.ApiCompras;
import app.clases.Compras;
import app.clases.Proveedores;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modulos.Funciones;
import modulos.Ventanas;

public class CompraLista {

    Stage stage;
    double xOffest = 0;
    double yOffest = 0;

    @FXML
    void cerrar(ActionEvent event) {
        this.stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Compras> tabla;
    @FXML
    private Button btnDetalles;

    @FXML
    void detalles(ActionEvent event) throws IOException {

        Compras compraSelect = tabla.getSelectionModel().getSelectedItem();
        abrirDetalleCompra(compraSelect);

    }

    @FXML
    void nuevo(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CompraAgregar.fxml"));
        fxmlLoader.load();

        CompraAgregar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.getCompraGenerado() != null) {
            refrescarTabla();

            //abrir la pestaña de la compra
            abrirDetalleCompra(formulario.getCompraGenerado());
            //generar el pdf
        }
    }

    public void abrirDetalleCompra(Compras compra) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CompraView.fxml"));

        Scene escena = new Scene(fxmlLoader.load());

        CompraView formulario = fxmlLoader.getController();
        formulario.ini(compra.getIdCompra());
        principal.addTab("Compra: " + compra.getFormatIdCompra(), escena);

    }

    public void refrescarTabla() {

        tabla.getItems().clear();
        tabla.getItems().addAll(new ApiCompras().listar());

    }
    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Id", "formatIdCompra", "0"},
        {"P. Total", "formatMonto", "0"},
        {"Proveedor", "formatProveedor", "0"},
        {"F. Orden", "formatFechaOrden", "0"},
        {"Estado", "estado", "0"}

    };

    @FXML
    void initialize() {
    }

    Principal principal;

    public void ini(Principal principal) {
        this.principal = principal;
        new Funciones().agregarColumnasAncho(columnas, tabla);

        btnDetalles.setDisable(true);
        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDetalles.setDisable(newValue == null);
        });

        refrescarTabla();
    }

}
