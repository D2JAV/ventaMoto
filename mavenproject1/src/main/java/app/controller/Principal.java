 
package app.controller;

import DB.DB_Info;
import com.mycompany.mavenproject1.App;
import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modulos.Ventanas;

/**
 * FXML Controller class
 *
 * @author CYBERTEL
 */
public class Principal implements Initializable {

    Stage stage;
    double xOffest = 0;
    double yOffest = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private Label lblCopyright;

    @FXML
    private Label lblDirecionEmpresa;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblNombreEmpresa;

    @FXML
    private Label lblNombrePorgrama;

    @FXML
    private Label lblRucEmpresa;

    @FXML
    private Label lblVersion;

    @FXML
    private TabPane tabPane;

    public void ini(Stage stage) {
        this.stage = stage;
        DB_Info dbInfo = new DB_Info();
        lblNombreEmpresa.setText(dbInfo.getNombreEmpresa());
        lblDirecionEmpresa.setText(dbInfo.getDireccionEmpresa());
        lblRucEmpresa.setText(dbInfo.getRucEmpresa());

        lblNombrePorgrama.setText("Gestion de Inventario");
        lblCopyright.setText("@Copyright Grupo 06 - 2025");
        lblVersion.setText("Version 0.0a");

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MMM");
        String fechaFormateada = fechaActual.format(formato);
        lblFecha.setText(fechaFormateada.toUpperCase());

    }

    @FXML
    void productos(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProductoLista.fxml"));
        Scene escena = new Scene(fxmlLoader.load());
        ProductoLista formulario = fxmlLoader.getController();
        formulario.ini();
        Tab tab = addTab("Producto lista", escena);
    }

    @FXML
    void proveedorLista(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProveedorLista.fxml"));
        Scene escena = new Scene(fxmlLoader.load());
        ProveedorLista formulario = fxmlLoader.getController();
        formulario.ini();
        Tab tab = addTab("Proveedor nuevo", escena);
    }

    @FXML
    void compraList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CompraLista.fxml"));
        Scene escena = new Scene(fxmlLoader.load());
        CompraLista formulario = fxmlLoader.getController();
        formulario.ini(this);
        Tab tab = addTab("Compra Registro", escena);

    }

    @FXML
    void proveedorNuevo(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProveedorAgregar.fxml"));
        fxmlLoader.load();

        ProveedorAgregar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        nVentana.show();
    }

    @FXML
    void clienteLista(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ClienteLista.fxml"));

        Scene escena = new Scene(fxmlLoader.load());

        ClienteLista formulario = fxmlLoader.getController();
        formulario.ini();

        Tab tab = addTab("Cliente lista", escena);
    }

    @FXML
    void clienteNuevo(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ClienteAgregar.fxml"));
        fxmlLoader.load();

        ClienteAgregar formulario = fxmlLoader.getController();
        Stage nVentana = new Ventanas().inicarVentanaSecundaria(fxmlLoader);
        formulario.setStage(nVentana);
        formulario.ini();
        //nVentana.initStyle(StageStyle.UNDECORATED);
        nVentana.show();

    }

    @FXML
    void configuracion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Configuracion.fxml"));

        Scene escena = new Scene(fxmlLoader.load());

        Configuracion formulario = fxmlLoader.getController();
        formulario.ini();

        Tab tab = addTab("Configuracion", escena);
        formulario.guardarConfig(tabPane, tab, lblDirecionEmpresa, lblNombreEmpresa, lblRucEmpresa);
    }

    @FXML
    void usuarioLista(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UsuarioLista.fxml")); 

        Scene escena = new Scene(fxmlLoader.load());

        UsuarioLista formulario = fxmlLoader.getController();
        formulario.ini();

        Tab tab = addTab("Usuario lista", escena);
    }

    public Tab addTab(String title, Scene escena) {
        HBox cabecera = new HBox();
        cabecera.setStyle("-fx-alignment: center ");
        Label label = new Label();
        label.setText(title);
        label.setPadding(new Insets(0, 2, 2, 5));
        label.setStyle("-fx-text-fill: black");
        Button button = new Button();
        button.setPadding(new Insets(2, 2, 2, 2));
        button.setStyle("-fx-background-color: transparent");
        ImageView imageView = new javafx.scene.image.ImageView(new Image("/imagenes/cerrar.png"));
        button.setGraphic(imageView);

        cabecera.getChildren().add(label);
        cabecera.getChildren().add(button);

        Tab tab = new Tab();
        tab.setGraphic(cabecera);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(escena.getRoot());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        tab.setContent(scrollPane);

        scrollPane.vbarPolicyProperty().set(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();
            double scrollSpeed = 1.3; // Ajusta la velocidad de desplazamiento aquí

            scrollPane.setVvalue(scrollPane.getVvalue() - 1 * deltaY * scrollSpeed / 100);
            event.consume();

        });

        button.setOnAction(event1 -> {
            tab.getTabPane().getTabs().remove(tab);

        });

        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);

        return tab;
    }

    @FXML
    void minimizar(ActionEvent event) {
       stage.setIconified(true);
    }

    @FXML
    void cerrar(ActionEvent event) {
        Platform.exit();
    }
}
