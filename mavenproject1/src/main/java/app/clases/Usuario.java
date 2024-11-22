/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

/**
 *
 * @author CYBERTEL
 */
public class Usuario {

    private int idUsuario;
    private int idCargo;
    private String usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String constrasenia;

    private String formatIdUsuario, formatCargo;

    public Usuario() {
    }

    public Usuario(int idUsuario, int idCargo, String usuario, String nombre, String apellido, String email, String telefono, String direccion, String constrasenia) {
        this.idUsuario = idUsuario;
        this.idCargo = idCargo;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.constrasenia = constrasenia;
        
        this.formatIdUsuario = String.format("US-%03d", idUsuario);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    public String getFormatIdUsuario() {
        return formatIdUsuario;
    }

    public void setFormatIdUsuario(String formatIdUsuario) {
        this.formatIdUsuario = formatIdUsuario;
    }

    public String getFormatCargo() {
        return formatCargo;
    }

    public void setFormatCargo(String formatCargo) {
        this.formatCargo = formatCargo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getConstrasenia() {
        return constrasenia;
    }

    public void setConstrasenia(String constrasenia) {
        this.constrasenia = constrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
