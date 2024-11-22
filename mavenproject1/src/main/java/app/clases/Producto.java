/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

/**
 *
 * @author CYBERTEL
 */
public   class Producto {
    private int idProducto; 
    private String descripcion;
    private String categoria;//1.moto, 2.accesorio
    private double precio;
    private int stock;
    private boolean eliminado;
    
    private String formatIdProducto,formatDescricpion,formatPrecio;

    public Producto() {
    }

    public Producto(int idProducto, String descripcion, String categoria, double precio,int stock, boolean eliminado) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock= stock;
        this.eliminado = eliminado;
        
       
        this.formatPrecio = String.format("%.2f", precio);
        this.formatIdProducto = String.format("P-%04d", idProducto); 
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    

    public String getFormatPrecio() {
        return formatPrecio;
    }

    public void setFormatPrecio(String formatPrecio) {
        this.formatPrecio = formatPrecio;
    }
    
    

    public String getFormatIdProducto() {
        return formatIdProducto;
    }

    public void setFormatIdProducto(String formatIdProducto) {
        this.formatIdProducto = formatIdProducto;
    }

    public String getFormatDescricpion() {
        return formatDescricpion;
    }

    public void setFormatDescricpion(String formatDescricpion) {
        this.formatDescricpion = formatDescricpion;
    }

    
    
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
    

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

   

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
    
    
}
