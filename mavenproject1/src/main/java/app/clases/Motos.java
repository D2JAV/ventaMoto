/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

import java.sql.Date;

/**
 *
 * @author UCLABRED109
 */
public class Motos extends Producto{
    private int idMotos;
    private int idMarca;
    private int idModelo;
    private int idProducto;
    private Date anio;
    private String color; 
    public Motos() {
    }

    public Motos(int idMotos, int idMarca, int idModelo,int idProducto, Date anio, String color ) {
        this.idMotos = idMotos;
        this.idMarca = idMarca;
        this.idModelo = idModelo;
        this.idProducto = idProducto;
        this.anio = anio;
        this.color = color; 
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    
    

    public int getIdMotos() {
        return idMotos;
    }

    public void setIdMotos(int idMotos) {
        this.idMotos = idMotos;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public Date getAnio() {
        return anio;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
    }

  

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    } 
 
    
    
    
    
}
