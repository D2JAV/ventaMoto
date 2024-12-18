/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.clases;

/**
 *
 * @author UCLABRED109
 */
public class Clientes {

    private int idCliente;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    
    private String formatIdCliente;

    public Clientes() {
    }

    public Clientes(int idCliente, String nombre, String apellido, String email, String telefono, String direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        
        this.formatIdCliente =  String.format("CL-%03d", idCliente); 
    }

    public String getFormatIdCliente() {
        return formatIdCliente;
    }

    public void setFormatIdCliente(String formatIdCliente) {
        this.formatIdCliente = formatIdCliente;
    }
    
    

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
