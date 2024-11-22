package app.controller;

import DB.ApiProveedorTipoProducto;
import DB.DB_Proveedores;
import app.clases.Clientes;
import app.clases.ProveedorTipoProducto;
import app.clases.Proveedores;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modulos.Funciones;
import modulos.Ventanas;

public class ProveedorAgregar {

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
    private Button bttnEliminarTipo;

    @FXML
    private Label lblErrorContacto;

    @FXML
    private Label lblErrorDireccion;

    @FXML
    private Label lblErrorNombre;

    @FXML
    private TableView<ProveedorTipoProducto> tabla;

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
    private TextField txtContacto;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    Proveedores proveedorGenerado;

    @FXML
    void aceptar(ActionEvent event) {
        refrescarErrores();

        boolean continuar = true;

        if (txtNombre.getText().isEmpty()) {
            lblErrorNombre.setText("*error");
            continuar = false;
        }

        if (txtContacto.getText().isEmpty()) {
            lblErrorContacto.setText("*error");
            continuar = false;
        }

        if (txtDireccion.getText().isEmpty()) {
            lblErrorDireccion.setText("*error");
            continuar = false;
        }

        if (!continuar) {
            return;
        }

        String nombre = txtNombre.getText();
        String contacto = txtContacto.getText();
        String direccion = txtDireccion.getText();

        proveedorGenerado = new Proveedores(-1, nombre, contacto, direccion);
        int idProveedor = new DB_Proveedores().insertar(proveedorGenerado);

        proveedorGenerado.setIdProveedor(idProveedor);

        for (ProveedorTipoProducto tipoProducto : tabla.getItems()) {
            tipoProducto.setIdProveedor(idProveedor);
            new ApiProveedorTipoProducto().insertar(tipoProducto);
        }

        this.stage.close();
    }

    @FXML
    void initialize() {
        assert txtContacto != null : "fx:id=\"txtContacto\" was not injected: check your FXML file 'ProveedorAgregar.fxml'.";
        assert txtDireccion != null : "fx:id=\"txtDireccion\" was not injected: check your FXML file 'ProveedorAgregar.fxml'.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'ProveedorAgregar.fxml'.";

    }
    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Descripcion", "descripcion", "0"}

    };

    public void refrescarErrores() {
        Label[] list = {lblErrorNombre, lblErrorDireccion, lblErrorContacto};

        new Funciones().reiniciarErroes(list);
    }

    public void ini() {
        refrescarErrores();
        new Funciones().agregarColumnasAncho(columnas, tabla);
        bttnEliminarTipo.setDisable(true);
        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> bttnEliminarTipo.setDisable(newValue == null)
        );
        
        tabla.setFixedCellSize(25); // Altura de cada celda (ajusta según tus necesidades)
        tabla.prefHeightProperty().bind(
                tabla.fixedCellSizeProperty().multiply(tabla.getItems().size() + 1).add(25 + 1) // +25 para la cabecera1
        );
    }

    public Proveedores getProveedorGenerado() {
        return proveedorGenerado;
    }

    public void setProveedorGenerado(Proveedores proveedorGenerado) {
        this.proveedorGenerado = proveedorGenerado;
    }

    @FXML
    void agregarTipoProducto(ActionEvent event) throws IOException {

        String tipo = new Ventanas().inputText("");

        if (!tipo.isEmpty()) {
            ProveedorTipoProducto proveedorTipoProducto = new ProveedorTipoProducto(-1, tipo);
            tabla.getItems().add(proveedorTipoProducto);
        }
        tabla.prefHeightProperty().bind(
                tabla.fixedCellSizeProperty().multiply(tabla.getItems().size()).add(25 + 1) // +25 para la cabecera
        );
    }

    @FXML
    void eliminarTipoProducto(ActionEvent event) {
        tabla.getItems().remove(tabla.getSelectionModel().getSelectedItem());
        bttnEliminarTipo.setDisable(true);
         tabla.prefHeightProperty().bind(
                tabla.fixedCellSizeProperty().multiply(tabla.getItems().size()).add(25 + 1) // +25 para la cabecera
        );
    }

}
