import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
	
	private Map<String,Integer> wordMap = new HashMap<String,Integer>();
	private Digraph graph;
	private int resultDistance;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
	   In sysnetFile = new In(synsets);
	   In hypernymFile = new In(hypernyms);
	   while(sysnetFile.hasNextLine()) {
		   String temp = sysnetFile.readLine();
		   String[] tempList = temp.split(",");
		   String nounTemp = tempList[1];
		   String[] tempList1 = nounTemp.split(" ");
		   for(String data:tempList1)
		   {
			   wordMap.put(data,Integer.parseInt(tempList[0]));
		   }
	   }
	   graph = new Digraph(wordMap.size());
	   while(hypernymFile.hasNextLine()) {
		   String temp = hypernymFile.readLine();
		   String[] tempList = temp.split(",");
		   for(int i=1;i<tempList.length;i++)
		   {
			   graph.addEdge(Integer.parseInt(tempList[0]),Integer.parseInt(tempList[i]));
		   }
	   }
   }

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
	   return wordMap.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
	   return wordMap.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
	   return resultDistance;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
	   SAP sap = new SAP(graph);
	   int nodeA = wordMap.get(nounA);
	   int nodeB = wordMap.get(nounB);
	   int resultNode = sap.ancestor(nodeA, nodeB);
	   resultDistance = sap.length(nodeA, nodeB);
	   for(Entry<String,Integer> i :wordMap.entrySet())
	   {
		   if(i.getValue()==resultDistance)
			   return i.getKey();
	   }
	   return null;
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
	   
   }
}