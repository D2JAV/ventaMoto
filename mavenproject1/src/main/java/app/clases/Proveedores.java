package app.clases;

/**
 *
 * @author UCLABRED109
 */
public class Proveedores {

    private int idProveedor;
    private String nombre;
    private String contacto;
    private String direccion; 
    private String formatIdProveedor;

    public Proveedores() {
    }

    public Proveedores(int idProveedor, String nombre, String contacto, String direccion) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.contacto = contacto;
        this.direccion = direccion; 
        
        this.formatIdProveedor  =  String.format("PROV-%03d", idProveedor);
    }
    
    

    public String getFormatIdProveedor() {
        return formatIdProveedor;
    }

    public void setFormatIdProveedor(String formatIdProveedor) {
        this.formatIdProveedor = formatIdProveedor;
    }

  
    
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
 
    
}
