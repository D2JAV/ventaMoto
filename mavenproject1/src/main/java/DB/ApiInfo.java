/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import DB.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DB_Info {

    private Conexion conexion;

    public DB_Info() {
        this.conexion = new Conexion();
    }

    public String getNombreEmpresa() {
        return estructura("NOMBRE_EMPRESA");
    }

    public String getDireccionEmpresa() {
        return estructura("DIRECCION_EMPRESA");
    }

    public String getRucEmpresa() {
        return estructura("RUC_EMPRESA");
    }

    private String estructura(String campo) {
        String found = "";
        String query = "SELECT " + campo + " FROM INFO WHERE ID_INFO = ?";
        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos el ID del registro que queremos obtener (en este caso, ID 0)
            stmt.setInt(1, 1);
            ResultSet rs = stmt.executeQuery();

            // Avanzamos al primer registro (si existe) antes de obtener el valor
            if (rs.next()) {
                return rs.getString(campo);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener " + campo + e.getMessage());
        }
        return found;
    }

    public boolean insertDefault() {
        // Consulta SQL para verificar si existe el registro con ID = 1
        String verificarExistenciaSQL = "SELECT COUNT(*) FROM [dbo].INFO WHERE ID_INFO = 1";
        // Consulta SQL para insertar el nuevo registro si no existe
        String insertarSQL = "INSERT INTO [dbo].INFO (NOMBRE_EMPRESA, DIRECCION_EMPRESA, RUC_EMPRESA) VALUES (?,?,?)";

        try (Connection conn = new Conexion().getConn()) {
            // Verificar si el registro con ID = 1 ya existe
            try (PreparedStatement verificarStmt = conn.prepareStatement(verificarExistenciaSQL); ResultSet rs = verificarStmt.executeQuery()) {

                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {  // Solo realiza el INSERT si no existe el registro
                    try (PreparedStatement pstmt = conn.prepareStatement(insertarSQL)) {
                        // Establecer valores de los parámetros
                        pstmt.setString(1, "DEFAULT");
                        pstmt.setString(2, "DEFAULT");
                        pstmt.setString(3, "DEFAULT");

                        // Ejecutar la consulta de inserción
                        int rowsAffected = pstmt.executeUpdate();
                        return rowsAffected > 0; // Retorna true si se insertó el registro
                    }
                } else {
                    System.out.println("El registro con ID = 1 ya existe. No se realiza el insert.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si ocurre un error, devolver false
        return false;
    }

    public void modificarNombreEmpresa(String nuevoNombre) {
        try (Connection conn = new Conexion().getConn()) {
            String sql = "UPDATE INFO SET NOMBRE_EMPRESA = ? WHERE ID_INFO = 1";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoNombre);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Nombre de la empresa actualizado a: " + nuevoNombre);
                } else {
                    System.out.println("No se encontró el registro con ID_INFO = 1");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarDireccionEmpresa(String nuevaDireccion) {
        try (Connection conn = new Conexion().getConn()) {
            String sql = "UPDATE INFO SET DIRECCION_EMPRESA = ? WHERE ID_INFO = 1";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevaDireccion);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Dirección de la empresa actualizada a: " + nuevaDireccion);
                } else {
                    System.out.println("No se encontró el registro con ID_INFO = 1");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarRucEmpresa(String nuevoRuc) {
        try (Connection conn = new Conexion().getConn()) {
            String sql = "UPDATE INFO SET RUC_EMPRESA = ? WHERE ID_INFO = 1";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoRuc);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("RUC de la empresa actualizado a: " + nuevoRuc);
                } else {
                    System.out.println("No se encontró el registro con ID_INFO = 1");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
