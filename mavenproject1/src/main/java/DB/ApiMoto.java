/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Motos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DB_Moto {

    public boolean insert(Motos moto) {
        // Establecemos la conexión (suponiendo que tienes un método getConn())
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para insertar el nuevo registro en la tabla MOTO
            String sql = "INSERT INTO [dbo].MOTO (ID_MARCA, ID_MODELO, ID_PRODUCTO, ANIO, COLOR) VALUES (?, ?, ?, ?, ?)";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer los valores de los parámetros
                pstmt.setInt(1, moto.getIdMarca());
                pstmt.setInt(2, moto.getIdModelo());
                pstmt.setInt(3, moto.getIdProducto());
                pstmt.setDate(4, moto.getAnio()); // Convertir a java.sql.Date
                pstmt.setString(5, moto.getColor());

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

    public Motos getMotoById(int idMotoBuscado) {
        Motos moto = null;
        String query = "SELECT ID_MARCA, ID_MODELO, ID_PRODUCTO, ANIO, COLOR FROM [dbo].MOTO WHERE ID_PRODUCTO = ?";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos el ID del registro que queremos obtener
            stmt.setInt(1, idMotoBuscado);

            ResultSet rs = stmt.executeQuery();
 
            // Avanzamos al primer registro (si existe) antes de obtener el valor
            if (rs.next()) {
                moto = new Motos(
                        idMotoBuscado,
                        rs.getInt("ID_MARCA"),
                        rs.getInt("ID_MODELO"),
                        rs.getInt("ID_PRODUCTO"),
                        rs.getDate("ANIO"),
                        rs.getString("COLOR")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la moto con ID " + idMotoBuscado + ": " + e.getMessage());
        }
        return moto;
    }

}
