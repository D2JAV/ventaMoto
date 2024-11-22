/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

/**
 *
 * @author ASUS
 */
public class ProveedorTipoProducto {

    private int idProveedor;
    private String descripcion;

    public ProveedorTipoProducto() {
    }

    public ProveedorTipoProducto(int idProveedor, String descripcion) {
        this.idProveedor = idProveedor;
        this.descripcion = descripcion;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
