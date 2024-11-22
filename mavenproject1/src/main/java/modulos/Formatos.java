/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulos;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author ASUS
 */
public class Formatos {

    public void enteros(TextField textField) {

        //este formato solo permite entradas de valores enteros mayor que 1
        textField = new TextField();

        // Expresión regular para números enteros mayores que 1
        Pattern validIntegerPattern = Pattern.compile("[2-9]\\d*");

        // Define un filtro para el TextFormatter
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (validIntegerPattern.matcher(newText).matches()) {
                return change; // Acepta el cambio si coincide con el patrón
            } else {
                return null; // Rechaza el cambio si no coincide
            }
        };

        // Crea un TextFormatter con el filtro y asígnalo al TextField
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);

    }

    public void decimales(TextField textField) {

        // Crear un filtro para permitir solo decimales
        UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*\\.?[0-9]*")) { // Permite números decimales, con signo opcional
                return change;
            }
            return null; // Rechaza el cambio si no cumple con el formato
        };

        // Aplicar el TextFormatter al TextField
        TextFormatter<String> textFormatter = new TextFormatter<>(decimalFilter);
        textField.setTextFormatter(textFormatter);

    }

    public void formato3(TextField textField) {

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    textField.setText(oldValue);
                }
            }
        });
    }
    

    public void correo(TextField textField) {

        // Patrón para restringir el formato de correo a example@empresa.com
        //Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.-]*\\.com?$");
        Pattern part1 = Pattern.compile("^[a-zA-Z0-9._%+-]*$");
        Pattern part2 = Pattern.compile("^[a-zA-Z0-9._%+-]*@$");
        Pattern part3 = Pattern.compile("^[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.]*$");
        Pattern part4 = Pattern.compile("^[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.]*\\.?$");
        Pattern part5 = Pattern.compile("^[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.]*\\.c?$");
        Pattern part6 = Pattern.compile("^[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.]*\\.co?$");
        Pattern part7 = Pattern.compile("^[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.]*\\.com?$");

        Pattern[] list = {part1, part2, part3, part4, part5, part6, part7};

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            for (int i = 0; i < list.length; i++) {
                if (list[i].matcher(newText).matches()) {
                    return change;
                }

            }

            if (newText.isEmpty()) {
                return change;
            }
            return null; // Rechaza el cambio si no cumple el patrón
        };

        TextFormatter<String> formatter = new TextFormatter<>(filter);
        textField.setTextFormatter(formatter);

    }

    public void telefono(TextField textField) {
        Pattern phonePattern = Pattern.compile("9\\d{0,8}");

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (phonePattern.matcher(newText).matches()) {
                return change; // Acepta el cambio si cumple el patrón
            }
            return null; // Rechaza el cambio si no cumple el patrón
        };

        TextFormatter<String> formatter = new TextFormatter<>(filter);
        textField.setTextFormatter(formatter);
    }

}
