import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
	
	private Map<String,Integer> wordMap = new HashMap<String,Integer>();
	private Digraph graph;
	private int resultDistance;
	private final SAP sap;

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
	   sap = new SAP(graph);
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
	   getDist(nounA, nounB);
	   return resultDistance;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
	   getDist(nounA, nounB);
	   for(Entry<String,Integer> i :wordMap.entrySet())
	   {
		   if(i.getValue()==resultDistance)
			   return i.getKey();
	   }
	   return null;
   }

	private void getDist(String nounA, String nounB) {
		   
		   int nodeA = wordMap.get(nounA);
		   int nodeB = wordMap.get(nounB);
		   resultDistance = sap.length(nodeA, nodeB);
	}

   // do unit testing of this class
   public static void main(String[] args)
   {
	   WordNet wordNet = new WordNet("synsets.txt","hypernyms.txt");
	   System.out.println(wordNet.sap("Maya","gentleness"));
	   System.out.println(wordNet.distance("Maya","gentleness"));
   }
}