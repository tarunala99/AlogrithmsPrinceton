import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;
class TempBoard implements Comparable<TempBoard>{
    Board board;
    int priority;
    TempBoard parent;
    int moveCount;
    
    TempBoard(Board board,int manhattan,int moves,TempBoard parent)
    {
        this.board=board;
        this.priority=manhattan+moves;
        this.parent=parent;
        this.moveCount = moves;
    }
    
    public int compareTo(TempBoard compareBoard) {
        return priority-compareBoard.priority;
    }
    
}

public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    private MinPQ<TempBoard> queue = new MinPQ<TempBoard>();
    private MinPQ<TempBoard> twinQueue = new MinPQ<TempBoard>();
    private int moves = 0;
    private List<Board> solutionList;
    private TempBoard solutionBoard = null;
    private int solvable =0;
    public Solver(Board initial)
    {
        if(initial==null)
            throw new IllegalArgumentException();
        Board currentBoard = initial;
        Board currentTwinBoard = initial.twin();
        solutionList = new ArrayList<>();
        int tempManhattan = initial.manhattan();
        int tempTwinManhattan =currentTwinBoard.manhattan();
        TempBoard board = new TempBoard(currentBoard,tempManhattan,0,null);
        TempBoard twinBoard = new TempBoard(currentTwinBoard,tempTwinManhattan,0,null);
        if(tempManhattan==0)
        {
            solutionBoard = board;
            solvable = 1;
            createList();
            return;
        }
        if(tempTwinManhattan==0)
        {
            solvable = 2;
            return;
        }
        queue.insert(board);
        twinQueue.insert(twinBoard);
        while(!queue.isEmpty())
        {
            board = queue.delMin();
            currentBoard = board.board;
            twinBoard = twinQueue.delMin();
            currentTwinBoard = twinBoard.board;
            //System.out.println(currentBoard.toString());
            if(currentBoard.manhattan()==0)
            {
                solutionBoard = board;
                createList();
                solvable = 1;
                return;
            }
            if(tempTwinManhattan==0)
            {
                solvable = 2;
                return;
            }
            Iterator<Board> test = currentBoard.neighbors().iterator();
            int tempVal = board.moveCount;
            tempVal++;
            int twinVal = twinBoard.moveCount;
            twinVal++;
            while(test.hasNext())
            {
                Board tempBoard = test.next();
                //System.out.println(tempBoard.toString() +" sdskdl  "+boardList.size());
                int manhattan = tempBoard.manhattan();
                //int movieCount = board.moveCount++;
                TempBoard temp10 = new TempBoard(tempBoard,manhattan,tempVal,board);
                if(manhattan==0)
                {
                    solutionBoard = temp10;
                    createList();
                    solvable = 1;
                    return;
                }
                if(temp10.parent==null || temp10.parent.parent==null || !temp10.parent.parent.board.equals(temp10.board))
                {
                    queue.insert(temp10);
                }
            }
            test = currentTwinBoard.neighbors().iterator();
            while(test.hasNext())
            {
                Board tempBoard = test.next();
                int manhattan = tempBoard.manhattan();
                //int movieCount = twinBoard.moveCount++;
                TempBoard temp10 = new TempBoard(tempBoard,manhattan,twinVal,twinBoard);
                if(manhattan==0)
                {
                    solvable = 2;
                    return;
                }
                if(temp10.parent==null || temp10.parent.parent==null || !temp10.parent.parent.board.equals(temp10.board))
                {
                    twinQueue.insert(temp10);
                }
            }
        }
    }
    private void createList()
    {
        solutionList = new ArrayList<Board>();
        TempBoard tempSol = solutionBoard;
        while(tempSol.parent!=null)
        {
            solutionList.add(0,tempSol.board);
            tempSol = tempSol.parent;
        }
        //adding the original board
        solutionList.add(0,tempSol.board);
        moves=solutionList.size()-1;
        
    }
    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        return solvable!=2;
    }

    // min number of moves to solve initial board
    public int moves()
    {
    	if(!isSolvable())
    		return -1;
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if(!isSolvable())
            return null;
        Iterable<Board> iterable = solutionList;
        return iterable;
    }

    // test client (see below) 
    public static void main(String[] args)
    {
        
          int[][] temp = {{8,3,5},{6,4,2},{1,0,7}}; 
          int[][] temp1 ={{5,1,8},{2,7,3},{4,0,6}}; 
          Board board = new Board(temp); 
          Solver solver = new Solver(board); 
          System.out.println("the count is "+solver.isSolvable()); 
          Iterator<Board> test = solver.solution().iterator();
          System.out.println("the count is "+solver.moves()); 
          while(test.hasNext()) {
          Board board1 = test.next();
          System.out.println(board1.toString()+board1.manhattan()); }
    }

}