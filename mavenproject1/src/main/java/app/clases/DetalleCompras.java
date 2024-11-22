/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

import app.controller.CompraAgregar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import modulos.Formatos;

/**
 *
 * @author CYBERTEL
 */
public class DetalleCompras {

    private int idCompra;
    private int idProducto;
    private int cantidad;
    private double precioCompra;
    private double precioTotal;//esto es el precio total de cada detalle

    private String productoDescripcion;
    private String detalleProducto;

    private String formatIdCompra, formatIdProducto, formatPrecioCompra, formatPrecioTotal;

    private HBox hboxCantidad;
    private TextField textFieldPrecioCompra;
    private Label labelPrecioTotal;

    public DetalleCompras() {
    }

    public DetalleCompras(int idCompra, int idProducto, int cantidad, double precioCompra) {
        this.idCompra = idCompra;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;

        this.precioTotal = cantidad * precioCompra;

        this.formatIdCompra = String.format("C-%04d", idProducto);
        this.formatIdProducto = String.format("P-%04d", idProducto);

        this.formatPrecioCompra = String.format("%.2f", precioCompra);
        this.formatPrecioTotal = String.format("%.2f", precioTotal);
    }

    public void setTablaCrearCompra(CompraAgregar compraAgregar) {
        this.textFieldPrecioCompra = new TextField();
        new Formatos().formato3(textFieldPrecioCompra);
        this.textFieldPrecioCompra.setText("0");
        this.labelPrecioTotal = new Label("0,00");

        this.hboxCantidad = new HBox();

        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setText(String.valueOf(cantidad));

        textField.setAlignment(Pos.CENTER);
        new Formatos().enteros(textField);

        Button btnDerecha = new Button();
        btnDerecha.setText(">");
        btnDerecha.setMinWidth(25);

        btnDerecha.setOnAction(event3 -> {
            cantidad = Integer.parseInt(textField.getText());
            cantidad++;
            textField.setText(String.valueOf(cantidad));

            precioCompra = Double.parseDouble(textFieldPrecioCompra.getText());
            actualizarTotales(compraAgregar);
        });

        Button btnIzquierda = new Button();
        btnIzquierda.setText("<");
        btnIzquierda.setMinWidth(25);

        btnIzquierda.setOnAction(event2 -> {

            cantidad = Integer.parseInt(textField.getText());
            if (cantidad > 1) {
                cantidad--;
                textField.setText(String.valueOf(cantidad));
                precioCompra = Double.parseDouble(textFieldPrecioCompra.getText());

                actualizarTotales(compraAgregar);
            }
        });

        hboxCantidad.getChildren().add(btnIzquierda);
        hboxCantidad.getChildren().add(textField);
        hboxCantidad.getChildren().add(btnDerecha);

        textFieldPrecioCompra.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*(\\.\\d*)?")) {

                cantidad = Integer.parseInt(textField.getText());
                precioCompra = Double.parseDouble(newValue);

                actualizarTotales(compraAgregar);
            }
        });

        textFieldPrecioCompra.setAlignment(Pos.CENTER);
    }

    private void actualizarTotales(CompraAgregar compraAgregar) {
        this.precioTotal = precioCompra * cantidad;
        this.labelPrecioTotal.setText(String.format("%.2f", precioTotal));

        compraAgregar.actualizarMontoTotal();
    }

    public String getFormatPrecioCompra() {
        return formatPrecioCompra;
    }

    public void setFormatPrecioCompra(String formatPrecioCompra) {
        this.formatPrecioCompra = formatPrecioCompra;
    }

    public String getFormatPrecioTotal() {
        return formatPrecioTotal;
    }

    public void setFormatPrecioTotal(String formatPrecioTotal) {
        this.formatPrecioTotal = formatPrecioTotal;
    }

    
    
    
    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public HBox getHboxCantidad() {
        return hboxCantidad;
    }

    public void setHboxCantidad(HBox hboxCantidad) {
        this.hboxCantidad = hboxCantidad;
    }

    public TextField getTextFieldPrecioCompra() {
        return textFieldPrecioCompra;
    }

    public void setTextFieldPrecioCompra(TextField textFieldPrecioCompra) {
        this.textFieldPrecioCompra = textFieldPrecioCompra;
    }

    public Label getLabelPrecioTotal() {
        return labelPrecioTotal;
    }

    public void setLabelPrecioTotal(Label labelPrecioTotal) {
        this.labelPrecioTotal = labelPrecioTotal;
    }

    public String getProductoDescripcion() {
        return productoDescripcion;
    }

    public void setProductoDescripcion(String productoDescripcion) {
        this.productoDescripcion = productoDescripcion;
    }

    public String getDetalleProducto() {
        return detalleProducto;
    }

    public void setDetalleProducto(String detalleProducto) {
        this.detalleProducto = detalleProducto;
    }

    public String getFormatIdCompra() {
        return formatIdCompra;
    }

    public void setFormatIdCompra(String formatIdCompra) {
        this.formatIdCompra = formatIdCompra;
    }

    public String getFormatIdProducto() {
        return formatIdProducto;
    }

    public void setFormatIdProducto(String formatIdProducto) {
        this.formatIdProducto = formatIdProducto;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
