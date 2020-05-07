import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private double mean = 0;
	
	private double stdev = 0;
	
	private int size = 0;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
    	if(n<=0 || trials<=0)
    	{
    		throw new IllegalArgumentException ();
    	}
    	int count2=0;
    	double[] resultArray = new double[trials];
    	while(count2<trials)
    	{
	    	Percolation percolation = new Percolation(n);
	    	int count = 0;
	    	while(!percolation.percolates())
	    	{
	    		int index = StdRandom.uniform(0, n*n);
	        	int row = index/n+1;
	        	int col =  index%n+1;
	    		if(!percolation.isOpen(row,col))
	    		{
	    			count++;
	    			percolation.open(row,col);
	    		}
	    	}
	    	resultArray[count2]=(count*1.0)/(n*n);
	    	count2++;
    	}
    	mean = StdStats.mean(resultArray);
    	stdev = StdStats.stddev(resultArray);
    	size = n;
    }

    // sample mean of percolation threshold
    public double mean()
    {
    	return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
    	return stdev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean - 1.96*stdev/Math.pow(size, 0.5);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean + 1.96*stdev/Math.pow(size, 0.5);
    }

   // test client (see below)
   public static void main(String[] args) {
	   PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	   //PercolationStats percolationStats = new PercolationStats(6,2);
	   System.out.println("mean	\t = "+percolationStats.mean());
	   System.out.println("stddev	\t = "+percolationStats.stddev());
	   System.out.println("95% confidence interval \t= ["+percolationStats.confidenceLo()+", "+percolationStats.confidenceHi()+"]");
   }

}