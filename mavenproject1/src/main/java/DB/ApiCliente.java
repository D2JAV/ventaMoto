/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DB_Clientes {

    public ArrayList<Clientes> byName(String nombreBusqueda) {
        ArrayList<Clientes> listaClientes = new ArrayList<>();
        String query = "SELECT ID_CLIENTE, NOMBRE, APELLIDO, EMAIL, TELEFONO, DIRECCION "
                + "FROM CLIENTE "
                + "WHERE ELIMINADO = 0 AND LOWER(NOMBRE) LIKE LOWER(?)";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreBusqueda + "%");  // Busca nombres que empiecen con el texto buscado

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idCliente = rs.getInt("ID_CLIENTE");
                    String nombre = rs.getString("NOMBRE");
                    String apellido = rs.getString("APELLIDO");
                    String email = rs.getString("EMAIL");
                    String telefono = rs.getString("TELEFONO");
                    String direccion = rs.getString("DIRECCION");

                    Clientes cliente = new Clientes(idCliente, nombre, apellido, email, telefono, direccion);
                    listaClientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes por nombre: " + e.getMessage());
        }

        return listaClientes;
    }

    public ArrayList<Clientes> listar() {
        ArrayList<Clientes> listaClientes = new ArrayList<>();
        String query = "SELECT ID_CLIENTE, NOMBRE, APELLIDO, EMAIL, TELEFONO, DIRECCION FROM [dbo].CLIENTE WHERE ELIMINADO = 0";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int idCliente = rs.getInt("ID_CLIENTE");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                String email = rs.getString("EMAIL");
                String telefono = rs.getString("TELEFONO");
                String direccion = rs.getString("DIRECCION");

                Clientes cliente = new Clientes(idCliente, nombre, apellido, email, telefono, direccion);
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return listaClientes;
    }

    public void insertar(Clientes cliente) {
        String sql = "INSERT INTO [dbo].CLIENTE (NOMBRE, APELLIDO, EMAIL, TELEFONO, DIRECCION, ELIMINADO) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getDireccion());
            pstmt.setInt(6, 0); // Por defecto, ELIMINADO es 0 (no eliminado)

            pstmt.executeUpdate();
            System.out.println("Cliente insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el cliente: " + e.getMessage());
        }
    }

    public void eliminar(int idCliente) {
        String sql = "UPDATE [dbo].CLIENTE SET ELIMINADO = 1 WHERE ID_CLIENTE = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente con ID " + idCliente + " marcado como eliminado.");
            } else {
                System.out.println("No se encontró el cliente con ID " + idCliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
    }

}
