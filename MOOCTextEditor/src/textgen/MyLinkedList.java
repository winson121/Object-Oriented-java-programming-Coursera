package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		
		this.head = new LLNode<E>();
		this.tail = new LLNode<E>();
		this.head.next = this.tail;
		this.tail.prev = this.head;
		this.size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		
		if (element == null) {
			throw new NullPointerException();
		}
		new LLNode<E>(element, tail.prev); 
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> current = this.getNode(index);
		return current.data;
	}
	/** get the node at position index in the list**/
	private LLNode<E> getNode(int index){
		LLNode<E> current = this.head;
		for (int i = 0; i < index+1; i++) {
			current = current.next;
		}
		return current;
	}
	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		
		if (element == null) {
			throw new NullPointerException();
		}
		// check for when adding to empty list with index
		if (this.isEmpty() && index != 0) {
			throw new IndexOutOfBoundsException();
		}
		
		// check for when adding element in non empty list
		if (!this.isEmpty() && (index>= size || index < 0)) {
			throw new IndexOutOfBoundsException();
		}
		
		if (this.isEmpty()) {
			new LLNode<E>(element, this.head, this.tail);
		} else {
			LLNode<E> nodeAtInd = this.getNode(index);
			new LLNode<E>(element, nodeAtInd.prev, nodeAtInd);
		} 
		size++;
	}
	
	/** check is the list is empty**/
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/** Return the size of the list */
	public int size() 
	{
		
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> current = this.getNode(index);
		current.prev.next = current.next;
		current.next.prev = current.prev;
		size--;
		return current.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) {throw new NullPointerException();}
		if (index >= size || index < 0) {throw new IndexOutOfBoundsException();}
		LLNode<E> targetNode = this.getNode(index);
		E oldVal = targetNode.data;
		targetNode.data = element;
		return oldVal;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// E.g. you might want to add another constructor
	
	public LLNode() {
		
	}
	
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> prev) {
		this(e);
		this.next = prev.next;
		this.prev = prev;
		prev.next = this;
		this.next.prev = this;
	}
	
	public LLNode(E e, LLNode<E> prev, LLNode<E> next) {
		this(e);
		this.next = next;
		this.prev = prev;
		prev.next = this;
		next.prev = this;
	}
	
	public String toString() {
		return this.data.toString();
	}
}
