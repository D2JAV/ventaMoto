package app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class CompraView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hbox;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    void initialize() {
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'CompraView.fxml'.";

    }

    public void ini(int idCompra) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CompraHojaPrincipal.fxml"));
        Parent root = fxmlLoader.load();

        CompraHojaPrincipal formulario = fxmlLoader.getController();
        formulario.ini(idCompra);
        hbox.getChildren().add(root);
    }

}
