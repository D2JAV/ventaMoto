 
package modulos;

import app.clases.Clientes;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ListaClientes {

    //lista doble circular 
    class Nodo {

        Clientes dato;
        Nodo siguiente;
        Nodo anterior;

        public Nodo(Clientes clientes) {
            this.dato = clientes;
            this.siguiente = null;
            this.anterior = null;
        }
    }

    private Nodo cabeza;

    public ListaClientes() {
        cabeza = null;
    }

    // Método para insertar un nuevo nodo
    public void insertar(Clientes dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.siguiente = cabeza;
            cabeza.anterior = cabeza;
        } else {
            Nodo cola = cabeza.anterior;
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
        }
    }

    // Método para eliminar un nodo
    public boolean eliminar(Clientes dato) {
        if (cabeza == null) {
            return false; // Lista vacía
        }

        Nodo actual = cabeza;
        do {
            if (actual.dato == dato) {
                if (actual == cabeza && actual.siguiente == cabeza) {
                    cabeza = null; // Solo había un nodo
                } else {
                    actual.anterior.siguiente = actual.siguiente;
                    actual.siguiente.anterior = actual.anterior;
                    if (actual == cabeza) {
                        cabeza = actual.siguiente; // Cambiar cabeza
                    }
                }
                return true;
            }
            actual = actual.siguiente;
        } while (actual != cabeza);

        return false; // No se encontró el nodo
    }
 // Método para guardar la lista en archivo de texto
    /*
      public void listar() {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("Clientes.csv"))) {
            Nodo actual = cabeza;
            if (cabeza != null) {
                do {
                    writer.write(actual.dato.toCsv() + "\n");
                    actual = actual.siguiente;
                } while (actual != cabeza);
            }
            System.out.println("Lista guardada ");
        } catch (IOException e) {
            System.err.println("Error al guardar la lista en el archivo: " + e.getMessage());
        }
    }

    */
  
    // Método para modificar un nodo
    public boolean modificar(Clientes datoAntiguo, Clientes datoNuevo) {
        Nodo actual = cabeza;
        if (cabeza == null) {
            return false; // Lista vacía
        }

        do {
            if (actual.dato == datoAntiguo) {
                actual.dato = datoNuevo;
                return true; // Modificación exitosa
            }
            actual = actual.siguiente;
        } while (actual != cabeza);

        return false; // No se encontró el nodo a modificar
    }

    // Método para buscar un nodo
    public boolean buscar(Clientes dato) {
        Nodo actual = cabeza;
        if (cabeza == null) {
            return false; // Lista vacía
        }

        do {
            if (actual.dato == dato) {
                return true; // Nodo encontrado
            }
            actual = actual.siguiente;
        } while (actual != cabeza);

        return false; // No se encontró el nodo
    }

    // Método para imprimir la lista
    public void imprimir() {
        Nodo actual = cabeza;
        if (cabeza != null) {
            do {
                System.out.print(actual.dato.toString() + " ");
                actual = actual.siguiente;
            } while (actual != cabeza);
            System.out.println();
        }
    }

}
