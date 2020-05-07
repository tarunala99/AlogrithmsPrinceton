import java.util.Iterator;
import java.util.NoSuchElementException;

class Node<Item>{
	//The item here needs to be generic rather than using an object
	Item data;
	Node<Item> next;
	//requires the doubly linked list to support removal of the last element
	Node<Item> prev;
}

public class Deque<Item> implements Iterable<Item> {
	
	//Implementing using a linked list
	
    // construct an empty deque
	
	private int queueSize = 0;
	private Node<Item> head = null;
	private Node<Item> tail = null;
	//Empty constructor
    public Deque()
    {
    	
    }

    // is the deque empty?
    public boolean isEmpty()
    {
    	if(size()==0)
    		return true;
    	return false;
    }

    // return the number of items on the deque
    public int size()
    {
    	return queueSize;
    }

    // add the item to the front
    //the tail is expanded when an element is added
    public void addFirst(Item item)
    {
    	if(item==null)
    	{
    		throw new IllegalArgumentException ();
    	}
    	Node<Item> temp = new Node<Item>();
    	temp.data = item;
    	if(tail!=null) //case where the queue is just initialized
    	{
    		tail.next = temp;
    		temp.prev = tail;
    	}
    	tail=temp;
    	queueSize++;
    	//if it is the first element then reassign the head to the tail
    	if(head==null)
    	{
    		head = tail;
    	}
    }

    // add the item to the back
    public void addLast(Item item)
    {
    	
    	if(item==null)
    	{
    		throw new IllegalArgumentException ();
    	}
    	Node<Item> temp = new Node<Item>();
    	temp.data = item;
    	temp.next = head;
    	if(head!=null) {
    		head.prev=temp;
    	}
    	
    	head=temp;
    	
    	if(tail==null)
    	{
    		tail = head;
    	}
    	queueSize++;
    }

    // remove and return the item from the front
    //move the tail backward
    public Item removeFirst()
    {
    	if(isEmpty())
    	{
    		throw new NoSuchElementException();
    	}
    	Item returnItem = tail.data;
    	tail=tail.prev;
    	if(tail!=null)
    	{
    		tail.next=null;
    	}
    	queueSize--;
    	return returnItem;
    }

    // remove and return the item from the back
    //move the head forward
    public Item removeLast()
    {
    	if(isEmpty())
    	{
    		throw new NoSuchElementException();
    	}
    	Item temp = head.data;
    	head = head.next;
    	if(head!=null)
    	{
    		head.prev=null;
    	}
    	queueSize--;
    	return temp;
    }
    private class LinkedListIterator implements Iterator<Item>
    {
    	//assign temp variable so that there is no repitition
    	private Node<Item> temp = head;
    	public boolean hasNext() {
    		if(temp==null)
    			return false;
    		else
    			return true;
    	}
    	public void remove() {
    		throw new UnsupportedOperationException();  		
    	}
    	public Item next() {
    		Item temp1 = temp.data;
    		temp=temp.next;
    		return temp1;
    	}	
    }
    
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
    	return new LinkedListIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
    	Deque<Integer> deque = new Deque<Integer>();
    	deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        for(Integer temp :deque)    	{
    		System.out.println(temp);
    	}
    	System.out.println(deque.isEmpty());
    	
    }

}