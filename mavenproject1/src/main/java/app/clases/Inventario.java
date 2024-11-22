/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

/**
 *
 * @author UCLABRED109
 */
public class Inventario {

    private int idInventario;
    private int idProducto;
    private int stock; 
    
    public Inventario() {
    }

    public Inventario(int idInventario, int idProducto, int stock) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.stock = stock;
    }

    
    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
 
    
 
}
