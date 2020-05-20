import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	
	private WordNet wordnet;
   public Outcast(WordNet wordnet)         // constructor takes a WordNet object
   {
	   this.wordnet = wordnet;
   }
   public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
   {
	   int maxDistance = Integer.MIN_VALUE;
	   String result = null;
	   for(String temp1 : nouns)
	   {
		   int distance = 0;
		   for(String temp2 : nouns)
		   {
			   if(!temp1.equals(temp2))
			   {
				   distance = distance + wordnet.distance(temp1,temp2);
			   }
		   }
		   if(maxDistance>distance)
		   {
			   maxDistance = distance;
			   result = temp1;
		   }
	   }
	   return result;
   }
   public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}


