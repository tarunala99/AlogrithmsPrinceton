import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
	
   private int count = 0;
   private List<Point[]> segs = new ArrayList<>();
   
   private Point minCompare(Point minNode,Point currentNode)
   {
	   if(minNode.compareTo(currentNode)==1)
		{
			minNode = currentNode;
		} 
	   return minNode;
   }
   
   private Point maxCompare(Point maxNode,Point currentNode)
   {
	   if(maxNode.compareTo(currentNode)==-1)
		{
			maxNode = currentNode;
		} 
	   return maxNode;
   }
   
   public BruteCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
	   if(points==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   	Point[] temp = points;	
		Point[] temp1 = Arrays.copyOf(temp, temp.length);
		
		for(int i=0;i<temp1.length;i++)
		{
			int dupCount = 0;
			Point currentPoint = temp1[i];
			temp = Arrays.copyOf(temp1, temp.length);
			Arrays.sort(temp,currentPoint.slopeOrder());
			//System.out.println("The current point is "+currentPoint.toString()+" and the array is "+Arrays.toString(temp));
			Double currentSlope = null;
			int count1=0;
			Point minNode = currentPoint;
			Point maxNode = currentPoint;
			for(int j=0;j<temp.length;j++)
			{
				if(temp[j].compareTo(currentPoint)==0)
				{
					dupCount++;
					if(dupCount>=2)
					{
						throw new IllegalArgumentException();
					}
				}
				if(currentSlope!=null && Math.abs(currentSlope) == Math.abs(currentPoint.slopeTo(temp[j])))
				{
					count1++;
					//if j is lesser then update the minNode
					//if j is greater then update the maxNode
					minNode = minCompare(minNode,temp[j]);
					maxNode = maxCompare(maxNode,temp[j]);
				}
				else
				{
					//System.out.println("the current count is "+count1+"the point is "+currentPoint.toString()+"the slope is "+currentSlope+"Tjhe min node is "+minNode.toString()+" the max node is "+maxNode.toString());
						
					if(count1>=2)
					{					
						if(addArray(minNode,maxNode))
						{
							Point[] tempArray = {minNode,maxNode};
							segs.add(tempArray);
							count++;
						}
					}
					currentSlope = currentPoint.slopeTo(temp[j]);
					count1=0;
					if(currentPoint.compareTo(temp[j])==1)
					{
						minNode = temp[j];
					}
					else
					{
						minNode = currentPoint;
					}
					if(currentPoint.compareTo(temp[j])==-1)
					{
						maxNode = temp[j];
					}
					else {
						maxNode = currentPoint;
					}
				}
			}
			//for the case where the last element has not yet been added
			if(count1>=2)
			{					
				if(addArray(minNode,maxNode))
				{
					Point[] tempArray = {minNode,maxNode};
					segs.add(tempArray);
					count++;
				}
			}
		}
		

   }
   public int numberOfSegments()        // the number of line segments
   {
	   return count;
   }
   public LineSegment[] segments()                // the line segments
   {
	   LineSegment[] lineArray = new LineSegment[segs.size()];
	   int count = 0;
	   for(Point[] arr: segs)
		{
			LineSegment temp3 = new LineSegment(arr[0],arr[1]);
			lineArray[count] = temp3;
			count++;
		}
	   return lineArray;
   }
   
   private boolean addArray(Point minNode,Point maxNode)
   {
	   if(segs!=null) {
	   for(Point[] points : segs)
	   {
		   if(points[0].compareTo(minNode)==0&&points[1].compareTo(maxNode)==0)
		   {
			   return false;
		   }
	   	}
	   
	   return true;
	   }
	   return false;
   }
   
   public static void main(String[] args)
	{
		Point[] temp = new Point[16];
	
		
		for(int i=0;i<10;i++)
		{
			temp[i] = new Point(i*10000,10000);
		}
		temp[0] = new Point(1000, 17000);  
		temp[2] = new Point(1000, 27000);  
		temp[7] = new Point(1000, 28000); 
		temp[8] = new Point(1000, 31000);
		
		temp[10] = new Point(24,35);
		temp[11] = new Point(3,8);
		temp[12] = new Point(5,10);
		temp[13] = new Point(7,12);
		temp[14] = new Point(9,14);
		temp[15] = new Point(11,15);
		FastCollinearPoints tempPoints = new FastCollinearPoints(temp); 
		for(LineSegment line :tempPoints.segments())
		{
			System.out.println(line.toString());
		}
	}
}