import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


class Data{
	int row;
	int column;
	
	Data(int row1,int column1)
	{
		row = row1;
		column = column1;
	}
}

public class BoggleSolver {
	
	private final Dictionary dictionaryTree;
	private Map<Integer,Integer> pointsMap;
	private Set<String> resultString;
	// Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
    	pointsMap();
    	resultString = new TreeSet<String>();
    	dictionaryTree = new Dictionary();
    	for(String temp : dictionary)
    	{
    		dictionaryTree.addString(temp,10);
    	}
    }
    
    private void pointsMap()
    {
    	pointsMap = new HashMap<>();
    	pointsMap.put(0,0);
    	pointsMap.put(1,0);
    	pointsMap.put(2,0);
    	pointsMap.put(3,1);
    	pointsMap.put(4,1);
    	pointsMap.put(5,2);
    	pointsMap.put(6,3);
    	pointsMap.put(7,5);
    	pointsMap.put(8,11);
    }

 // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	int length = word.length();
		length = length>8?8:length;
		int points = pointsMap.get(length);
		if(dictionaryTree.equalsString(word))
			return points;
		else
			return 0;
    }
    
    //returns list of characters in all directions
    private List<Data> getNeighbour(BoggleBoard board, int i,int j)
    {
    	List<Data> characterList = new ArrayList<>();    	
    	validateNodes(board,i,j+1,characterList);
    	validateNodes(board,i-1,j+1,characterList);
    	validateNodes(board,i+1,j,characterList);
    	validateNodes(board,i-1,j,characterList);
    	validateNodes(board,i-1,j-1,characterList);
    	validateNodes(board,i+1,j-1,characterList);
    	validateNodes(board,i,j-1,characterList);
    	validateNodes(board,i+1,j+1,characterList);
    	return characterList;
    }
    
    private void validateNodes(BoggleBoard board, int i,int j,List<Data> characterList)
    {
    	int rows = board.rows();
    	int cols = board.cols();
    	if(i<0 || j<0 || i>=rows || j>=cols)
    	{
    		return;
    	}
    	characterList.add(new Data(i,j));
    	return;
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    	resultString = new TreeSet<String>();
    	for(int i=0;i<board.rows();i++)
    	{
    		for(int j=0;j<board.cols();j++)
    		{
    			boolean[][] marked = new boolean[board.rows()][board.cols()];
    			String alpha = String.valueOf(board.getLetter(i, j));
    			if(alpha.equals("Q"))
    			{
    				alpha = "QU";
    			}
    			checkString(alpha,i,j,board,marked);    			
    		}
    	}
    	return resultString;
    }
    
    private void checkString(String string,int i, int j,BoggleBoard board,boolean[][] marked)
    {
		List<Data> neighs = getNeighbour(board, i, j);
		marked[i][j]=true;
		String temp1 = null;
		for(Data temp : neighs)
		{
			int tempRow = temp.row;
			int tempCol = temp.column;
			//not letting it go back
			if(marked[tempRow][tempCol])
				continue;
			temp1 = string + String.valueOf(board.getLetter(tempRow, tempCol));
			if(String.valueOf(board.getLetter(tempRow, tempCol)).equals("Q"))
					temp1 = temp1 + "U";
			if(dictionaryTree.equalsString(temp1))
			{
				//String is only valid when length is >=3
				if(temp1.length()>=3)
					resultString.add(temp1);
				checkString(temp1,tempRow,tempCol,board,marked);
			}
			if(dictionaryTree.doesContain(temp1))
			{
				checkString(temp1,tempRow,tempCol,board,marked);
			}
		}
		//resetting the value so other call by references can use it
    	marked[i][j]=false;
    }
	 
    public static void main(String[] args) {
    	In in = new In("dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("board-q.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
