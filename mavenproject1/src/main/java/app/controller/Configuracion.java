package app.controller;

import DB.DB_Info;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import modulos.Ventanas;

public class Configuracion {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtDireccionEmpresa;

    @FXML
    private TextField txtNombreEmpresa;

    @FXML
    private TextField txtRucEmpresa;

    @FXML
    void initialize() {
        assert txtDireccionEmpresa != null : "fx:id=\"txtDireccionEmpresa\" was not injected: check your FXML file 'Configuracion.fxml'.";
        assert txtNombreEmpresa != null : "fx:id=\"txtNombreEmpresa\" was not injected: check your FXML file 'Configuracion.fxml'.";
        assert txtRucEmpresa != null : "fx:id=\"txtRucEmpresa\" was not injected: check your FXML file 'Configuracion.fxml'.";

    }

    public void ini() {
        txtDireccionEmpresa.setText(new DB_Info().getDireccionEmpresa());
        txtNombreEmpresa.setText(new DB_Info().getNombreEmpresa());
        txtRucEmpresa.setText(new DB_Info().getRucEmpresa());

    }

    @FXML
    void guardarCambios(ActionEvent event) {
        String direccion = txtDireccionEmpresa.getText();
        String nombre = txtNombreEmpresa.getText();
        String ruc = txtRucEmpresa.getText();

        new DB_Info().modificarDireccionEmpresa(direccion);
        new DB_Info().modificarNombreEmpresa(nombre);
        new DB_Info().modificarRucEmpresa(ruc);

        new Ventanas().alerta("Exito", "Configuracion guardada");

        tabpane.getTabs().remove(tab);
        lblDirecionEmpresa.setText(direccion);
        lblNombreEmpresa.setText(nombre);
        lblRucEmpresa.setText(ruc); 
    }

    TabPane tabpane;
    Tab tab;
    Label lblDirecionEmpresa;
    Label lblNombreEmpresa;
    Label lblRucEmpresa;

    public void guardarConfig(TabPane tabpane, Tab tab, Label lblDirecionEmpresa, Label lblNombreEmpresa, Label lblRucEmpresa) {
        this.tabpane = tabpane;
        this.tab = tab;
        this.lblDirecionEmpresa = lblDirecionEmpresa;
        this.lblNombreEmpresa = lblNombreEmpresa;
        this.lblRucEmpresa = lblRucEmpresa;
    }
}
