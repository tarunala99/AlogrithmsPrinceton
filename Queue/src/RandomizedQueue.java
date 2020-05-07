import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	//using an array to be able to remove elements in constant time with the index
	//use dynamic sizing for the array so that the insert elements are in constant time
	//java doesn't have the dynamic sizing option, but the array list takes care of this

	private Item[] queue;
	private int size;
    // construct an empty randomized queue
	//cannot create an array of Items
    public RandomizedQueue()
    {
    	queue = (Item[])new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
    	if(size==0)
    		return true;
    	else
    		return false;
    }

    // return the number of items on the randomized queue
    public int size()
    {
    	return size;
    }
    
    private void resizeArray()
    {
    	Item[] temp = null;
    	if(size>queue.length)
    	{
    		//the array is full here
    		 temp = (Item[])new Object[2*queue.length];
    		 System.arraycopy( queue, 0, temp, 0, size-1 );
    		 queue = temp;
    	}
    	else if(size<queue.length/4)
    	{
    		 temp = (Item[])new Object[queue.length/2];
    		 System.arraycopy( queue, 0, temp, 0, size );
    		 queue = temp;
    	}
    }

    // add the item
    public void enqueue(Item item)
    {
    	if(item==null)
    	{
    		throw new IllegalArgumentException ();
    	}
    	size=size+1;
    	resizeArray();
    	
    	queue[size-1]=item;
    }

    // remove and return a random item
    public Item dequeue()
    {
    	if(size==0)
    	{
    		throw new NoSuchElementException();
    	}
    	int index = StdRandom.uniform(0, size);
		Item returnValue = queue[index];
		queue[index]=queue[size-1];
		size--;
		resizeArray();
		return returnValue;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
    	int index = StdRandom.uniform(0, size);
    	return queue[index];
    }

    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
    	return new ArrayListIterator();
    }
    
    private class ArrayListIterator implements Iterator<Item>
    {
    	int tempSize = size;
    	public boolean hasNext() {
    		if(tempSize>0)
    			return true;
    		else
    			return false;
    	}
    	public void remove() {
    		throw new UnsupportedOperationException();
    	}
    	public Item next() {
    		int index = StdRandom.uniform(0, tempSize);
    		Item returnValue = queue[index];
    		queue[index]=queue[tempSize-1];
    		tempSize--;
    		return returnValue;
    	}	
    }

    // unit testing (required)
    public static void main(String[] args)
    {
    	RandomizedQueue<Integer> sample = new RandomizedQueue<Integer>();
    	sample.iterator();
    	sample.isEmpty();
    	sample.size();
    	sample.iterator();
    	sample.enqueue(10);
    	sample.enqueue(11);
    	sample.dequeue();
    	System.out.println(sample.sample());
    	sample.enqueue(12);
    	sample.enqueue(13);
    	sample.enqueue(14);
    	for(int a : sample)
    	{
    		System.out.println(a);
    	}
    	
    }

}