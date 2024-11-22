/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.controller;

import DB.DB_Accesorio;
import DB.DB_Marca;
import DB.DB_Modelo;
import DB.DB_Moto;
import DB.DB_Producto;
import app.clases.Accesorios;
import app.clases.Marca;
import app.clases.Modelo;
import app.clases.Motos;
import app.clases.Producto;
import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import modulos.Formatos;
import modulos.Ventanas;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ProductoAgregar implements Initializable {

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
    private TextField txtAccTipo;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtMotoAnio;

    @FXML
    private TextField txtMotoColor;

    @FXML
    private TextField txtMotoMarca;

    @FXML
    private TextField txtMotoModelo;

    @FXML
    private TextField txtPrecio;

    @FXML
    private Label txtErroTipo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    Marca marcaSelect;
    Modelo modeloSelect;
    Producto productoGenerado;
    String categoriaSelect;

    public void ini() {

        reiniciarErrores();
        txtMotoAnio.setEditable(false);
        txtMotoAnio.setText(String.valueOf(LocalDate.now().getYear()));
        categoriaSelect = "moto";
        contenedor.getChildren().remove(gridAccesorio);
        btnCategoria.setText(categoriaSelect.toUpperCase());

        new Formatos().decimales(txtPrecio);

        marcaSelect = new DB_Marca().getMarcaDefault();
        modeloSelect = new DB_Modelo().getModeloDefault();

        txtMotoMarca.setEditable(false);
        txtMotoModelo.setEditable(false);

        setearTextos();
    }

    public void reiniciarErrores() {

        Label[] labelsErrores = {txtErrorDescripcion, txtErrorPrecio, txtErroTipo, txtErrorColor};

        for (Label label : labelsErrores) {
            label.setStyle("-fx-text-fill: red");
            label.setText("");
        }

    }

    public void setearTextos() {
        txtMotoMarca.setText(marcaSelect.getNombre().toUpperCase());
        txtMotoModelo.setText(modeloSelect.getNombre().toUpperCase());

    }

    @FXML
    void seleccionarMarca(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MarcaList.fxml"));
        fxmlLoader.load();

        MarcaList FORMULARIO = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        FORMULARIO.setStage(nVentana);
        FORMULARIO.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (FORMULARIO.getMarcaSelect() != null) {
            this.marcaSelect = FORMULARIO.getMarcaSelect();
            setearTextos();
        }
    }

    @FXML
    void seleccionarModelo(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModeloList.fxml"));
        fxmlLoader.load();

        ModeloList FORMULARIO = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        FORMULARIO.setStage(nVentana);
        FORMULARIO.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.showAndWait();

        if (FORMULARIO.getModeloSelect() != null) {
            this.modeloSelect = FORMULARIO.getModeloSelect();
            setearTextos();
        }
    }

    @FXML
    void aceptar(ActionEvent event) throws ParseException {

        reiniciarErrores();

        boolean continuar = true;

        if (txtDescripcion.getText().isEmpty()) {
            txtErrorDescripcion.setText("*error");
            continuar = false;
        }

        if (txtPrecio.getText().isEmpty()) {
            txtErrorPrecio.setText("*error");
            continuar = false;
        }

        if (txtMotoColor.getText().isEmpty() && categoriaSelect.equals("moto")) {
            txtErrorColor.setText("*error");
            continuar = false;
        }
        if (txtAccTipo.getText().isEmpty() && categoriaSelect.equals("accesorio")) {
            txtErroTipo.setText("*error");
            continuar = false;
        }

        if (!continuar) {
            return;
        }

        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());

        productoGenerado = new Producto(-1, descripcion, categoriaSelect, precio,0, false);
        int idProdGenerado = new DB_Producto().insertarProducto(productoGenerado);

        if (categoriaSelect.equals("moto")) {
            int anio = Integer.parseInt(txtMotoAnio.getText());
            //long anioL = (anio ) * 86400000 * 356;

            String dateString = "01 01 " + anio; // Fecha en formato texto
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
            java.util.Date dateUtil = formatter.parse(dateString);
            Date date = new Date(dateUtil.getTime());
            String color = txtMotoColor.getText();

            Motos moto = new Motos(-1, marcaSelect.getIdMarca(), modeloSelect.getIdModelo(), idProdGenerado, date, color);

            new DB_Moto().insert(moto);
        } else {

            String tipo = txtAccTipo.getText();
            Accesorios accesorio = new Accesorios(-1, idProdGenerado, tipo);

            new DB_Accesorio().insert(accesorio);
        }

        this.stage.close();
    }

    public Producto getProductoGenerado() {
        return productoGenerado;
    }

    public void setProductoGenerado(Producto productoGenerado) {
        this.productoGenerado = productoGenerado;
    }

    @FXML
    private Button btnCategoria;

    @FXML
    private GridPane gridAccesorio;

    @FXML
    private GridPane gridMoto;

    @FXML
    private Label txtErrorColor;

    @FXML
    private Label txtErrorDescripcion;

    @FXML
    private Label txtErrorPrecio;

    @FXML
    void agregarAnio(ActionEvent event) {
        int anioActual = LocalDate.now().getYear();
        int anioSelect = Integer.parseInt(txtMotoAnio.getText());

        if (anioSelect == anioActual) {
            return;
        }

        anioSelect++;
        txtMotoAnio.setText(String.valueOf(anioSelect));

    }

    @FXML
    private VBox contenedor;

    @FXML
    void cambiarCategoria(ActionEvent event) {

        if (categoriaSelect.equals("moto")) {
            categoriaSelect = "accesorio";

            contenedor.getChildren().remove(gridMoto);
            contenedor.getChildren().add(gridAccesorio);

        } else {
            categoriaSelect = "moto";

            contenedor.getChildren().remove(gridAccesorio);
            contenedor.getChildren().add(gridMoto);
        }

        btnCategoria.setText(categoriaSelect.toUpperCase());

    }

    @FXML
    void quitarAnio(ActionEvent event) {
        int aniLimite = LocalDate.now().getYear() - 30;
        int anioSelect = Integer.parseInt(txtMotoAnio.getText());

        if (anioSelect == aniLimite) {
            return;
        }
        anioSelect--;
        txtMotoAnio.setText(String.valueOf(anioSelect));

    }

}
