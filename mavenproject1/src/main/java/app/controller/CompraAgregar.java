package app.controller;

import DB.ApiCompraDetalle;
import DB.ApiCompras;
import DB.ApiSerieComprada;
import DB.DB_Producto;
import app.clases.Compras;
import app.clases.DetalleCompras;
import app.clases.Producto;
import app.clases.Proveedores;
import app.clases.SerieComprada;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button; 
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modulos.Funciones;
import modulos.Ventanas;

public class CompraAgregar {

    Stage stage;
    double xOffest = 0;
    double yOffest = 0;

    @FXML
    void cerrar(ActionEvent event) {
        this.stage.close();
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
    private DatePicker dateFechaOrden;

    @FXML
    private Label lblErrorFecha;

    @FXML
    private Label lblErrorProductos;

    @FXML
    private Label lblErrorProveedor;

    @FXML
    private Label lblTotal;
    @FXML
    private TableView<DetalleCompras> tabla;

    @FXML
    private TextField txtProveedor;
    @FXML
    private Button btnProductoEliminar;
    Compras compraGenerado;

    @FXML
    void agregarCompra(ActionEvent event) throws IOException {

        lblErrorFecha.setText("");
        lblErrorProductos.setText("");
        lblErrorProveedor.setText("");

        boolean seguir = true;

        if (proveedorSelect == null) {
            seguir = false;
            lblErrorProveedor.setText("Seleccionar un proveedor");
        }

        LocalDate selectedDate = dateFechaOrden.getValue(); // Fecha seleccionada
        LocalDate currentDate = LocalDate.now(); // Fecha actual

        if (dateFechaOrden == null) {
            lblErrorFecha.setText("No se ha seleccionado ninguna fecha.");
            seguir = false;
        } else if (selectedDate.isAfter(currentDate)) {
            lblErrorFecha.setText("Fecha ilegal");
            seguir = false;
        }

        if (tabla.getItems().isEmpty()) {
            lblErrorProductos.setText("No existen productos\nseleccionados");
            seguir = false;
        }

        for (DetalleCompras cDetalle : tabla.getItems()) {
            if (cDetalle.getPrecioTotal() == 0) {

                lblErrorProductos.setText("Existen elementos\ncon precio 0.00");
                seguir = false;
                break;
            }
        }

        if (seguir) {

            Date fechaOrden = Date.valueOf(dateFechaOrden.getValue());
            compraGenerado = new Compras(-1, proveedorSelect.getIdProveedor(), fechaOrden, "PENDIENTE", montoCompra);
            int idCompraGenerado = new ApiCompras().insertarCompra(compraGenerado);
            compraGenerado.setIdCompra(idCompraGenerado);
            for (DetalleCompras cDetalle : tabla.getItems()) {
                cDetalle.setIdCompra(idCompraGenerado);
                new ApiCompraDetalle().insertar(cDetalle);
                new DB_Producto().modificarStock(cDetalle.getIdProducto(), cDetalle.getCantidad());

                if (cDetalle.getDetalleProducto().toLowerCase().startsWith("ma:")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SerieInput.fxml"));
                    fxmlLoader.load();

                    SerieInput formulario = fxmlLoader.getController();
                    Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
                    formulario.setStage(nVentana);
                    formulario.ini(cDetalle);
                    //nVentana.initStyle(StageStyle.UNDECORATED);
                    nVentana.showAndWait();

                    SerieComprada serieComprada = new SerieComprada(-1, idCompraGenerado, "COMPRADO", formulario.getSerie());
                    new ApiSerieComprada().insertar(serieComprada);
                }

            }

            this.stage.close();
        }

    }

    double montoCompra = 0;

    public Compras getCompraGenerado() {
        return compraGenerado;
    }

    public void setCompraGenerado(Compras compraGenerado) {
        this.compraGenerado = compraGenerado;
    }

    public Label getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(Label lblTotal) {
        this.lblTotal = lblTotal;
    }

    public double getMontoCompra() {
        return montoCompra;
    }

    public void setMontoCompra(double montoCompra) {
        this.montoCompra = montoCompra;
    }

    @FXML
    void productoAgregar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProductoSeleccionar.fxml"));
        fxmlLoader.load();

        ProductoSeleccionar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.getProductoSelect() != null) {
            Producto productoSelect = formulario.getProductoSelect();
            DetalleCompras detalleCompras = new DetalleCompras(
                    -1, productoSelect.getIdProducto(), 1, productoSelect.getPrecio());

            detalleCompras.setProductoDescripcion(productoSelect.getDescripcion());
            detalleCompras.setDetalleProducto(productoSelect.getFormatDescricpion());
            detalleCompras.setTablaCrearCompra(this);

            tabla.getItems().add(detalleCompras);

        }
    }

    public void actualizarMontoTotal() {
        montoCompra = 0;

        for (DetalleCompras cDetalle : tabla.getItems()) {
            try {
                montoCompra += cDetalle.getPrecioTotal();
            } catch (Exception e) {
            }
        }

        lblTotal.setText(String.format("%.2f", montoCompra));

    }

    @FXML
    void productoEliminar(ActionEvent event) {
        tabla.getItems().remove(tabla.getSelectionModel().getSelectedItem());
        tabla.getSelectionModel().clearSelection();
    }

    Proveedores proveedorSelect;

    @FXML
    void seleccionarProveedor(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProveedorSeleccionar.fxml"));
        fxmlLoader.load();

        ProveedorSeleccionar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.getProveedorSelect() != null) {
            proveedorSelect = formulario.getProveedorSelect();
            txtProveedor.setText(proveedorSelect.getFormatIdProveedor() + ": " + proveedorSelect.getNombre().toUpperCase());
        }
    }

    @FXML
    void initialize() {
        assert dateFechaOrden != null : "fx:id=\"dateFechaOrden\" was not injected: check your FXML file 'CompraAgregar.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'CompraAgregar.fxml'.";
        assert txtProveedor != null : "fx:id=\"txtProveedor\" was not injected: check your FXML file 'CompraAgregar.fxml'.";

    }
    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Id", "formatIdProducto", "50"},
        {"Descripcion", "productoDescripcion", "0"},
        {"Cantidad", "hboxCantidad", "90"},
        {"P. Compra", "textFieldPrecioCompra", "90"},
        {"P. Total", "labelPrecioTotal", "90"},};

    public void ini() throws IOException {
        txtProveedor.setEditable(false);
        dateFechaOrden.setEditable(false);
        lblTotal.setText("0.00");
        new Funciones().reiniciarDatePicker(dateFechaOrden);

        new Funciones().agregarColumnasAncho(columnas, tabla);

        btnProductoEliminar.setDisable(true);

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DetalleCompras>() {
            @Override
            public void changed(ObservableValue<? extends DetalleCompras> observable, DetalleCompras oldValue, DetalleCompras newValue) {
                if (newValue != null) {
                    btnProductoEliminar.setDisable(false);
                } else {
                    btnProductoEliminar.setDisable(true);
                }
            }
        });

        lblErrorFecha.setText("");
        lblErrorProductos.setText("");
        lblErrorProveedor.setText("");
        lblErrorProveedor.setStyle("-fx-text-fill: red;");
        lblErrorProductos.setStyle("-fx-text-fill: red;");
        lblErrorFecha.setStyle("-fx-text-fill: red;");

    }

}
