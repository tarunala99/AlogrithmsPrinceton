import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private Set<Point2D> pointsSet; 
	public PointSET()                               // construct an empty set of points 
	{
		pointsSet = new TreeSet<>();
	}
   public boolean isEmpty()                      // is the set empty? 
   {
	   if(pointsSet==null || pointsSet.isEmpty())
	   {
		   return true;
	   }
	   return false;
   }
   public int size()                         // number of points in the set 
   {
	   return pointsSet.size();
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
	   if(p==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   pointsSet.add(p);
   }
   public boolean contains(Point2D p)         // does the set contain point p? 
   {
	   if(p==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   return pointsSet.contains(p);
   }
   public void draw()                         // draw all points to standard draw 
   {
	   for(Point2D point : pointsSet)
		{
		   StdDraw.point(point.x(), point.y());
		}
   }
   public Iterable<Point2D> range(RectHV rect)   // all points that are inside the rectangle (or on the boundary) 
   {
	   if(rect==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   List<Point2D> pointsList = new ArrayList<Point2D>();
	   for(Point2D point : pointsSet)
		{
			if(point.x() >= rect.xmin() && point.x() <= rect.xmax() 
					&& point.y() >= rect.ymin() && point.y() <= rect.ymax())
			{
				pointsList.add(point);
			}
		}
	   Iterable<Point2D> iterable = pointsList;
	   return iterable;
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
	   if(p==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   if(isEmpty())
		   return null;
		Double minimum = Double.MAX_VALUE;
		Point2D resultPoint = null;
		for(Point2D point : pointsSet)
		{
			double distance = point.distanceSquaredTo(p);
			if(distance < minimum)
			{
				resultPoint = point;
				minimum = distance;
			}
		}
		return resultPoint;
   }
   public static void main(String[] args)  // unit testing of the methods (optional) 
   {
	   PointSET pointSET = new PointSET();
	   RectHV rectHV = new RectHV(0,0,5,5);
	   for(int i=0;i<10;i++)
	   {
		   Point2D point = new Point2D(i,i);
		   pointSET.insert(point);
	   }
	   for(Point2D point : pointSET.range(rectHV))
	   {
		   System.out.println(point.toString());
	   }
	   System.out.println(pointSET.nearest(new Point2D(4.3,2.1)).toString());
   }
}