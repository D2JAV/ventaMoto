package com.mycompany.mavenproject1;

import DB.DB_Info;
import DB.DB_Marca;
import DB.DB_Modelo;
import app.controller.Login;
import app.controller.Principal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import modulos.Encriptar;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene; 
    public static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException, ParseException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = fxmlLoader.load();
        Login principal = fxmlLoader.getController();
        root.setEffect(darSombra());
        scene = new Scene(root);
        stage.setScene(scene); 

        stage.initStyle(StageStyle.UNDECORATED);
        principal.ini(stage);
        stage.show();

        this.stage = stage; 
        new DB_Info().insertDefault();
        new DB_Marca().insertDefault();
        new DB_Modelo().insertDefault();
    }

    public DropShadow darSombra() {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5)); // Sombra negra
        dropShadow.setRadius(15); // Radio de la sombra
        dropShadow.setOffsetX(5); // Desplazamiento horizontal de la sombra
        dropShadow.setOffsetY(5); // Desplazamiento vertical de la sombra
        return dropShadow;
    }

}
