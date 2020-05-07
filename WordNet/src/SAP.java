import java.util.Arrays;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class SAP {
	
	private final Digraph graph;
	private int[] distance;
	private int[] reverseDistance;
	private boolean[] marked;
	private boolean[] reverseMarked;
	private int[] parent;
	private int size;
	private Queue<Integer> queue;
	private Queue<Integer> reverseQueue;
	private int solution;
	private int shortestDistance;
	

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
	   if(G==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   graph = G;
	   size = graph.V();
	   marked = new boolean[size];
	   reverseMarked = new boolean[size];
	   distance = new int[size];
	   reverseDistance = new int[size];
	   parent = new int[size];
	   //setting the distance value to the maximum
	   for(int i=0;i<size;i++)
	   {
		   distance[i]=Integer.MAX_VALUE;
		   reverseDistance[i]=Integer.MAX_VALUE;
	   }
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
		   
	   if(v==w)
	   {
		   return 0;
	   }
	   shortestPath(v,w);
	   if(solution<0)
		   return -1;
	   return distance[solution]+reverseDistance[solution];
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
	   if(v<0 || w<0||v>=size||w>=size)
	   {
		   throw new IllegalArgumentException();
	   }
	   return shortestPath(v,w);
   }
   
   private int shortestPath(int v,int w)
   {
	   //update the distance
	   //queue marked and distance can be local variables which are passed
	   if(v==w)
	   {
		   return v;
		   
	   }
	   queue = new Queue<Integer>();
	   reverseQueue = new Queue<Integer>();
	   queue.enqueue(v);
	   marked[v]=true;
	   reverseQueue.enqueue(w);
	   distance[v]=0;
	   reverseDistance[w]=0;
	   reverseMarked[w]=true;
	   solution=Integer.MIN_VALUE;
	   while(!queue.isEmpty() || !reverseQueue.isEmpty())
	   {
		   if(!queue.isEmpty())
			   solution = updateQueue();
		   if(solution>=0)
			   break;
		   if(!reverseQueue.isEmpty())
			   solution = updateReverseQueue();
		   if(solution>=0)
			   break;
	   }
	   if(solution<0)
		   return -1;
	   return solution;
   }
   
   //do it on both the directions to get a bidrectional search
   //add both the sides to get the final length
   private int updateReverseQueue()
   {
	   int currentNode = 0;
	   currentNode = reverseQueue.dequeue();
	   int currentDistance = reverseDistance[currentNode];
	   for(Integer nextNode : graph.adj(currentNode)) { 
		   if(currentDistance+1<reverseDistance[nextNode])
		   {
			   reverseDistance[nextNode] = currentDistance+1;
			   parent[nextNode]=currentNode;
		   }
		   if(marked[nextNode])
			   return nextNode;
		   if(!reverseMarked[nextNode]) {
			   reverseQueue.enqueue(nextNode);
			   reverseMarked[nextNode] = true;
		   }
	   }
	   return Integer.MIN_VALUE;
   }
   
   private int updateQueue()
   {
	   int currentNode = 0;
	   currentNode = queue.dequeue();
	   int currentDistance = distance[currentNode];
	   for(Integer nextNode : graph.adj(currentNode)) { 
		   if(currentDistance+1<distance[nextNode])
		   {
			   distance[nextNode] = currentDistance+1;
			   parent[nextNode]=currentNode;
		   }
		   if(reverseMarked[nextNode])
			   return nextNode;
		   if(!marked[nextNode]) {
			   queue.enqueue(nextNode);
			   marked[nextNode] = true;
		   }
		   
	   }
	   return Integer.MIN_VALUE;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
	   iterableBFS(v, w);
	   return shortestDistance;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
	   return iterableBFS(v, w);
   }

   private int iterableBFS(Iterable<Integer> v, Iterable<Integer> w)
   {
	   shortestDistance = Integer.MAX_VALUE;
	   int ancestor = 0;
	   //might require more optimizations
	   //currently at n^2
	   for(Integer node1 : v)
	   {
		   for(Integer node2 : w)
		   {
			   if(node1==null || node2==null || node1<0 || node2<0||node1>=size||node2>=size)
			   {
				   throw new IllegalArgumentException();
			   }
			   int tempAncestor = ancestor(node1,node2);
			   int tempDistance = length(node1,node2);
			   if(tempDistance<shortestDistance && tempDistance>=0)
			   {
				   ancestor = tempAncestor;
				   shortestDistance = tempDistance;
			   }
		   }
	   }
	   return ancestor;
   }
   // do unit testing of this class
   public static void main(String[] args)
   {
	   	//In in = new In(args[0]);
	   	In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    G.reverse();
	    System.out.println(G.toString());
	    SAP sap = new SAP(G);
	    System.out.println("The ancestor is" + sap.ancestor(0, 6));
	    System.out.println(Arrays.toString(sap.parent));
	    System.out.println("The length is"+sap.length(0, 6));
   }
}