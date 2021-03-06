import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private WeightedQuickUnionUF colation;
	private int highest; 
	private boolean[] precolationMatrix;
	//caching the computations for the full matrix

	private int size;
	
	private int openCount;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
    	if(n<1)
    	{
    		throw new java.lang.IllegalArgumentException();
    	}
    	colation = new WeightedQuickUnionUF(n*n+1);
    	precolationMatrix = new boolean[n*n];
    	highest = n*n;
    	size = n;
    	for(int i=0;i<n;i++)
    	{
    		for(int j=0;j<n;j++)
        	{
    			precolationMatrix[j+n*i]=false;
        	}
    	}
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
    	if(isOpen(row,col)) 
    	{
    		return;
    	}
    	else
    	{
    		precolationMatrix[index(row,col)] = true;
    		openCount++;
    		int value = index(row,col);
    		int updatedValue ;
    		int minValue ;
    		int maxValue;
    		if(row-1>0 && isOpen(row-1,col)) 
        	{
    			updatedValue = index(row-1,col);
        		minValue = Integer.min(value,updatedValue);
        		maxValue = Integer.max(value,updatedValue);
    			colation.union(minValue, maxValue);
        	}
    		if(col-1>0 && isOpen(row,col-1)) 
        	{
    			value = index(row,col);
    			updatedValue = index(row,col-1);
        		minValue = Integer.min(value,updatedValue);
        		maxValue = Integer.max(value,updatedValue);
        		colation.union(minValue, maxValue);
        	}
    		if(col<size && isOpen(row,col+1)) 
        	{
    			value = index(row,col);
    			updatedValue = index(row,col+1);
        		minValue = Integer.min(value,updatedValue);
        		maxValue = Integer.max(value,updatedValue);
        		colation.union(minValue, maxValue);
        	}
    		if(row<size && isOpen(row+1,col)) 
        	{
    			value = index(row,col);
    			updatedValue = index(row+1,col);
        		minValue = Integer.min(value,updatedValue);
        		maxValue = Integer.max(value,updatedValue);
        		colation.union(minValue, maxValue);
        	}
    		if(row==1)
    		{
    			colation.union(value, highest);
    		}
    		
    	}
    }
    
    private int index(int row,int col)
    {
    	return (col-1)+size*(row-1);
    }
    
    private int rowNo(int index)
    {
    	return index/size+1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
    	if(row>size || col>size ||row<1||col<1)
    	{
    		throw new IllegalArgumentException();
    	}
    	if(precolationMatrix[index(row,col)])
    		return true;
    	else
    		return false;
    }
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
    	if(row>size || col>size ||row<1||col<1)
    	{
    		throw new IllegalArgumentException();
    	}
    	if(!isOpen(row,col))
    	{
    		return false;
    	}
    	int parentIndex = colation.find(index(row,col));
    	if(rowNo(parentIndex)==1)
    		return true;
    	else
    	//case where the root of the matrix is outside the first row
    	{
    		if(parentIndex==colation.find(highest))
   				 return true;
  		  	}
    		return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
    	return openCount;
    }

    // does the system percolate?
    public boolean percolates()
    {	
		  for(int i=1;i<=size;i++)
		  {
			 if(isFull(size,i))
				 return true; 
		  }
		  return false;
		 
    }
    
    // test client (optional)
    public static void main(String[] args)
    {
    	Percolation percolation = new Percolation(6);
    	percolation.open(1,6);
    	//System.out.println(percolation.isFull(1, 6));
    	percolation.open(2,6);
    	percolation.open(3,6);
    	percolation.open(4,6);
    	percolation.open(5,6);
    	percolation.open(5,5);
    	percolation.open(4,4);
    	percolation.open(3,4);
    	percolation.open(2,3);
    	percolation.open(2,4);
    	percolation.open(2,3);
    	percolation.open(2,2);
    	percolation.open(2,1);
    	percolation.open(3,1);
    	percolation.open(4,1);
    	percolation.open(5,1);
    	percolation.open(5,2);
    	percolation.open(6,2);
    	percolation.open(5,4);
    	percolation.open(6,6);
    	System.out.println(percolation.percolates());
    	WeightedQuickUnionUF weightedQuickUnionUF = percolation.colation;
    	for(int i=1;i<=6;i++)
    	{
    		for(int j=1;j<=6;j++)
    		{
    			System.out.print(weightedQuickUnionUF.find(percolation.index(i,j))+" ");
    		}
    		System.out.println("\n");
    	}
    }
}