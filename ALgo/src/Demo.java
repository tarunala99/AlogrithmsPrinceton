import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
	
	//Implement LRU cache
	//Using Linked list HashMap
	//The lined list is to delete any node in O(1)
	//The Hashmap is to access any node in O(1)
	//Using a doubly linked list
	
	static Map<Integer,Node> nodeMap = new HashMap<Integer,Node>();
	static Node head=null,tail=null;
	static int limit = 0;
	static int tailLength = 0;
	//Initializing the capacity of the cache
	//Check the capacity of the cache before creating a new node
	public Demo(int capacity) {
        limit = capacity;
    }
	
	public int get(int key) {
		//return the value
		if(!nodeMap.containsKey(key))
			return -1;
		
		Node valueNode = nodeMap.get(key);
		int value = valueNode.getValue();
		//put it at the front of the queue
		Node tempNode = valueNode;
		tempNode.prev.next = tempNode.next;
		if(tempNode.next!=null)
		tempNode.next.prev = tempNode.prev;
		if(tempNode.next!=null)
		{
			tail = tempNode.next;
		}
		head.prev = valueNode;
		valueNode.next = head;
		valueNode.prev = null;
		head = valueNode;
		System.out.println("Put limit for "+ limit);
		System.out.println("Get successful for "+ value);
		return value;
    }
    
    public void put(int key, int value) {
    	//incase of a map of the same value remove it
    	Node valueNode = null;
    	if(nodeMap.containsKey(key))
    	{
    		valueNode = nodeMap.get(key);
    		valueNode.setIndex(key);
    		valueNode.setValue(value);
    		//move it to the beginning
    		//update the links
    		Node tempNode = valueNode;
    		tempNode.prev.next = tempNode.next;
    		tempNode.next.prev = tempNode.prev;
    		if(tempNode.next.next==null)
    		{
    			tail = tempNode.next;
    		}
    	}
    	else
    	{
    		//create a new node
    		//check capacity
    		//check if node is at capacity
    		//will require reference to the tail node
    		//Need tail length;
    		valueNode = new Node();
    		valueNode.setIndex(key);
    		valueNode.setValue(value);
    		nodeMap.put(key, valueNode);
    		tailLength++;
    		if(tailLength > limit)
    		{
    			System.out.println(tail.value+" sample ");
    			nodeMap.remove(tail.value);
    			tail.prev.next=null;
    			//Remove the tail from the node
    			tailLength--;
    		}
    		if(valueNode.next==null)
    			tail=valueNode;
    	}
    	if(head!=null)
    	head.prev = valueNode;
		valueNode.next = head;
		valueNode.prev = null;
		head = valueNode;
		System.out.println("Put limit for "+ limit);
		System.out.println("Put successful for "+ value);
    }

	public static void main(String[] args)
	{
		Demo cache = new Demo( 2 /* capacity */ );
		cache.put(1, 1);
		cache.put(2, 2);
		int returnValue = cache.get(1);
		cache.put(3, 3);
		System.out.println(cache.get(2)); 
		
		System.out.println(nodeMap.keySet());
		while(head!=null)
		{
			System.out.println(head.getIndex());
			head=head.next;
		}
		cache.put(4, 4);
		cache.get(1);       // returns -1 (not found)
		cache.get(3);       // returns 3
		cache.get(4);
		
		
	}
	
}

class Node {
	
	int index;
	Node prev;
	Node next;
	int value;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Node getPrev() {
		return prev;
	}
	public void setPrev(Node prev) {
		this.prev = prev;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
