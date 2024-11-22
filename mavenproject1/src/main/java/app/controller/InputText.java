package app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InputText {

    Stage stage;
    double xOffest = 0;
    double yOffest = 0;

    String texto = null;

    @FXML
    void cerrar(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    void keryboardPressed(KeyEvent event) {
        String name = event.getCode().getName().toUpperCase();

        if (name.equals("ENTER")) {
            texto = txt.getText();
            this.stage.close();
        } else if (name.equals("ESC")) {
            if (this.stage != null) {
                texto = null;
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
    private TextField txt;

    @FXML
    void initialize() {
        assert txt != null : "fx:id=\"txt\" was not injected: check your FXML file 'InputText.fxml'.";

    }

    public void ini(String textDefault) {
        txt.setText(textDefault);
        txt.selectHome();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
