/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DB_Modelo {

    public Modelo getModeloDefault() {
        Modelo modelo = null;
        String query = "SELECT NOMBRE FROM [dbo].MODELO WHERE ID_MODELO = 1";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos el ID del registro que queremos obtener (en este caso, ID 1)
            ResultSet rs = stmt.executeQuery();

            // Avanzamos al primer registro (si existe) antes de obtener el valor
            if (rs.next()) {

                modelo = new Modelo(1, rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre del modelo: " + e.getMessage());
        }
        return modelo;
    }

    public boolean insertDefault() {
        String nombreModelo = "Default";
        // Consulta SQL para verificar si existe un registro con el nombre proporcionado
        String verificarExistenciaSQL = "SELECT COUNT(*) FROM [dbo].MODELO WHERE ID_MODELO = 1";
        // Consulta SQL para insertar un nuevo registro
        String insertarSQL = "INSERT INTO [dbo].MODELO (NOMBRE) VALUES (?)";

        try (Connection conn = new Conexion().getConn()) {
            // Verificar si el registro ya existe
            try (PreparedStatement verificarStmt = conn.prepareStatement(verificarExistenciaSQL)) {

                try (ResultSet rs = verificarStmt.executeQuery()) {
                    rs.next();
                    int count = rs.getInt(1);

                    if (count == 0) {  // Solo realiza el INSERT si no existe el registro
                        try (PreparedStatement pstmt = conn.prepareStatement(insertarSQL)) {
                            pstmt.setString(1, nombreModelo);
                            int rowsAffected = pstmt.executeUpdate();
                            return rowsAffected > 0;  // Retorna true si se insertó el registro
                        }
                    } else {
                        System.out.println("El registro con nombre '" + nombreModelo + "' ya existe. No se realiza el insert.");
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

    public boolean insertarModelo(String nombreModelo) {
        // Establecemos la conexión (suponiendo que tienes un método getConn())
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para insertar el nuevo registro
            String sql = "INSERT INTO [dbo].MODELO (NOMBRE) VALUES (?)";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer el valor del parámetro
                pstmt.setString(1, nombreModelo);

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

    public ArrayList<Modelo> listar() {
        ArrayList lista = new <Modelo>ArrayList();
        String query = "SELECT ID_MODELO, NOMBRE FROM [dbo].MODELO";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Recorremos todos los resultados y los agregamos a la lista
            while (rs.next()) {
                int id = rs.getInt("ID_MODELO");
                String nombre = rs.getString("NOMBRE");
                Modelo modelo = new Modelo(id, nombre);
                lista.add(modelo);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al listar modelos: " + e.getMessage());
        }

        return lista;
    }

    public Modelo getModeloById(int idModeloBuscado) {
        Modelo modelo = null;
        String query = "SELECT NOMBRE FROM [dbo].MODELO WHERE ID_MODELO = ?";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos el ID del registro que queremos obtener con el parámetro
            stmt.setInt(1, idModeloBuscado);

            ResultSet rs = stmt.executeQuery();

            // Avanzamos al primer registro (si existe) antes de obtener el valor
            if (rs.next()) {
                modelo = new Modelo(idModeloBuscado, rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el modelo con ID " + idModeloBuscado + ": " + e.getMessage());
        }
        return modelo;
    }

}
