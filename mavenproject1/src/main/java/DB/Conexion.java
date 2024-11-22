/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class Conexion {

    private static Connection conectar = null;
    private static final String usuario = "userVentas";
    private static final String contrasenia = "123";
    private static final String db = "moto_db";
    private static final String ip = "localhost";
    private static final String puerto = "1433";

    private static final String cadena = "jdbc:sqlserver://" + ip + ":" + puerto
            + ";databaseName=" + db + ";user=" + usuario + ";password=" + contrasenia
            + ";encrypt=true;trustServerCertificate=true";

    public Conexion() {
    }

    public Connection getConn() {

        try {
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
        } catch (Exception e) {
            System.out.println("Error al establecer conexion");
        }
        return conectar;
    }

}
