package app.controller;

import DB.ApiCompraDetalle;
import DB.ApiCompras;
import DB.DB_Info;
import app.clases.Compras;
import app.clases.DetalleCompras;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableViewSkin;
import javafx.stage.Stage;
import modulos.Funciones;

public class CompraHojaPrincipal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblDireccionEmpresa;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblFechaConsulta;

    @FXML
    private Label lblIdCompra;

    @FXML
    private Label lblMontoTotal;

    @FXML
    private Label lblNombreEmpresa;

    @FXML
    private Label lblPagina;

    @FXML
    private Label lblProveedor;

    @FXML
    private Label lblRucEmpresa;

    @FXML
    private TableView<DetalleCompras> tabla;

    @FXML
    private Label lblFechaOrden;

    @FXML
    void initialize() {
        assert lblDireccionEmpresa != null : "fx:id=\"lblDireccionEmpresa\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblEstado != null : "fx:id=\"lblEstado\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblFecha != null : "fx:id=\"lblFecha\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblFechaConsulta != null : "fx:id=\"lblFechaConsulta\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblIdCompra != null : "fx:id=\"lblIdCompra\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblMontoTotal != null : "fx:id=\"lblMontoTotal\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblNombreEmpresa != null : "fx:id=\"lblNombreEmpresa\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblPagina != null : "fx:id=\"lblPagina\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblProveedor != null : "fx:id=\"lblProveedor\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert lblRucEmpresa != null : "fx:id=\"lblRucEmpresa\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'CompraHojaPrincipal.fxml'.";

    }
    /*
     private int idCompra;
    private int idProducto;
    private int cantidad;
    private double precioCompra;
    private double precioTotal;//esto es el precio total de cada detalle

    private String productoDescripcion;
    private String detalleProducto;

    private String formatIdCompra, formatIdProducto;
     */

    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Id", "formatIdProducto", "80"},
        {"Descripcion", "productoDescripcion", "0"},
        {"Cantidad", "cantidad", "120"},
        {"P. Compra", "formatPrecioCompra", "120"},
        {"P. Total", "formatPrecioTotal", "120"}
    };

    public void ini(int idCompra) {

        lblNombreEmpresa.setText(new DB_Info().getNombreEmpresa());
        lblDireccionEmpresa.setText(new DB_Info().getDireccionEmpresa());
        lblRucEmpresa.setText(new DB_Info().getRucEmpresa());

        Compras compra = new ApiCompras().byId(idCompra);

        lblIdCompra.setText(compra.getFormatIdCompra());
        lblEstado.setText(compra.getEstado());
        lblFechaOrden.setText(compra.getFormatFechaOrden());
        lblMontoTotal.setText("S/ " + compra.getFormatMonto());
        lblProveedor.setText(compra.getFormatProveedor());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM"); // Define el formato
        lblFecha.setText(formatter.format(compra.getFechaOrden()).toUpperCase()); // Formatea la fecha como String

        Date currentDate = new Date(); // Obtiene la hora y fecha actual
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss dd-MMM-yyyy"); // Formato deseado
        lblFechaConsulta.setText(formatter2.format(currentDate)); // Formatea la fecha actual
        lblPagina.setText("Pag. 1 de 1");
        new Funciones().agregarColumnasAncho(columnas, tabla);

        tabla.getItems().addAll(new ApiCompraDetalle().listarDetallesPorCompra(idCompra));

        // Ajustar la altura
        tabla.setFixedCellSize(25); // Altura de cada celda (ajusta según tus necesidades)
        tabla.prefHeightProperty().bind(
                tabla.fixedCellSizeProperty().multiply(tabla.getItems().size()).add(25+1) // +25 para la cabecera
        );
 
    }

    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
