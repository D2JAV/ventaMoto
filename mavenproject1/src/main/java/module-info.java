module com.mycompany.mavenproject1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
 

    opens com.mycompany.mavenproject1 to javafx.fxml;
    opens app.controller to javafx.fxml;
    opens app.clases to javafx.base; 
    
    exports com.mycompany.mavenproject1;
}
