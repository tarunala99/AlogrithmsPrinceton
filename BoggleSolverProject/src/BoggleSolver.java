import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieST;

public class BoggleSolver {
	
	Dictionary dictionaryTree;
	Map<Integer,Integer> pointsMap;
	List<String> resultString;
	// Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
    	pointsMap();
    	resultString = new ArrayList<String>();
    	dictionaryTree = new Dictionary();
    	for(String temp : dictionary)
    	{
    		int points=pointScore(temp);
    		dictionaryTree.addString(temp,points);
    	}
    }
    
    private int pointScore(String string)
    {
    	int length = string.length();
		length = length>8?8:length;
		int points = pointsMap.get(length);
		return points;
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
    	if(dictionaryTree.contains(word))
    	{
    		return pointScore(word);
    	}
    	return 0;
    }
    
    private Iterable<Character> getNeighbour(BoggleBoard board, int i,int j,String currentString)
    {
    	Character currentChar = board.getLetter(i, j);
    	currentString = currentString + currentChar;
    	List<Character> characterList = new ArrayList<>();    	
    	Character tempChar = validateNodes(board,i+1,j+1,characterList);
    	validateCharacter(tempChar,currentString);
    	validateNodes(board,i,j+1,characterList);
    	validateNodes(board,i-1,j+1,characterList);
    	validateNodes(board,i+1,j,characterList);
    	validateNodes(board,i-1,j,characterList);
    	validateNodes(board,i-1,j-1,characterList);
    	validateNodes(board,i+1,j-1,characterList);
    	validateNodes(board,i,j-1,characterList);
    	return characterList;
    }
    
    private void validateCharacter(Character tempChar,String currentString)
    {
	    
		StringBuilder sb = new StringBuilder();
		sb.append(currentString);
		sb.append(tempChar);
		String string = sb.toString();
		boolean flag = dictionaryTree.contains(string);
		int value = 0;
		//case where there is a matching between the values
		if(flag)
		{
			value = dictionaryTree.get(string);
			resultString.add(string);
		}
		//case where it is a prefix match
		//continue with expanding the character list
		Iterable<String> stringList = dictionaryTree.keysWithPrefix(string);
		if(stringList.iterator().hasNext())
		{
			
		}
    }
    
    
    private Character validateNodes(BoggleBoard board, int i,int j,List<Character> characterList)
    {
    	int rows = board.rows();
    	int cols = board.cols();
    	if(i<0 || j<0 || i>=rows || j>=cols)
    	{
    		return null;
    	}
    	return board.getLetter(i, j);
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    	for(int i=0;i<board.rows();i++)
    	{
    		for(int j=0;j<board.cols();j++)
    		{
    			char alpha = board.getLetter(i, j);
    		//	Iterable<Character> neighs = getNeighbour(board, i, j);
    		}
    		System.out.println();
    	}
    	return null;
    }
	 
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
