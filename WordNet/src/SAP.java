import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class SAP {
	
	private final Digraph graph;
	private int distance;
	private int ancestor;
	private int size;
	private Queue<Integer> queue;
	private Queue<Integer> reverseQueue;
	private int maxDistance;
	

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
	   if(G==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   graph = G;
	   size = graph.V();
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
	   //do a directed search on the undirected graph one of the sources
	   //can only change to directed or undirected only once
	   //add caching to reduce number of calls
	   if(v<0 || w<0||v>=size||w>=size)
	   {
		   throw new IllegalArgumentException();
	   }
	   maxDistance = Integer.MAX_VALUE;
	   shortestPath(v,w);
	   return maxDistance;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
	   if(v<0 || w<0||v>=size||w>=size)
	   {
		   throw new IllegalArgumentException();
	   }
	   shortestPath(v,w);
	   return ancestor;
   }
   
   private void shortestPath(int v,int w)
   {
	   //use map to store the values visited and the distance as well
	   Map<Integer,Integer> map1 = new HashMap<>();
	   Map<Integer,Integer> map2 = new HashMap<>();
	   //when they are both the same node
	   if(v==w)
	   {
		   ancestor = v;
		   maxDistance = 0;
		   return;
	   }	   
	   queue = new Queue<Integer>();
	   reverseQueue = new Queue<Integer>();
	   queue.enqueue(v);
	   map1.put(v, 0);
	   map2.put(w, 0);
	   reverseQueue.enqueue(w);
	   int value1 = Integer.MIN_VALUE;
	   int value2 = Integer.MIN_VALUE;
	   
	   while(!queue.isEmpty() || !reverseQueue.isEmpty())
	   {		   
		   if(!queue.isEmpty())
		   {
			   value1 = queue.dequeue();
			   //found a solution
			   if(map2.containsKey(value1))
			   {
			   //just set do not return for iterative values
				   distance = map1.get(value1) + map2.get(value1);
				   if(distance<maxDistance)
				   {
					   ancestor = value1;
					   maxDistance = distance;
				   }
			   }
			   updateQueue(queue,value1,map1);
		   }
		   
		   if(!reverseQueue.isEmpty())
		   {
			   value2 = reverseQueue.dequeue();
			   if(map1.containsKey(value2))
			   {
				   distance = map2.get(value2) + map1.get(value2);
				   if(distance<maxDistance)
				   {
					   ancestor = value2;
					   maxDistance = distance;
				   }
			   }
			   updateQueue(reverseQueue,value2,map2);
		   }
		   //since acyclic same value cannot be reached again
	   }
	   if(maxDistance==Integer.MAX_VALUE)
	   {

		   ancestor = -1;
		   maxDistance = -1;
	   }
	   return;
   }
   
   private void updateQueue(Queue<Integer> queue,int value,Map<Integer,Integer> map)
   {
	   //would require the count as well
	   Iterable<Integer> temp = graph.adj(value);
	   int value1 = map.get(value);
	   for(Integer tempVal : temp)
	   {
		   if(!map.containsKey(tempVal))
		   {
			   queue.enqueue(tempVal);
			   map.put(tempVal, value1+1);
		   }
	   }
   }
   
   private boolean validityCheck(Iterable<Integer> v, Iterable<Integer> w)
   {
	   if(v==null || w==null)
	   {
		   return false;
	   }
	   for(Object temp : v)
	   {
		   if(temp==null)
			   return false;
	   }
	   for(Object temp : w)
	   {
		   if(temp==null)
			   return false;
	   }
	   return true;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
	   if(!validityCheck(v,w))
		   throw new IllegalArgumentException();
	   maxDistance = Integer.MAX_VALUE;
	   iterableBFS(v, w);
	   return maxDistance;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
	   if(!validityCheck(v,w))
		   throw new IllegalArgumentException();
	   iterableBFS(v, w);
	   return ancestor;
   }

   private void iterableBFS(Iterable<Integer> v, Iterable<Integer> w)
   {
	 //use map to store the values visited and the distance as well
	   Map<Integer,Integer> map1 = new HashMap<>();
	   Map<Integer,Integer> map2 = new HashMap<>();  
	   queue = new Queue<Integer>();
	   reverseQueue = new Queue<Integer>();
	   for(Integer temp : v)
	   {
		   queue.enqueue(temp);
		   map1.put(temp, 0);
	   }
	   for(Integer temp : w)
	   {	   
		   map2.put(temp, 0);
		   reverseQueue.enqueue(temp);
	   }

	   int value1 = Integer.MIN_VALUE;
	   int value2 = Integer.MIN_VALUE;
	   
	   while(!queue.isEmpty() || !reverseQueue.isEmpty())
	   {		   
		   if(!queue.isEmpty())
		   {
			   value1 = queue.dequeue();
			   //found a solution
			   if(map2.containsKey(value1))
			   {
			   //just set do not return for iterative values
				   distance = map1.get(value1) + map2.get(value1);
				   if(distance<maxDistance)
				   {
					   ancestor = value1;
					   maxDistance = distance;
				   }
			   }
			   updateQueue(queue,value1,map1);
		   }
		   
		   if(!reverseQueue.isEmpty())
		   {
			   value2 = reverseQueue.dequeue();
			   if(map1.containsKey(value2))
			   {
				   distance = map2.get(value2) + map1.get(value2);
				   if(distance<maxDistance)
				   {
					   ancestor = value2;
					   maxDistance = distance;
				   }
			   }
			   updateQueue(reverseQueue,value2,map2);
		   }
		   //since acyclic same value cannot be reached again
	   }
	   if(maxDistance==Integer.MAX_VALUE)
	   {

		   ancestor = -1;
		   maxDistance = -1;
	   }
	   return;
   }
   // do unit testing of this class
   public static void main(String[] args)
   {
	   	//In in = new In(args[0]);
	   	In in = new In("digraph1.txt");
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    int v = 3;
	    int w = 3;
	    System.out.println("The length is "+sap.length(v, w));
	    System.out.println("The ancestor is "+sap.ancestor(v, w));
   }
}