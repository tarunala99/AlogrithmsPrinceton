import java.util.ArrayList;
import java.util.List;

public class Board {

	private final int[][] board;
	private final int size;
	private final int manhattan;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
    	size = tiles.length;
    	board = new int[size][size];
    	int count = 0;
    	for(int i=0;i<tiles.length;i++)
    	{
    		for(int j=0;j<tiles.length;j++)
    		{
    			board[i][j]=tiles[i][j];
    			int currentNo = board[i][j];
    			if(currentNo!=0)
    			{
    				count = count + Math.abs(rowNo(currentNo)-(i+1));
    				count = count + Math.abs(columnNo(currentNo)-(j+1));
    				//System.out.println("The current count is "+count+" and the number is "+currentNo);
    			}
    		}
    	}
    	manhattan = count;
    }
                                           
    // string representation of this board
    public String toString()
    {
    	StringBuilder string = new StringBuilder();
    	string.append(size+"\n");
    	for(int i=1;i<=size;i++)
    	{
    		for(int j=1;j<=size;j++)
    		{
    			string.append(" "+board[i-1][j-1]);
    		}
    		string.append("\n");
    	}
    	String resultString = string.toString();
    	//removing the trailing new line character
    	return resultString.substring(0,resultString.length()-1);
    }

    // board dimension n
    public int dimension()
    {
    	return size;
    }
    
    private int singleRow(int row,int column)
    {
    	return column+size*(row-1);
    }
    
    private int rowNo(int index)
    {
    	//System.out.println("The current row is "+index/size);
    	return ((index-1)/size+1);
    }
    private int columnNo(int index)
    {
    	//System.out.println("The current column is "+index%size);
    	return ((index-1)%size+1);
    }
    
 // number of tiles out of place
    public int hamming()
    {
    	int count = 0;
    	for(int i=1;i<=size;i++)
    	{
    		for(int j=1;j<=size;j++)
    		{
    			if(board[i-1][j-1]!=0 && board[i-1][j-1]!=singleRow(i,j))
	    		{
	    			count++;
	    		}
    		}
    	}
    	return count;
    }
    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
    	return manhattan;
    }
    // is this board the goal board?
    public boolean isGoal()
    {
    	if(hamming()==0)
    	{
    		return true;
    	}
    	return false;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
    	if (!(y instanceof Board)) {
    		return false;
    		}
    	Board temp =(Board) y;
    	int[][] tempObj = temp.board;
    	if(tempObj.length!=size || tempObj[0].length!=size)
    		return false;
    	if(tempObj.length==size)
    	{
    		int count = 0;
    		for(int i=0;i<size;i++)
    		{
    			for(int j=0;j<size;j++)
    			{
    				count=count+Math.abs((tempObj[i][j]-board[i][j]));
    			}
    			if(count>0)
    			{
    				return false;
    			}
    		}
    	}
    	else {
    		return false;
    	}
    	return true;
    }

    private int findZero()
    {
    	for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				if(board[i][j]==0)
				{
					return (j+1)+i*(size);
				}
			}
		}
    	//throw exception here
    	return Integer.MAX_VALUE;
    }
    private boolean checkValid(int row,int column)
    {
    	if(row>0 && column>0 && row<=size && column<=size)
    	{
    		return true;
    	}
    	return false;
    }
    
    private int[][] createArray()
    {
    	int [][] resultBoard = new int[size][size];
    	for(int i = 0; i < size; i++)
    	{
    		for(int j=0;j<size;j++)
    		{
    			resultBoard[i][j]=board[i][j];
    		}
    	}
    	return resultBoard;
    }
    
    public Iterable<Board> neighbors() {
    	List<Board> resultList = new ArrayList<>();
		int index = findZero();
		
		int row = rowNo(index);
		int col = columnNo(index);
		
    	int[][] indexArray = {{row+1,col},{row,col-1},{row-1,col},{row,col+1}};
    	for(int[] i: indexArray)
    	{
    		int rowNo = i[0];
    		int colNo = i[1];
    		if(checkValid(rowNo,colNo))
    		{
    			//create a new array and make it iterable
    			int [][] cloneArray = createArray();
    			cloneArray[rowNo(index)-1][columnNo(index)-1] = cloneArray[rowNo-1][colNo-1];
    			cloneArray[rowNo-1][colNo-1]=0;	
    			resultList.add(new Board(cloneArray));
    		} 
    	}
    	Iterable<Board> iterable = resultList;
    	return iterable;
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
    	int[][] twinBoard = createArray();
    	int row = 1;
    	int column = 1;
    	if(twinBoard[row-1][column-1]==0)
    	{
    		column =2;
    	}
    	int row1= 1;
    	int column1= 1;
    	boolean flag = false;
    	for(int i=0;i<size;i++)
    	{
    		for(int j=0;j<size;j++)
    		{
    			if(twinBoard[i][j]!=0 && twinBoard[i][j]!=twinBoard[row-1][column-1])
    			{
    				row1= i+1;
    				column1 = j+1;
    				flag = true;
    				break;
    			}
    		}
    		if(flag)
    			break;
    	}
    	int tempValue = twinBoard[row-1][column-1];
    	twinBoard[row-1][column-1]=twinBoard[row1-1][column1-1];
    	twinBoard[row1-1][column1-1]=tempValue;
    	return new Board(twinBoard);
    }
    // unit testing (not graded)
    public static void main(String[] args)
    {
    	int[][] temp = {{2,1},
    	         {0,3}};
    	int[][] temp1 = {{1,2,3,4,5,6,7,8,9},
    			{10,11,12,13,14,15,16,17,18},
    			{19,20,21,22,23,24,25,26,27},
    			{28,29,30,31,32,33,34,35,36},
    			{37,38,39,40,41,42,43,44,45},
    			{46,47,48,49,50,51,52,53,54},
    			{55,56,57,58,59,60,61,62,63},
    			{64,0,65,67,68,78,69,70,72},
    			{73,74,66,75,76,77,79,71,80}};
    	
    	//Board board = new Board(temp);
		/*
		 * System.out.println(board.equals(temp1)+"he length is "+temp1.length);
		 * System.out.println(board.toString());
		 * System.out.println("The hamming distance is "+board.hamming()
		 * +" and the manhattan distance is "+board.manhattan());
		 */
		
		/*
		 * Iterator<Board> test = board.neighbors().iterator(); while(test.hasNext()) {
		 * System.out.println(test.next().toString()); }
		 */
    	Object other =10;
    	Board board = new Board(temp);
    	System.out.println(board.manhattan());
	     for(int i=0;i<1000;i++)
	     {
	    	 Board twin = board.twin();
	    	 System.out.println(twin.toString());
	     }
	     System.out.println(board.toString());
    	
    }

}