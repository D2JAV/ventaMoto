/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ApiUsuario {
    // Listar todos los usuarios que no han sido eliminados

    public ArrayList<Usuario> listar() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        String query = "SELECT * FROM USUARIO WHERE ELIMINADO = 0";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("ID_USUARIO"),
                        rs.getInt("ID_CARGO"),
                        rs.getString("USUARIO"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONO"),
                        rs.getString("DIRECCION"),
                        rs.getString("CONTRASENIA")
                );
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return listaUsuarios;
    }

    // Insertar un nuevo usuario
    public int insertar(Usuario usuario) {
        int idGenerado = -1;
        String query = "INSERT INTO USUARIO (ID_CARGO,USUARIO, NOMBRE, APELLIDO, EMAIL, TELEFONO, DIRECCION, CONTRASENIA, ELIMINADO) "
                + "VALUES (?, ?, ?,?, ?, ?, ?, ?, 0)";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, usuario.getIdCargo());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getTelefono());
            stmt.setString(7, usuario.getDireccion());
            stmt.setString(8, usuario.getConstrasenia());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }
        return idGenerado;
    }

    // Actualizar un usuario
    public boolean actualizar(Usuario usuario) {
        String query = "UPDATE USUARIO SET ID_CARGO = ?,USUARIO = ?, NOMBRE = ?, APELLIDO = ?, EMAIL = ?, "
                + "TELEFONO = ?, DIRECCION = ?, CONTRASENIA = ? WHERE ID_USUARIO = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, usuario.getIdCargo());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getTelefono());
            stmt.setString(7, usuario.getDireccion());
            stmt.setString(8, usuario.getConstrasenia());
            stmt.setInt(9, usuario.getIdUsuario());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar lógicamente un usuario
    public boolean eliminar(int idUsuario) {
        String query = "UPDATE USUARIO SET ELIMINADO = 1 WHERE ID_USUARIO = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para modificar la contraseña de un usuario
    public boolean modificarContrasenia(int idUsuario, String nuevaContrasenia) {
        String query = "UPDATE USUARIO SET CONTRASENIA = ? WHERE ID_USUARIO = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nuevaContrasenia);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar contraseña: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener usuarios con información de cargo
    public ArrayList<Usuario> listarConCargo() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        String query = "SELECT U.ID_USUARIO, U.USUARIO ,U.NOMBRE, U.APELLIDO, U.EMAIL,U.TELEFONO,U.DIRECCION, C.DESCRIPCION AS CARGO "
                + "FROM USUARIO U "
                + "INNER JOIN CARGO C ON U.ID_CARGO = C.ID_CARGO "
                + "WHERE U.ELIMINADO = 0";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("ID_USUARIO"),
                        -1,
                        rs.getString("USUARIO"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONO"),
                        rs.getString("DIRECCION"),
                        ""
                );

                usuario.setFormatCargo(rs.getString("CARGO"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios con cargo: " + e.getMessage());
        }
        return listaUsuarios;
    }

    public boolean verificarUsuario(String usuario, String contrasenia) {
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE USUARIO = ? AND CONTRASENIA = ? AND ELIMINADO = 0";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasenia);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Si COUNT(*) > 0, el usuario existe
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return false; // Si ocurre algún error o no se encuentra el usuario
    }

    public boolean esUsuarioUnico(String usuario) {
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE USUARIO = ?";

        try (Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // Si COUNT(*) es 0, el usuario es único
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return false; // Si ocurre algún error, asumimos que no es único
    }

  
}
