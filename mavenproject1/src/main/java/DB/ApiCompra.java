/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Compras;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ApiCompras {

    // Método para insertar una nueva compra
    public int insertarCompra(Compras compra) {
        try (Connection conn = new Conexion().getConn()) {
            String sql = "INSERT INTO [dbo].COMPRA (ID_PROVEEDOR, FECHA_ORDEN, ESTADO, MONTO, ELIMINADO) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, compra.getIdProveedor());
                pstmt.setDate(2, new java.sql.Date(compra.getFechaOrden().getTime()));
                pstmt.setString(3, compra.getEstado());
                pstmt.setDouble(4, compra.getMonto());
                pstmt.setBoolean(5, false); // Por defecto no eliminado

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Método para listar todas las compras no eliminadas
    public ArrayList<Compras> listar() {
        ArrayList<Compras> lista = new ArrayList<>();
        String query = "SELECT ID_COMPRA, ID_PROVEEDOR, FECHA_ORDEN, ESTADO, MONTO FROM [dbo].COMPRA WHERE ELIMINADO = 0";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idCompra = rs.getInt("ID_COMPRA");
                int idProveedor = rs.getInt("ID_PROVEEDOR");
                Date fechaOrden = rs.getDate("FECHA_ORDEN");
                String estado = rs.getString("ESTADO");
                float monto = rs.getFloat("MONTO");

                Compras compra = new Compras(idCompra, idProveedor, fechaOrden, estado, monto);
                lista.add(compra);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar compras: " + e.getMessage());
        }
        return lista;
    }

    // Método para eliminar (lógicamente) una compra por su ID
    public void eliminar(int idCompra) {
        try (Connection conn = new Conexion().getConn()) {
            String sql = "UPDATE [dbo].COMPRA SET ELIMINADO = 1 WHERE ID_COMPRA = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idCompra);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Compra con ID " + idCompra + " marcada como eliminada.");
                } else {
                    System.out.println("No se encontró la compra con ID " + idCompra);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar compras por un proveedor o estado
    public ArrayList<Compras> buscar(String busqueda) {
        ArrayList<Compras> lista = new ArrayList<>();
        String query = "SELECT ID_COMPRA, ID_PROVEEDOR, FECHA_ORDEN, ESTADO, MONTO "
                + "FROM [dbo].COMPRA WHERE ELIMINADO = 0 AND (CAST(ID_PROVEEDOR AS NVARCHAR) LIKE ? OR ESTADO LIKE ?)";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + busqueda + "%");
            stmt.setString(2, "%" + busqueda + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idCompra = rs.getInt("ID_COMPRA");
                    int idProveedor = rs.getInt("ID_PROVEEDOR");
                    Date fechaOrden = rs.getDate("FECHA_ORDEN");
                    String estado = rs.getString("ESTADO");
                    float monto = rs.getFloat("MONTO");

                    Compras compra = new Compras(idCompra, idProveedor, fechaOrden, estado, monto);
                    lista.add(compra);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar compras: " + e.getMessage());
        }
        return lista;
    }

    // Método para buscar compras por un proveedor o estado
    public Compras byId(int idBusqueda) {
        String query = "SELECT ID_COMPRA, ID_PROVEEDOR, FECHA_ORDEN, ESTADO, MONTO "
                + "FROM [dbo].COMPRA WHERE ELIMINADO = 0 AND ID_COMPRA=?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idBusqueda);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idCompra = rs.getInt("ID_COMPRA");
                    int idProveedor = rs.getInt("ID_PROVEEDOR");
                    Date fechaOrden = rs.getDate("FECHA_ORDEN");
                    String estado = rs.getString("ESTADO");
                    float monto = rs.getFloat("MONTO");

                    Compras compra = new Compras(idCompra, idProveedor, fechaOrden, estado, monto);
                    return compra;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar compras: " + e.getMessage());
        }
        return null;
    }
}
