/**
 * @Author Guillermo Gerardo Andres Urbano.
 * Representa un Nodo en una lista enlazada.
 */
//package processManagement;

public class Node<T> {
    private Node<T> next;
    private T element;

    /**
     * Crea un nodo vacio
     */
    public Node() {
		this.next = null;
		this.element = null;
    }

    /**
     * Crea un nodo en el que se almacena el elemento
     * especificado.
     */
    public Node(T element) {
		this.next = null;
		this.element = element;
    }

    /**
     * Devuelve el nodo que sigue a este.
     */
    public Node<T> getNext() {
		return next;
    }

    /**
     * Establece el nodo que sigue a este.
     *
     */
    public void setNext(Node<T> node) {
		this.next = node;
    }

    /**
     * Devuelve el elemento almacenado en este nodo.
     */
    public T getElement() {
		return this.element;
    }
    
    /**
     * Asigna el elemento que hay que almacenar en este
     * nodo.
     */
    public void setElement(T element) {
		this.element = element;
    }
}

