/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import app.clases.Marca;
import app.clases.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DB_Producto {

    public int insertarProducto(Producto producto) {
        // Establecemos la conexión (suponiendo que tienes un método getConn())
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para insertar el nuevo registro en la tabla PRODUCTO
            String sql = "INSERT INTO [dbo].PRODUCTO (DESCRIPCION, CATEGORIA, PRECIO,STOCK,ELIMINADO) VALUES (?,?, ?, ?, ?)";

            // Crear el PreparedStatement, solicitando que devuelva las claves generadas
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                // Establecer los valores de los parámetros
                pstmt.setString(1, producto.getDescripcion());
                pstmt.setString(2, producto.getCategoria());
                pstmt.setDouble(3, producto.getPrecio());
                pstmt.setDouble(4, producto.getStock());

                int bit = 0;
                if (producto.isEliminado()) {
                    //esta eliminado
                    bit = 1;
                } else {
                    //no esta eliminado
                    bit = 0;
                }

                pstmt.setInt(5, bit); // Convertir booleano a 1 o 0

                // Ejecutar la consulta de inserción
                int rowsAffected = pstmt.executeUpdate();

                // Si se insertó una fila, obtener el ID generado
                if (rowsAffected > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            return rs.getInt(1); // Retorna el ID generado
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si ocurre un error, devolver -1
        return -1;
    }

    public ArrayList<Producto> listar() {
        ArrayList lista = new <Marca> ArrayList();
        String query = "SELECT ID_PRODUCTO,DESCRIPCION, CATEGORIA, PRECIO,STOCK FROM [dbo].PRODUCTO WHERE ELIMINADO=0";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Recorremos todos los resultados y los agregamos a la lista
            while (rs.next()) {
                int id = rs.getInt("ID_PRODUCTO");
                String descripcion = rs.getString("DESCRIPCION");
                String categoria = rs.getString("CATEGORIA");
                double precio = rs.getDouble("PRECIO");
                int stock = rs.getInt("STOCK");

                Producto producto = new Producto(id, descripcion, categoria, precio, stock, false);
                lista.add(producto);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al listar producto: " + e.getMessage());
        }

        return lista;
    }

    public void eliminar(int idProducto) {
        // Establecemos la conexión (suponiendo que tienes un método getConn())
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para actualizar el campo ELIMINADO a 1
            String sql = "UPDATE [dbo].PRODUCTO SET ELIMINADO = 1 WHERE ID_PRODUCTO = ?";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer el valor del parámetro (idProducto)
                pstmt.setInt(1, idProducto);

                // Ejecutar la consulta de actualización
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Producto con ID " + idProducto + " marcado como eliminado.");
                } else {
                    System.out.println("No se encontró el producto con ID " + idProducto);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarStock(int idProducto, int ajusteStock) {
        // Establecemos la conexión (suponiendo que tienes un método getConn())
        try (Connection conn = new Conexion().getConn()) {
            // Crear la consulta SQL para actualizar el stock
            String sql = "UPDATE [dbo].PRODUCTO SET STOCK = STOCK + ? WHERE ID_PRODUCTO = ?";

            // Crear el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer los valores de los parámetros
                pstmt.setInt(1, ajusteStock); // Ajuste de stock (positivo o negativo)
                pstmt.setInt(2, idProducto); // ID del producto

                // Ejecutar la consulta de actualización
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Stock del producto con ID " + idProducto + " actualizado.");
                } else {
                    System.out.println("No se encontró el producto con ID " + idProducto);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Producto> listar2() {
        ArrayList<Producto> lista = new ArrayList<>();
        String query = "SELECT P.ID_PRODUCTO, P.DESCRIPCION, P.CATEGORIA, P.PRECIO, P.STOCK, "
                + "M.ID_MOTO, MO.NOMBRE AS NOMBRE_MODELO, MA.NOMBRE AS NOMBRE_MARCA, "
                + "M.ANIO, M.COLOR, A.ID_ACCESORIO, A.TIPO "
                + "FROM [dbo].PRODUCTO P "
                + "LEFT JOIN [dbo].MOTO M ON P.ID_PRODUCTO = M.ID_PRODUCTO "
                + "LEFT JOIN [dbo].ACCESORIO A ON P.ID_PRODUCTO = A.ID_PRODUCTO "
                + "LEFT JOIN [dbo].MODELO MO ON M.ID_MODELO = MO.ID_MODELO "
                + "LEFT JOIN [dbo].MARCA MA ON M.ID_MARCA = MA.ID_MARCA "
                + "WHERE P.ELIMINADO = 0";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_PRODUCTO");
                String descripcion = rs.getString("DESCRIPCION");
                String categoria = rs.getString("CATEGORIA");
                double precio = rs.getDouble("PRECIO");
                int stock = rs.getInt("STOCK");

                Producto producto = new Producto(id, descripcion, categoria, precio, stock, false);

                String formatDescripcion = "";

                if (producto.getCategoria().equalsIgnoreCase("MOTO")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
                    String anio = dateFormat.format(rs.getDate("ANIO"));

                    formatDescripcion
                            += "Ma:" + rs.getString("NOMBRE_MARCA")
                            + " Mo:" + rs.getString("NOMBRE_MODELO")
                            + " A:" + anio
                            + " C:" + rs.getString("COLOR");
                } else {
                    formatDescripcion += "TIPO: " + rs.getString("TIPO");
                }

                producto.setFormatDescricpion(formatDescripcion);
                lista.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<Producto> buscar(String busqueda) {
        ArrayList<Producto> lista = new ArrayList<>();
        String query = "SELECT P.ID_PRODUCTO, P.DESCRIPCION, P.CATEGORIA, P.PRECIO, P.STOCK, "
                + "M.ID_MOTO, MO.NOMBRE AS NOMBRE_MODELO, MA.NOMBRE AS NOMBRE_MARCA, "
                + "M.ANIO, M.COLOR, A.ID_ACCESORIO, A.TIPO "
                + "FROM [dbo].PRODUCTO P "
                + "LEFT JOIN [dbo].MOTO M ON P.ID_PRODUCTO = M.ID_PRODUCTO "
                + "LEFT JOIN [dbo].ACCESORIO A ON P.ID_PRODUCTO = A.ID_PRODUCTO "
                + "LEFT JOIN [dbo].MODELO MO ON M.ID_MODELO = MO.ID_MODELO "
                + "LEFT JOIN [dbo].MARCA MA ON M.ID_MARCA = MA.ID_MARCA "
                + "WHERE P.ELIMINADO = 0 AND P.DESCRIPCION LIKE ?";

        try (
                Connection conn = new Conexion().getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Asignar el parámetro de búsqueda usando comodines para el "contains"
            stmt.setString(1, "%" + busqueda + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID_PRODUCTO");
                    String descripcion = rs.getString("DESCRIPCION");
                    String categoria = rs.getString("CATEGORIA");
                    double precio = rs.getDouble("PRECIO");
                    int stock = rs.getInt("STOCK");

                    Producto producto = new Producto(id, descripcion, categoria, precio, stock, false);
                    String formatDescripcion = "";

                    if (producto.getCategoria().equalsIgnoreCase("MOTO")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
                        String anio = dateFormat.format(rs.getDate("ANIO"));

                        formatDescripcion
                                += "Ma:" + rs.getString("NOMBRE_MARCA")
                                + " Mo:" + rs.getString("NOMBRE_MODELO")
                                + " A:" + anio
                                + " C:" + rs.getString("COLOR");
                    } else {
                        formatDescripcion += "TIPO: " + rs.getString("TIPO");
                    }

                    producto.setFormatDescricpion(formatDescripcion);
                    lista.add(producto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

}
