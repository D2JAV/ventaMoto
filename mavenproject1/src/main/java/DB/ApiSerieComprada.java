/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.SerieComprada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ApiSerieComprada {

    public ArrayList<SerieComprada> listar() {
        ArrayList<SerieComprada> listaSeries = new ArrayList<>();
        String query = "SELECT ID_SERIE_COMPRADA, ID_DETALLE_COMPRA, ESTADO, SERIE FROM SERIE_COMPRADA";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int idSerieComprada = rs.getInt("ID_SERIE_COMPRADA");
                int idDetalleComprada = rs.getInt("ID_DETALLE_COMPRA");
                String estado = rs.getString("ESTADO");
                String serie = rs.getString("SERIE");

                SerieComprada serieComprada = new SerieComprada(idSerieComprada, idDetalleComprada, estado, serie);
                listaSeries.add(serieComprada);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar series compradas: " + e.getMessage());
        }

        return listaSeries;
    }

    public void insertar(SerieComprada serieComprada) {
        String sql = "INSERT INTO SERIE_COMPRADA (ID_DETALLE_COMPRA, ESTADO, SERIE) VALUES (?, ?, ?)";

        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, serieComprada.getIdDetalleComprada());
            pstmt.setString(2, serieComprada.getEstado());
            pstmt.setString(3, serieComprada.getSerie());

            pstmt.executeUpdate();
            System.out.println("Serie comprada insertada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar la serie comprada: " + e.getMessage());
        }
    }

    public void eliminar(int idSerieComprada) {
        String sql = "DELETE FROM SERIE_COMPRADA WHERE ID_SERIE_COMPRADA = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idSerieComprada);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Serie comprada con ID " + idSerieComprada + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró la serie comprada con ID " + idSerieComprada);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar la serie comprada: " + e.getMessage());
        }
    }
}
