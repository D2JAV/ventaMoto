/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulos;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ASUS
 */
public class Funciones {

    public void reiniciarErroes(Label[] list) {
        for (Label label : list) {
            label.setText("");
            label.setStyle("-fx-text-fill: red;");

        }

    }

    public void reiniciarDatePicker(DatePicker datePicker) {

        Timestamp fechaCero = new Timestamp(new Date().getTime());
        datePicker.setValue(fechaCero.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    }

    public void agregarColumnasAncho(String[][] columnas, TableView tabla) {

        for (int i = 0; i < columnas.length; i++) {
            TableColumn<?, ?> columna = new TableColumn(columnas[i][0]);
            columna.setCellValueFactory(new PropertyValueFactory<>(columnas[i][1]));
            columna.setSortable(false);
            int ancho = Integer.valueOf(columnas[i][2]);
            if (ancho > 0) {
                columna.setMaxWidth(ancho);
                columna.setMinWidth(ancho);
            }

            columna.setStyle("-fx-alignment: TOP-CENTER");
            tabla.getColumns().add(columna);
        }
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
}
