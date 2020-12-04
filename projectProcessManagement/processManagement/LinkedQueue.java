//package processManagement;

public class LinkedQueue<T> implements Queue <T> {
	private Node<T> front; // Apunta al primer elemento de la lista.
	private Node<T> rear; // Apunta al ultimo elemento de la lista.
	private int count;

	public LinkedQueue() {
		count = 0;
		front = null;
		rear = null;
	}
	
	// Añade un elemento a la cola
	public void enqueue(T element) {
		Node<T> node = new Node<T>(element);		
		if (isEmpty()) 
			front = node;
		else
			rear.setNext(node); //rear es la se va ir moviendo
		rear = node;
		count++;
		
	}

	// Elimina y devuelve un elemento de la cola
	public T dequeue() throws EmptyCollectionException{
		if( isEmpty()){
			throw new EmptyCollectionException("La cola se encuentra vacia, no puede sacar mas");
		}
		T result = front.getElement();
		front = front.getNext(); // Devueleve null si solo hay un elemento.
		count--;

		// Si lo hay un elmento al devolver, inicializamos la referencia
		if (isEmpty()) {
			rear = null;
		}
		return result;
	}

	// Devuelve sin eliminarlo el elemento situado a al cola
	public T first() {
		//@refernce  https://stackoverflow.com/questions/869033/how-do-i-copy-an-object-in-java
		// https://www.arquitecturajava.com/java-clone-un-concepto-importante/
		// https://www.geeksforgeeks.org/clone-method-in-java-2/
		return front.getElement();
	}

	// Devuelve si esta vacia
	public boolean isEmpty() {
		return count == 0;
	}

	// Devuelve el tamanño de la cola
	public int size() {
		return count;
	}

	// Devuelve una representacion de la cola
	public String toString() {
		int i = count;
		Node<T> iterador = front;
		String aux = "";
		while( i  > 0) {
			aux += "| " + iterador.getElement() + " | ";
			iterador = iterador.getNext();
			i--;
		}
		return aux;
	}

	
}
