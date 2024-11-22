package app.controller;

import DB.ApiUsuario;
import app.clases.Usuario;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modulos.Encriptar;
import modulos.Formatos;
import modulos.Funciones;

public class UsuarioAgregar {

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
    private Label lblErroApellido;

    @FXML
    private Label lblErroEmail;

    @FXML
    private Label lblErrorContrasenia;

    @FXML
    private Label lblErrorDireccion;

    @FXML
    private Label lblErrorNombre;

    @FXML
    private Label lblErrorUsuario;

    @FXML
    private Label lblErrorTelefono;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnCargo;

    @FXML
    void aceptar(ActionEvent event) {

        boolean continuar = true;
        refrescarErrores();

        TextField[] txtList = {txtNombre, txtApellido, txtEmail, txtTelefono, txtDireccion, txtContrasenia, txtUsuario};
        Label[] lblError = {lblErrorNombre, lblErroApellido, lblErroEmail, lblErrorTelefono, lblErrorDireccion, lblErrorContrasenia, lblErrorUsuario};

        for (int i = 0; i < lblError.length; i++) {
            if (txtList[i].getText().isEmpty()) {
                continuar = false;
                lblError[i].setText("*error");
            }
        }

        if (txtTelefono.getText().length() != 9) {
            continuar = false;
            lblErrorTelefono.setText("*error");
        }

        if (txtContrasenia.getText().length() < 8) {
            continuar = false;
            lblErrorContrasenia.setText("*error");
        }

        String usuario = txtUsuario.getText();

        if (!new ApiUsuario().esUsuarioUnico(usuario)) {
            continuar = false;
            lblErrorUsuario.setText("*error");
        }

        if (!continuar) {
            return;
        }

        int idCargo = 1;

        if (btnCargo.getText().toLowerCase().equals("Administrador")) {
            idCargo = 2;
        }

        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String contrasenia = new Encriptar().getContrasenia(txtContrasenia.getText());

        usuarioGenerado = new Usuario(-1, idCargo, usuario, nombre, apellido, email, telefono, direccion, contrasenia);

        new ApiUsuario().insertar(usuarioGenerado);
        usuarioGenerado.setConstrasenia("");
        this.stage.close();

    }

    Usuario usuarioGenerado;

    @FXML
    void initialize() {
        assert lblErroApellido != null : "fx:id=\"lblErroApellido\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert lblErroEmail != null : "fx:id=\"lblErroEmail\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert lblErrorContrasenia != null : "fx:id=\"lblErrorContrasenia\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert lblErrorDireccion != null : "fx:id=\"lblErrorDireccion\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert lblErrorNombre != null : "fx:id=\"lblErrorNombre\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert lblErrorTelefono != null : "fx:id=\"lblErrorTelefono\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert txtApellido != null : "fx:id=\"txtApellido\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert txtContrasenia != null : "fx:id=\"txtContrasenia\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert txtDireccion != null : "fx:id=\"txtDireccion\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";
        assert txtTelefono != null : "fx:id=\"txtTelefono\" was not injected: check your FXML file 'UsuarioAgregar.fxml'.";

    }

    public void ini() {
        refrescarErrores();
        new Formatos().correo(txtEmail);
        new Formatos().telefono(txtTelefono);

    }

    @FXML
    void cambiarCargo(ActionEvent event) {
        btnCargo.setText("ADMNISTRADOR");
    }

    public void refrescarErrores() {

        Label[] list = {lblErrorNombre, lblErroApellido, lblErroEmail, lblErrorTelefono,
            lblErrorDireccion, lblErrorContrasenia, lblErrorUsuario};

        new Funciones().reiniciarErroes(list);
    }

    public Usuario getUsuarioGenerado() {
        return usuarioGenerado;
    }

    public void setUsuarioGenerado(Usuario usuarioGenerado) {
        this.usuarioGenerado = usuarioGenerado;
    }

}
