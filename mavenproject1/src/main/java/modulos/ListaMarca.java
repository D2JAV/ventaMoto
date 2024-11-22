/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulos;

import app.clases.Marca;

/**
 *
 * @author ASUS
 */
public class ListaMarca {
    private class Nodo {
        Marca marca;
        Nodo siguiente;

        public Nodo(Marca marca) {
            this.marca = marca;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;

    // Constructor de la lista, inicialmente vacía
    public ListaMarca() {
        this.cabeza = null;
    }

    // Método para agregar un nodo con una Marca al final de la lista
    public void agregar(Marca marca) {
        Nodo nuevoNodo = new Nodo(marca);

        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    // Método para imprimir la lista de marcas
    public void imprimirLista() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.println(actual.marca);
            actual = actual.siguiente;
        }
    }
}
