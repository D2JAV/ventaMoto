/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.ProveedorTipoProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ApiProveedorTipoProducto {

    public void insertar(ProveedorTipoProducto tipo) {
        // Establecemos la conexión
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para insertar el nuevo registro en la tabla PROV_TIPO_PRODUCTO
            String sql = "INSERT INTO [dbo].PROV_TIPO_PRODUCTO (ID_PROVEEDOR, DESCRIPCION) VALUES (?, ?)";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer los valores de los parámetros
                pstmt.setInt(1, tipo.getIdProveedor());
                pstmt.setString(2, tipo.getDescripcion());

                // Ejecutar la consulta de inserción
                pstmt.executeUpdate();
                System.out.println("Tipo de producto insertado correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al insertar el tipo de producto: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión: " + e.getMessage());
        }
    }

    public ArrayList<ProveedorTipoProducto> listar(int idProveedorFiltro) {
        ArrayList<ProveedorTipoProducto> lista = new ArrayList<>();
        String query = "SELECT ID_PROVEEDOR, DESCRIPCION FROM [dbo].PROV_TIPO_PRODUCTO WHERE ID_PROVEEDOR = ?";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {
            // Establecer el parámetro en la consulta
            stmt.setInt(1, idProveedorFiltro);

            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idProveedor = rs.getInt("ID_PROVEEDOR");
                    String descripcion = rs.getString("DESCRIPCION");

                    ProveedorTipoProducto proveedorTipoProducto = new ProveedorTipoProducto(idProveedor, descripcion);
                    lista.add(proveedorTipoProducto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores tipo producto: " + e.getMessage());
        }
        return lista;
    }

}
