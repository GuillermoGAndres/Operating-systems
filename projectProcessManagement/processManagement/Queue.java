/* Define la interfaz de una cola */
//package processManagement;

public interface Queue <T> {

	// Añade un elemento a la cola
	public void enqueue(T element);

	// Elimina y devuelve un elemento de la cola
	public T dequeue() throws EmptyCollectionException;

	// Devuelve sin eliminarlo el elemento situado a al cola
	public T first();

	// Devuelve si esta vacia
	public boolean isEmpty();

	// Devuelve el tamanño de la cola
	public int size();

	// Devuelve una representacion de la cola
	public String toString();
	
}	
