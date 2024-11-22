/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

import DB.DB_Proveedores;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author UCLABRED109
 */
public class Compras {

    private int idCompra;
    private int idProveedor;
    private Date fechaOrden;
    private String estado;//PENDIENTE-RECIBIDA(POR PAGAR)-RECIBIDA(SALDADO)-COMPLETADO-CANCELADO
    private double monto;

    private String formatIdCompra, formatMonto, formatProveedor, formatFechaOrden;

    public Compras() {
    }

    public Compras(int idCompra, int idProveedor, Date fechaOrden, String estado, double monto) {
        this.idCompra = idCompra;
        this.idProveedor = idProveedor;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.monto = monto;

        this.formatMonto = String.format("%.2f", monto);
        this.formatIdCompra = String.format("C-%03d", idCompra);
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
        formatFechaOrden = formato.format(fechaOrden).toUpperCase();
        Proveedores proveedor = new DB_Proveedores().byId(idProveedor);
        formatProveedor = proveedor.getFormatIdProveedor() + ": " +proveedor.getNombre();
    }

    public String getFormatProveedor() {
        return formatProveedor;
    }

    public void setFormatProveedor(String formatProveedor) {
        this.formatProveedor = formatProveedor;
    }

    public String getFormatFechaOrden() {
        return formatFechaOrden;
    }

    public void setFormatFechaOrden(String formatFechaOrden) {
        this.formatFechaOrden = formatFechaOrden;
    }

    public Compras(int idCompra, int idProveedor, Date fechaOrden, String estado, double monto, String formatIdCompra, String formatMonto, String formatProveedor, String formatFechaOrden) {
        this.idCompra = idCompra;
        this.idProveedor = idProveedor;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.monto = monto;
        this.formatIdCompra = formatIdCompra;
        this.formatMonto = formatMonto;
        this.formatProveedor = formatProveedor;
        this.formatFechaOrden = formatFechaOrden;
    }

    public String getFormatIdCompra() {
        return formatIdCompra;
    }

    public void setFormatIdCompra(String formatIdCompra) {
        this.formatIdCompra = formatIdCompra;
    }

    public String getFormatMonto() {
        return formatMonto;
    }

    public void setFormatMonto(String formatMonto) {
        this.formatMonto = formatMonto;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {

        this.formatMonto = String.format("C-%03d", idCompra);
        this.idCompra = idCompra;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

}
