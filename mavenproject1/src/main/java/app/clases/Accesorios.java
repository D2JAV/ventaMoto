/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

/**
 *
 * @author UCLABRED109
 */
public class Accesorios extends Producto{

    private int idAccesorio; 
    private int idProducto;
    private String tipo;
           

    public Accesorios() {
    }

    public Accesorios(int idAccesorio, int idProducto, String tipo) {
        this.idAccesorio = idAccesorio;
        this.idProducto = idProducto;
        this.tipo = tipo;
    }

     

    public int getIdAccesorio() {
        return idAccesorio;
    }

    public void setIdAccesorio(int idAccesorio) {
        this.idAccesorio = idAccesorio;
    }

 
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
 
    
    
}
