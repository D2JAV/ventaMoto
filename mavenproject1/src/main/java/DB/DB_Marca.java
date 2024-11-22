/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DB_Marca {

    public Marca getMarcaDefault() {
        Marca marca = null;
        String query = "SELECT NOMBRE FROM [dbo].MARCA WHERE ID_MARCA = ?";
        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos el ID del registro que queremos obtener (en este caso, ID 1)
            stmt.setInt(1, 1);
            ResultSet rs = stmt.executeQuery();

            // Avanzamos al primer registro (si existe) antes de obtener el valor
            if (rs.next()) {
                marca = new Marca(1, rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre del modelo: " + e.getMessage());
        }
        return marca;
    }

    public ArrayList<Marca> listarMarcas() {
        ArrayList lista = new <Marca> ArrayList();
        String query = "SELECT ID_MARCA, NOMBRE FROM [dbo].MARCA;";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Recorremos todos los resultados y los agregamos a la lista
            while (rs.next()) {
                int id = rs.getInt("ID_MARCA");
                String nombre = rs.getString("NOMBRE");
                Marca marca = new Marca(id, nombre);
                lista.add(marca);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al listar marcas: " + e.getMessage());
        }

        return lista;
    }

    public boolean insertarMarca(String nombreMarca) {
        // Establecemos la conexión (suponiendo que tienes un método getConn())
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para insertar el nuevo registro
            String sql = "INSERT INTO [dbo].MARCA (NOMBRE) VALUES (?)";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer el valor del parámetro
                pstmt.setString(1, nombreMarca);

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

    public boolean insertDefault() {
        String nombreMarca = "Default";
        // Consulta SQL para verificar si existe un registro con el nombre proporcionado
        String verificarExistenciaSQL = "SELECT COUNT(*) FROM [dbo].MARCA WHERE ID_MARCA = 1";
        // Consulta SQL para insertar un nuevo registro
        String insertarSQL = "INSERT INTO [dbo].MARCA (NOMBRE) VALUES (?)";

        try (Connection conn = new Conexion().getConn()) {
            // Verificar si el registro ya existe
            try (PreparedStatement verificarStmt = conn.prepareStatement(verificarExistenciaSQL)) {

                try (ResultSet rs = verificarStmt.executeQuery()) {
                    rs.next();
                    int count = rs.getInt(1);

                    if (count == 0) {  // Solo realiza el INSERT si no existe el registro
                        try (PreparedStatement pstmt = conn.prepareStatement(insertarSQL)) {
                            pstmt.setString(1, nombreMarca);
                            int rowsAffected = pstmt.executeUpdate();
                            return rowsAffected > 0;  // Retorna true si se insertó el registro
                        }
                    } else {
                        System.out.println("El registro con nombre '" + nombreMarca + "' ya existe. No se realiza el insert.");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si ocurre un error, devolver false
        return false;
    }

    public Marca getMarcaById(int idMarcaBuscada) {
        Marca marca = null;
        String query = "SELECT NOMBRE FROM [dbo].MARCA WHERE ID_MARCA = ?";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos el ID del registro que queremos obtener
            stmt.setInt(1, idMarcaBuscada);

            ResultSet rs = stmt.executeQuery();

            // Avanzamos al primer registro (si existe) antes de obtener el valor
            if (rs.next()) {
                marca = new Marca(
                        idMarcaBuscada,
                        rs.getString("NOMBRE")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la marca con ID " + idMarcaBuscada + ": " + e.getMessage());
        }
        return marca;
    }

}
