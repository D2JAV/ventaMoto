package app.controller;

import DB.DB_Clientes;
import DB.DB_Producto;
import app.clases.Clientes;
import app.clases.Detalle;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modulos.Funciones;

public class ClienteSeleccionar {

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
    private Button btnAceptar;

    @FXML
    private TableView<Clientes> tabla;

    @FXML
    private TextField txtBuscador;

    Clientes clienteSelect;

    @FXML
    void aceptar(ActionEvent event) {

        clienteSelect = tabla.getSelectionModel().getSelectedItem();
        this.stage.close();

    }

    public Clientes getClienteSelect() {
        return clienteSelect;
    }

    public void setClienteSelect(Clientes clienteSelect) {
        this.clienteSelect = clienteSelect;
    }

    @FXML
    void buscar(ActionEvent event) {
        String busqueda = txtBuscador.getText();

        if (!busqueda.isEmpty()) {
            tabla.getItems().clear();
            tabla.getItems().addAll(new DB_Clientes().byName(busqueda));
        }

    }

    @FXML
    void initialize() {
        assert btnAceptar != null : "fx:id=\"btnAceptar\" was not injected: check your FXML file 'ClienteSeleccionar.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'ClienteSeleccionar.fxml'.";
        assert txtBuscador != null : "fx:id=\"txtBuscador\" was not injected: check your FXML file 'ClienteSeleccionar.fxml'.";

    }
    String[][] columnas = {
        //cabecera-propiedad-ancho 
        {"Id", "formatIdCliente", "90"},
        {"Descripcion", "nombre", "0"},
        {"Categoria", "apellido", "0"}};

    public void ini() {
        new Funciones().agregarColumnasAncho(columnas, tabla);
        btnAceptar.setDisable(true);
        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnAceptar.setDisable(newValue == null);

        });

        refrescarTabla();
    }

    public void refrescarTabla() {
        txtBuscador.clear();
        tabla.getItems().clear();
        tabla.getItems().addAll(new DB_Clientes().listar());
    }

}
