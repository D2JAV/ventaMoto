package app.controller;

import DB.ApiUsuario;
import app.clases.Detalle;
import app.clases.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modulos.Funciones;
import modulos.Ventanas;

public class UsuarioLista {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
 

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Usuario> tabla;

    @FXML
    private TableView<Detalle> tabla2;

    @FXML
    void agregar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UsuarioAgregar.fxml"));
        fxmlLoader.load();

        UsuarioAgregar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (formulario.getUsuarioGenerado() != null) {
            refrescarTabla();
        }
    }

    @FXML
    void eliminar(ActionEvent event) {

        Usuario usuarioSelect = tabla.getSelectionModel().getSelectedItem();
        new ApiUsuario().eliminar(usuarioSelect.getIdUsuario());
        refrescarTabla();
        btnEliminar.setDisable(true);
        

    }

    @FXML
    void initialize() {
         assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'UsuarioLista.fxml'.";

    }

    String[][] columnas = {
        //cabecera-propiedad-ancho  
        {"Id", "formatIdUsuario", "0"},
        {"Cargo", "formatCargo", "0"},
        {"Nombre", "nombre", "0"},
        {"Apellido", "apellido", "0"}

    };
    String[][] columnas2 = {
        //cabecera-propiedad-ancho  
        {"Propiedad", "propiedad", "0"},
        {"Detalle", "valor", "0"}

    };

    /*
     this.idUsuario = idUsuario;
        this.idCargo = idCargo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.constrasenia = constrasenia;
     */
    public void ini() {

        new Funciones().agregarColumnasAncho(columnas, tabla);
        new Funciones().agregarColumnasAncho(columnas2, tabla2);
 
        btnEliminar.setDisable(true);
        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                btnEliminar.setDisable(newValue == null);

            tabla2.getItems().clear();
            if (newValue != null) {
                String[] cabecera = {"EMAIL", "DIRECCION", "TELEFONO"};
                String[] list = {newValue.getEmail(), newValue.getDireccion(), newValue.getTelefono()};

                for (int i = 0; i < list.length; i++) {
                    Detalle detalle = new Detalle(cabecera[i], list[i]);
                    tabla2.getItems().add(detalle);
                }
            }
        });

        refrescarTabla();
    }

    public void refrescarTabla() {
        tabla.getItems().clear();
        tabla.getItems().addAll(new ApiUsuario().listarConCargo());

    }

}
