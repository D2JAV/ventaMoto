package app.controller;
 
import app.clases.DetalleCompras;
import java.net.URL; 
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SerieInput {

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
    private Button btnAceptar;

    @FXML
    private Label lblProducto;

    @FXML
    private TextField txtInput;

    @FXML
    void aceptar(ActionEvent event) {

        if (txtInput.getText().isEmpty()) {
            return;
        }

        serie = txtInput.getText();
        
    }
    
    String serie;

    @FXML
    void initialize() {
        assert btnAceptar != null : "fx:id=\"btnAceptar\" was not injected: check your FXML file 'SerieInput.fxml'.";
        assert lblProducto != null : "fx:id=\"lblProducto\" was not injected: check your FXML file 'SerieInput.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SerieInput.fxml'.";

    }

    public void ini(DetalleCompras detalleCompras) {
        lblProducto.setText(detalleCompras.getDetalleProducto());
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    } 
}
