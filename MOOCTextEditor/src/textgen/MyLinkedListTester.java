/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		// test remove first and last index
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		
		int b = list1.remove(1);
		assertEquals("Remove: check b is correct ", 42, b);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 1, list1.size());
		
		// test remove from emptyList
		try {
			emptyList.remove(0);
			fail("Check index out of bound");
		} catch (IndexOutOfBoundsException e) {
					
		}
		
		// test remove from negative index
		try {
			list1.remove(-1);
			fail("Check index out of bound");
		} catch (IndexOutOfBoundsException e) {
					
		}
		
		// test remove in the middle of list
		int c = longerList.remove(4);
		assertEquals("Remove: check c is correct ", 4, c);
		assertEquals("Remove: check element 3 is correct ", (Integer)3, longerList.get(3));
		assertEquals("Remove: check element 4 is correct ", (Integer)5, longerList.get(4));
		assertEquals("Remove: check size is correct ", 9, longerList.size());
		
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		
		// test adding null element
		try {
			emptyList.add(null);
			fail("check NullPointerException");
		} catch (NullPointerException e) {
			
		}

		// test adding in empty list
		emptyList.add(30);
		assertEquals("List last element 30 is correct", (Integer)30, emptyList.tail.prev.data);
		assertEquals("List size is 1 is correct", 1, emptyList.size);
		
		// test add in list with single element
		
		emptyList.add(33);
		assertEquals("List last element 33 is correct", (Integer)33, emptyList.tail.prev.data);
		assertEquals("List size is 1 is correct", 2, emptyList.size);
				
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		// test number of element in long list
		int counter = 0;
		LLNode<Integer> current = longerList.head.next;
		while (current.next != null) {
			current = current.next;
			counter++;
		}
		assertEquals("Check the size equals to counter is correct", longerList.size, counter);
		
		// test on empty list
		counter = 0;
		current = emptyList.head.next;
		while (current.next != null) {
			current = current.next;
			counter++;
		}
		assertEquals("Check the counter equals to 0 is correct", counter, 0);
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		// test adding at index out of bound in emptyList
		try {
			emptyList.add(1, 30);
			fail("Check out of bound when adding to emptyList and index != 0");
		} catch (IndexOutOfBoundsException e) {
			
		}
		
		try {
			emptyList.add(-1, 30);
			fail("Check out of bound when adding to emptyList and index != 0");
		} catch (IndexOutOfBoundsException e) {
			
		}
		// test adding at 0 th index in  empty list
		emptyList.add(0, 30);
		assertEquals("first element 30 is correct", (Integer)30, emptyList.get(0));
		assertEquals("List size is 1 is correct", 1, emptyList.size);
		
		// test adding in non empty list
		// adding at first and last index of the list
		int list1Ind0 = list1.get(0);
		list1.add(0, 30);
		assertEquals("first element 30 is correct", (Integer)30, list1.head.next.data);
		assertEquals("element next to index 0 equals to initial element in list at index 0 before adding at index 0", (Integer)list1Ind0, list1.get(1));
		assertEquals("List size is 4 is correct", 4, list1.size);
		
		int list1Ind3 = list1.get(3);
		list1.add(3, 40);
		assertEquals("last element 42 is correct", (Integer)42, list1.tail.prev.data);
		assertEquals("element next to index 3 equals to last element in list at index 3 before adding at index 3", (Integer)list1Ind3, list1.get(4));
		assertEquals("List size is 5 is correct", 5, list1.size);
		
		// Test adding in middle of the list
		int list1Ind1 = list1.get(1);
		int list1Ind2 = list1.get(2);
		list1.add(2, 100);
		assertEquals("element at index 3 is equal to the element at index 2 before adding to index 2", list1.get(3), (Integer)list1Ind2);
		assertEquals("element at index 1 remain the same", list1.get(1), (Integer)list1Ind1);
		assertEquals("element at index 2 is 100 is correct", list1.get(2), (Integer)100);
		
		// Test adding null element
		try {
			list1.add(0, null);
			fail("check NullPointerException");
		} catch (NullPointerException e){
			
		}
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		// test for null pointer
		try {
			list1.set(0, null);
			fail("Check null pointer exception");
		} catch (NullPointerException e) {
			
		}
		
		// test for setting in index our of bound
		int a = list1.set(1, 5);
		assertEquals("Check if list at index 1 is 5 is correct", (Integer)5, list1.get(1));
		assertEquals("Check if old value is 21 is correct", 21, a);
		
		// test for setting at last and first index
		int b = list1.set(0, 10);
		assertEquals("Check if list at index 0 is 10 is correct", (Integer)10, list1.get(0));
		assertEquals("Check if old value is 65 is correct", 65, b);
		
		int c = list1.set(2, 30);
		assertEquals("Check if list at index 2 is 30 is correct", (Integer)30, list1.get(2));
		assertEquals("Check if old value is 42 is correct", 42, c);
		
		// test when setting item in emptyList
		try {
			emptyList.set(0, 39);
			fail("Check for index out of bound");
		} catch(IndexOutOfBoundsException e) {
			
		}
		
		try {
			emptyList.set(1, 39);
			fail("Check for index out of bound");
		} catch(IndexOutOfBoundsException e) {
			
		}

		try {
			emptyList.set(-1, 39);
			fail("Check for index out of bound");
		} catch(IndexOutOfBoundsException e) {
			
		}

	    
	}
	
	
	// TODO: Optionally add more test methods.
	
}
