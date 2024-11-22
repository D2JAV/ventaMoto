/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Accesorios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DB_Accesorio {

    public boolean insert(Accesorios accesorio) {
        // Establecemos la conexión (suponiendo que tienes un método getConn())
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para insertar el nuevo registro en la tabla ACCESORIO
            String sql = "INSERT INTO [dbo].ACCESORIO (ID_PRODUCTO, TIPO) VALUES (?, ?)";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer los valores de los parámetros
                pstmt.setInt(1, accesorio.getIdProducto());
                pstmt.setString(2, accesorio.getTipo());

                // Ejecutar la consulta de inserción
                int rowsAffected = pstmt.executeUpdate();

                // Si se insertó una fila, la operación fue exitosa
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si ocurre un error, devolver false
        return false;
    }

    public Accesorios getAccesorioById(int idAccesorioBuscado) {
        Accesorios accesorio = null;
        String query = "SELECT ID_PRODUCTO, TIPO FROM [dbo].ACCESORIO WHERE ID_PRODUCTO = ?";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos el ID del registro que queremos obtener
            stmt.setInt(1, idAccesorioBuscado);

            ResultSet rs = stmt.executeQuery();

            // Avanzamos al primer registro (si existe) antes de obtener el valor
            if (rs.next()) {
                accesorio = new Accesorios(
                        idAccesorioBuscado,
                        rs.getInt("ID_PRODUCTO"),
                        rs.getString("TIPO")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el accesorio con ID " + idAccesorioBuscado + ": " + e.getMessage());
        }
        return accesorio;
    }

}
