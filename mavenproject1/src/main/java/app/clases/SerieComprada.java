/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

/**
 *
 * @author ASUS
 */
public class SerieComprada {
    
    private int idSerieComprada;
    private int idDetalleComprada;
    private String estado;
    private String serie;

    public SerieComprada() {
    }

    public SerieComprada(int idSerieComprada, int idDetalleComprada, String estado, String serie) {
        this.idSerieComprada = idSerieComprada;
        this.idDetalleComprada = idDetalleComprada;
        this.estado = estado;
        this.serie = serie;
    }

    public int getIdSerieComprada() {
        return idSerieComprada;
    }

    public void setIdSerieComprada(int idSerieComprada) {
        this.idSerieComprada = idSerieComprada;
    }

    public int getIdDetalleComprada() {
        return idDetalleComprada;
    }

    public void setIdDetalleComprada(int idDetalleComprada) {
        this.idDetalleComprada = idDetalleComprada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
    
     
}
