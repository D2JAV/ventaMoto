/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulos;

import app.controller.InputText;
import com.mycompany.mavenproject1.App;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ASUS
 */
public class Ventanas {

    public void alerta(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        //  alert.setGraphic(new ImageView(this.getClass().getResource("/app_negocio/imagenes/logos/workshop48.png").toString()));
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();

    }

    public Stage inicarVentanaSecundaria(FXMLLoader fxmlLoader) {
        Stage stage = new Stage();
        //Parent root = fxmlLoader.load();
        stage.setScene(new Scene(fxmlLoader.getRoot()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Grupo 06");

        //stage.initOwner(stageMain);
        //stage.initOwner(Main.plataforma);
        stage.initStyle(StageStyle.UNDECORATED);

        Platform.runLater(() -> {
            centrarVentana(stage);
        });

        return stage;
    }

    public Object inicarVentanaSecundaria(String url, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
        Parent root = fxmlLoader.load();

        stage = new Stage();
        //Parent root = fxmlLoader.load();
        stage.setScene(new Scene(fxmlLoader.getRoot()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Grupo 06");

        //stage.initOwner(stageMain);
        //stage.initOwner(Main.plataforma);
        stage.initStyle(StageStyle.UNDECORATED);
 
        stage.show();

        return fxmlLoader.getController();
    }

    public void centrarVentana(Stage hija) {

        Stage padre = App.stage;

        double xVentana1 = padre.getX();
        double yVentana1 = padre.getY();

        // Calcula las coordenadas X e Y para que el centro de la ventana 2 coincida con el centro de la ventana 1
        double xVentana2 = xVentana1 + (padre.getWidth() - hija.getWidth()) / 2;
        double yVentana2 = yVentana1 + (padre.getHeight() - hija.getHeight()) / 2;

        // Establece las coordenadas X e Y de la ventana 2 para que coincidan con el centro de la ventana 1
        hija.setX(xVentana2);
        hija.setY(yVentana2);
    }

    public String inputText(String input) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/InputText.fxml"));
        fxmlLoader.load();

        InputText formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini(input);

        nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        return formulario.getTexto();

    }
}
