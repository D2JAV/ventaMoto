package app.controller;

import DB.DB_Clientes;
import app.clases.Clientes;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modulos.Formatos;

public class ClienteAgregar {

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
    private Label lblErroEmail;

    @FXML
    private Label lblErrorApellido;

    @FXML
    private Label lblErrorDireccion;

    @FXML
    private Label lblErrorNombre;

    @FXML
    private Label lblErrorTelefono;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    Clientes clienteGenerado;

    @FXML
    void aceptar(ActionEvent event) {

        refrescarErrores();

        boolean continuar = true;

        TextField[] text = {txtNombre, txtApellido, txtEmail, txtTelefono, txtDireccion};
        Label[] label = {lblErrorNombre, lblErrorApellido, lblErroEmail, lblErrorTelefono, lblErrorDireccion};

        for (int i = 0; i < label.length; i++) {
            if (text[i].getText().isEmpty()) {
                label[i].setText("*error");
                continuar = false;
            }
        }

        if (txtTelefono.getText().length() != 9) {
            continuar = false;
            lblErrorTelefono.setText("*error");
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(txtEmail.getText());

        if (!matcher.matches()) {
            continuar = false;

            lblErroEmail.setText("*error");
        }

        if (!continuar) {
            return;
        }

        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();

        clienteGenerado = new Clientes(-1, nombre, apellido, email, telefono, direccion);
        new DB_Clientes().insertar(clienteGenerado);
        this.stage.close();
    }

    @FXML
    void initialize() {

    }

    public void refrescarErrores() {
        Label[] list = {lblErrorNombre, lblErrorApellido, lblErroEmail, lblErrorTelefono, lblErrorDireccion};

        for (Label label : list) {
            label.setText("");
            label.setStyle("-fx-text-fill: red;");
        }

    }

    public void ini() {
        refrescarErrores();
        new Formatos().correo(txtEmail);
        new Formatos().telefono(txtTelefono);
    }

    public Clientes getClienteGenerado() {
        return clienteGenerado;
    }

    public void setClienteGenerado(Clientes clienteGenerado) {
        this.clienteGenerado = clienteGenerado;
    }

}
