/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Proveedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DB_Proveedores {

    public ArrayList<Proveedores> listar() {
        ArrayList<Proveedores> listaProveedores = new ArrayList<>();
        String query = "SELECT ID_PROVEEDOR, NOMBRE, CONTACTO, DIRECCION FROM [dbo].PROVEEDOR WHERE ELIMINADO = 0";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            // Recorremos todos los resultados y los agregamos a la lista
            while (rs.next()) {
                int idProveedor = rs.getInt("ID_PROVEEDOR");
                String nombre = rs.getString("NOMBRE");
                String contacto = rs.getString("CONTACTO");
                String direccion = rs.getString("DIRECCION");

                Proveedores proveedor = new Proveedores(idProveedor, nombre, contacto, direccion);
                listaProveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }

        return listaProveedores;
    }

    public int insertar(Proveedores proveedor) {
        int idGenerado = -1; // Variable para almacenar el ID generado

        // Establecemos la conexión
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para insertar el nuevo registro en la tabla PROVEEDOR
            String sql = "INSERT INTO [dbo].PROVEEDOR (NOMBRE, CONTACTO, DIRECCION, ELIMINADO) VALUES (?, ?, ?, ?)";

            // Crear el PreparedStatement con la opción para devolver las claves generadas
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                // Establecer los valores de los parámetros
                pstmt.setString(1, proveedor.getNombre());
                pstmt.setString(2, proveedor.getContacto());
                pstmt.setString(3, proveedor.getDireccion());

                int eliminado = 0; // Valor predeterminado de ELIMINADO es 0 (no eliminado)
                pstmt.setInt(4, eliminado);

                // Ejecutar la consulta de inserción
                int filasAfectadas = pstmt.executeUpdate();

                // Verificar si se generaron claves
                if (filasAfectadas > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            idGenerado = generatedKeys.getInt(1); // Obtener el ID generado
                        }
                    }
                }
                System.out.println("Proveedor insertado correctamente con ID: " + idGenerado);
            } catch (SQLException e) {
                System.out.println("Error al insertar el proveedor: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión: " + e.getMessage());
        }

        return idGenerado;
    }

    public void eliminar(int idProveedor) {
        // Establecemos la conexión
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para actualizar el campo ELIMINADO a 1
            String sql = "UPDATE [dbo].PROVEEDOR SET ELIMINADO = 1 WHERE ID_PROVEEDOR = ?";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer el valor del parámetro (idProveedor)
                pstmt.setInt(1, idProveedor);

                // Ejecutar la consulta de actualización
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Proveedor con ID " + idProveedor + " marcado como eliminado.");
                } else {
                    System.out.println("No se encontró el proveedor con ID " + idProveedor);
                }
            } catch (SQLException e) {
                System.out.println("Error al eliminar el proveedor: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión: " + e.getMessage());
        }
    }

    public ArrayList<Proveedores> byName(String nombreBusqueda) {
        ArrayList<Proveedores> listaProveedores = new ArrayList<>();
        String query = "SELECT ID_PROVEEDOR, NOMBRE, CONTACTO, DIRECCION "
                + "FROM [dbo].PROVEEDOR "
                + "WHERE ELIMINADO = 0 AND LOWER(NOMBRE) LIKE LOWER(?)";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreBusqueda + "%");  // Busca nombres que empiecen con el texto buscado

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idProveedor = rs.getInt("ID_PROVEEDOR");
                    String nombre = rs.getString("NOMBRE");
                    String contacto = rs.getString("CONTACTO");
                    String direccion = rs.getString("DIRECCION");

                    Proveedores proveedor = new Proveedores(idProveedor, nombre, contacto, direccion);
                    listaProveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores por nombre: " + e.getMessage());
        }

        return listaProveedores;
    }

    public Proveedores byId(int id) {
        String query = "SELECT ID_PROVEEDOR, NOMBRE, CONTACTO, DIRECCION  "
                + "FROM [dbo].PROVEEDOR "
                + "WHERE ID_PROVEEDOR=?;";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idProveedor = rs.getInt("ID_PROVEEDOR");
                    String nombre = rs.getString("NOMBRE");
                    String contacto = rs.getString("CONTACTO");
                    String direccion = rs.getString("DIRECCION");

                    Proveedores proveedor = new Proveedores(idProveedor, nombre, contacto, direccion);
                    return proveedor;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores por nombre: " + e.getMessage());
        }

        return null;
    }

}
