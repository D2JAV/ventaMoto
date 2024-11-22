package app.controller;

import app.clases.Clientes;
import app.clases.DetalleVenta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modulos.Ventanas;

public class VentaAgregar {

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
    private Button btnProductoEliminar;

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
    private Label lblVendedorLog;

    @FXML
    private TableView<DetalleVenta> tabla;

    @FXML
    private TextField txtCliente;

    @FXML
    void agregarCompra(ActionEvent event) {

    }

    @FXML
    void productoAgregar(ActionEvent event) {

    }

    @FXML
    void productoEliminar(ActionEvent event) {

    }

    Clientes clienteSelect;
    @FXML
    void seleccionarCliente(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ClienteSeleccionar.fxml"));
        fxmlLoader.load();

        ClienteSeleccionar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.getClienteSelect()!= null) {
            clienteSelect = formulario.getClienteSelect();
            txtCliente.setText(clienteSelect.getFormatIdCliente()+ ": " + clienteSelect.getNombre().toUpperCase());
        }
    }

    @FXML
    void initialize() {
        assert btnProductoEliminar != null : "fx:id=\"btnProductoEliminar\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
        assert dateFechaOrden != null : "fx:id=\"dateFechaOrden\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
        assert lblErrorFecha != null : "fx:id=\"lblErrorFecha\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
        assert lblErrorProductos != null : "fx:id=\"lblErrorProductos\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
        assert lblErrorProveedor != null : "fx:id=\"lblErrorProveedor\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
        assert lblTotal != null : "fx:id=\"lblTotal\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
        assert lblVendedorLog != null : "fx:id=\"lblVendedorLog\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'VentaAgregar.fxml'.";
     
    }

    public void ini() {

    }
}
