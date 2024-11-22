/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

/**
 *
 * @author ASUS
 */
import app.clases.DetalleCompras;
import java.sql.*;
import java.util.ArrayList;

public class ApiCompraDetalle {

    // Método para insertar un nuevo detalle de compra
    public void insertar(DetalleCompras detalle) {
        String sql = "INSERT INTO DETALLE_COMPRA (ID_COMPRA, ID_PRODUCTO, CANTIDAD, PRECIO) VALUES (?, ?, ?, ?)";
        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detalle.getIdCompra());
            pstmt.setInt(2, detalle.getIdProducto());
            pstmt.setInt(3, detalle.getCantidad());
            pstmt.setDouble(4, detalle.getPrecioCompra());
            pstmt.executeUpdate();

            System.out.println("Detalle insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar detalle: " + e.getMessage());
        }
    }

    public ArrayList<DetalleCompras> listarDetallesPorCompra(int idCompra) {
        ArrayList<DetalleCompras> detalles = new ArrayList<>();
        String sql = """
        SELECT 
            dc.ID_COMPRA, 
            dc.ID_PRODUCTO, 
            dc.CANTIDAD, 
            dc.PRECIO, 
            p.DESCRIPCION
        FROM DETALLE_COMPRA dc
        JOIN PRODUCTO p ON dc.ID_PRODUCTO = p.ID_PRODUCTO
        WHERE dc.ID_COMPRA = ?
    """;

        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCompra);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idProducto = rs.getInt("ID_PRODUCTO");
                    int cantidad = rs.getInt("CANTIDAD");
                    double precio = rs.getDouble("PRECIO");
                    String descripcion = rs.getString("DESCRIPCION");
 
                    // Asumiendo que DetalleCompras tiene un constructor que acepta descripción
                    DetalleCompras detalle = new DetalleCompras(idCompra, idProducto, cantidad, precio);
                    detalle.setProductoDescripcion(descripcion);
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.getMessage());
        }

        return detalles;
    }

    // Método para actualizar la cantidad y precio de un detalle
    public void actualizarDetalle(int idCompra, int idProducto, int nuevaCantidad, double nuevoPrecio) {
        String sql = "UPDATE DETALLE_COMPRA SET CANTIDAD = ?, PRECIO = ? WHERE ID_COMPRA = ? AND ID_PRODUCTO = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nuevaCantidad);
            pstmt.setDouble(2, nuevoPrecio);
            pstmt.setInt(3, idCompra);
            pstmt.setInt(4, idProducto);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Detalle actualizado correctamente.");
            } else {
                System.out.println("No se encontró el detalle a actualizar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle: " + e.getMessage());
        }
    }

    // Método para eliminar un detalle (eliminar lógicamente o físicamente según requerimientos)
    public void eliminarDetalle(int idCompra, int idProducto) {
        String sql = "DELETE FROM DETALLE_COMPRA WHERE ID_COMPRA = ? AND ID_PRODUCTO = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCompra);
            pstmt.setInt(2, idProducto);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Detalle eliminado correctamente.");
            } else {
                System.out.println("No se encontró el detalle a eliminar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle: " + e.getMessage());
        }
    }
}
