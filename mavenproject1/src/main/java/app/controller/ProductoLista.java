/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.controller;

import DB.DB_Accesorio;
import DB.DB_Marca;
import DB.DB_Modelo;
import DB.DB_Moto;
import DB.DB_Producto;
import app.clases.Accesorios;
import app.clases.Detalle;
import app.clases.Marca;
import app.clases.Modelo;
import app.clases.Motos;
import app.clases.Producto;
import java.io.IOException;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ProductoLista implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Producto> tabla;
    @FXML
    private TableView<Detalle> tabla2;

    @FXML
    private Button btnEliminar;

    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Id", "formatIdProducto", "80"},
        {"Descripcion", "descripcion", "0"},
        {"Precio", "precio", "100"},
        {"Stock", "stock", "100"},
        {"Categoria", "categoria", "100"}

    };

    String propiedad;
    String valor;

    String[][] columnaDetalle = {
        //cabecera-propiedad-ancho 
        {"Descripcion", "propiedad", "0"},
        {"Valor", "valor", "0"}

    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'ProductoLista.fxml'.";
    }

    public void ini() {
        new Funciones().agregarColumnasAncho(columnas, tabla);
        new Funciones().agregarColumnasAncho(columnaDetalle, tabla2);

        refrescarTabla();
        btnEliminar.setDisable(true);

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observable, Producto oldValue, Producto newValue) {
                tabla2.getSelectionModel().clearSelection();
                tabla2.getItems().clear();

                if (newValue != null) {
                    btnEliminar.setDisable(false);

                    if (newValue.getCategoria().toLowerCase().equals("moto")) {
                        String[] propiedad = {"Modelo", "Marca", "Año", "Color"};

                        Motos moto = new DB_Moto().getMotoById(newValue.getIdProducto());
                        Marca marca = new DB_Marca().getMarcaById(moto.getIdMarca());
                        Modelo modelo = new DB_Modelo().getModeloById(moto.getIdModelo());
                        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                        String anio = yearFormat.format(moto.getAnio());

                        String[] valores = {modelo.getNombre(), marca.getNombre(), anio, moto.getColor()};
                        for (int i = 0; i < propiedad.length; i++) {
                            Detalle detalle = new Detalle(propiedad[i], valores[i]);
                            tabla2.getItems().add(detalle);
                        }

                    } else {
                        String[] propiedad = {"Tipo"};
                        Accesorios accesorios = new DB_Accesorio().getAccesorioById(newValue.getIdProducto());

                        String[] valores = {accesorios.getTipo()};

                        for (int i = 0; i < propiedad.length; i++) {
                            Detalle detalle = new Detalle(propiedad[i], valores[i]);
                            tabla2.getItems().add(detalle);
                        }

                    }

                } else {
                    btnEliminar.setDisable(true);
                }
            }
        }
        );

    }

    public void refrescarTabla() {
        tabla.getItems().clear();
        tabla.getItems().addAll(new DB_Producto().listar());
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProductoAgregar.fxml"));
        fxmlLoader.load();

        ProductoAgregar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.getProductoGenerado() != null) {
            refrescarTabla();
        }
    }

    @FXML
    void eliminar(ActionEvent event) {
        Producto productoSelect = tabla.getSelectionModel().getSelectedItem();
        new DB_Producto().eliminar(productoSelect.getIdProducto());
        refrescarTabla();
    }

}
