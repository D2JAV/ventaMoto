package app.controller;

import DB.ApiUsuario;
import DB.DB_Info;
import DB.DB_Marca;
import DB.DB_Modelo; 
import com.mycompany.mavenproject1.App;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modulos.Encriptar;
import modulos.Funciones;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblErrorContrasenia;

    @FXML
    private Label lblErrorUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtUsuario;

    @FXML
    void aceptar(ActionEvent event) throws IOException {

        refrescarErrores();
        boolean continuar = true;

        if (txtUsuario.getText().isEmpty()) {
            continuar = false;
            lblErrorUsuario.setText("*Campo Obligatorio");
        }
        if (txtContrasenia.getText().isEmpty()) {
            continuar = false;
            lblErrorContrasenia.setText("*Campo Obligatorio");
        }

        String usuario = txtUsuario.getText();
        String contrasenia = new Encriptar().getContrasenia(txtContrasenia.getText());

        boolean comprobarUsuario = new ApiUsuario().verificarUsuario(usuario, contrasenia);

        if (!continuar) {
            return;
        }
        
        getStage().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
        Parent root = fxmlLoader.load();
        Principal principal = fxmlLoader.getController();
        root.setEffect(darSombra());
        Scene scene = new Scene(root);
        Stage stage  = new Stage ();
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
 
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.initStyle(StageStyle.UNDECORATED);

        App.stage = stage;
        stage.show();
        principal.ini(stage);

        new DB_Info().insertDefault();
        new DB_Marca().insertDefault();
        new DB_Modelo().insertDefault();
    }

    public void ini(Stage stage) {
        this.stage = stage;
        refrescarErrores();
    }
    
    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    

    public DropShadow darSombra() {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5)); // Sombra negra
        dropShadow.setRadius(15); // Radio de la sombra
        dropShadow.setOffsetX(5); // Desplazamiento horizontal de la sombra
        dropShadow.setOffsetY(5); // Desplazamiento vertical de la sombra
        return dropShadow;
    }

    @FXML
    void initialize() {
        assert lblErrorContrasenia != null : "fx:id=\"lblErrorContrasenia\" was not injected: check your FXML file 'Login.fxml'.";
        assert lblErrorUsuario != null : "fx:id=\"lblErrorUsuario\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtContrasenia != null : "fx:id=\"txtContrasenia\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtUsuario != null : "fx:id=\"txtUsuario\" was not injected: check your FXML file 'Login.fxml'.";

    } 
    public void refrescarErrores() {
        Label[] list = {lblErrorUsuario, lblErrorContrasenia};
        new Funciones().reiniciarErroes(list);
    }
}
