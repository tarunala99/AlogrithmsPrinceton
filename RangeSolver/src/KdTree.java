import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

class TempPoint{
	Double x;
	Double y;
	
	TempPoint(Double xval,Double yval)
	{
		this.x=xval;
		this.y=yval;
	}
}

class Element {
	Point2D data;
	Element left = null;
	Element right = null;
	int height;
	double distance;
	RectHV rect;
	
	void setHeight(int value)
	{
		this.height=value;
	}
	
	void setDistance(Double value)
	{
		this.distance=value;
	}
	
	Element(Point2D value)
	{
		data = value;
	}
	
	void setRect(RectHV value)
	{
		this.rect=value;
	}
}

public class KdTree {
   private Element root;
   private int count=0;
   private Double minimum;
   private Point2D savePoint;
   public KdTree()                               // construct an empty set of points 
   {
	   
   }
   public boolean isEmpty()                      // is the set empty? 
   {
	   if(root==null)
	   {
		   return true;
	   }
	   return false;
   }
   public int size()                         // number of points in the set 
   {
	   return count;
   }
   
   private boolean compareX(Point2D p,Point2D q)
   {
	   if(p.x()>=q.x())
		   return true;
	   else
		   return false;
   }
   
   private boolean compareX(TempPoint p,Point2D q)
   {
	   if(p.x>=q.x())
		   return true;
	   else
		   return false;
   }
   
   private boolean compareX(Point2D p,TempPoint q)
   {
	   if(p.x()>=q.x)
		   return true;
	   else
		   return false;
   }
   
   private boolean compareY(Point2D p,Point2D q)
   {
	   if(p.y()>=q.y())
		   return true;
	   else
		   return false;
   }
   
   private boolean compareY(Point2D p,TempPoint q)
   {
	   if(p.y()>=q.y)
		   return true;
	   else
		   return false;
   }
   
   private boolean compareY(TempPoint p,Point2D q)
   {
	   if(p.y>=q.y())
		   return true;
	   else
		   return false;
   }
   public void insert(Point2D p)
   {
	   if(p==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   compareValue(p, root, 0);
   }
   
   private Element compareValue(Point2D p, Element current, int height) {
	   if(current==null)
	   {
		   current = new Element(p);
		   if(height==0)
		   {
			   root = current;
			   count++;
		   }
		   return current;
	   }
	   //when the same point;just return
	   if(current.data.equals(p))
	   {
		   return null;
	   }
	   Element response = null;
	   if(height%2==0)
	   {
		   if(compareX(current.data,p))
		   {
			   response = compareValue(p,current.left,height+1);
			   if(response!=null && !response.data.equals(current.data))
			   {
				   count++;
				   current.left = response;
			   }
		   }
		   else
		   {
			   response = compareValue(p,current.right,height+1);
			   //redundant code; already checking for equality
			   if(response!=null && !response.data.equals(current.data))
			   {
				   count++;
				   current.right = response;
			   }
		   }
	   }
	   else {
		   if(compareY(current.data,p))
		   {
			   response = compareValue(p,current.left,height+1);
			   if(response!=null && !response.data.equals(current.data))
			   {
				   count++;
				   current.left = response;
			   } 
		   }
		   else
		   {
			   response = compareValue(p,current.right,height+1);
			   if(response!=null && !response.data.equals(current.data))
			   {
				   count++;
				   current.right = response;
			   }
		   }
	   }
	   
	   return null;
}
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
	   if(p==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   Element temp = root;
	   int height = 0;
	   while(temp!=null)
	   {
		   if(temp.data.distanceSquaredTo(p)==0)
			   return true;
		   if(height%2==0)
		   {
			   if(compareX(temp.data,p))
			   {
				   temp=temp.left;
			   }
			   else
			   {
				   temp=temp.right;
			   }
		   }
		   else {
			   if(compareY(temp.data,p))
			   {
				   temp=temp.left;
			   }
			   else
			   {
				   temp=temp.right;
			   }
		   }
		   height++;
	   }
	   return false;
   }
   
   public void draw()                         // draw all points to standard draw 
   {
	   drawNode(root,0,0,null);
		
   }
   
   private void drawNode(Element current,int height,int direction,Point2D parent)
   {
	   if(current==null)
		   return;
	    Point2D tempPoint = current.data;	  
	    StdDraw.setScale();
	   	if(height%2==0)
	   	{
	   		StdDraw.setPenColor(StdDraw.RED);
	   		if(direction==-1)
	   			StdDraw.line(tempPoint.x(), 0, tempPoint.x(), parent.y());
	   		if(direction==1)
	   			StdDraw.line(tempPoint.x(), parent.y(), tempPoint.x(), 1);
	   		if(direction==0)
	   			StdDraw.line(tempPoint.x(), 0, tempPoint.x(), 1);
	   	}
	   	else {
	   		StdDraw.setPenColor(StdDraw.BLUE);
	   		if(direction==-1)
	   			StdDraw.line(0,tempPoint.y(),parent.x(), tempPoint.y());
	   		if(direction==1)
	   			StdDraw.line(parent.x(),tempPoint.y(),1, tempPoint.y());
	   		if(direction==0)
	   			StdDraw.line(0,tempPoint.y(),1, tempPoint.y());
	   	}
	   	StdDraw.setPenColor(StdDraw.BLACK);
	   	StdDraw.point(tempPoint.x(), tempPoint.y());
	   	height++;
	   	if(current.left!=null)
	   	drawNode(current.left,height,-1,current.data);
	   	if(current.right!=null)
		drawNode(current.right,height,1,current.data);
   }
   
   
   private String traversal(Element current)
	{
		if(current==null)
			return null;
		StringBuilder sb = new StringBuilder();
		String temp = traversal(current.left);
		if(temp!=null)
			sb.append(temp);
		sb.append(current.data.toString());
		temp = traversal(current.right);
		if(temp!=null)
			sb.append(temp);
		return sb.toString();
	}
   
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
   {
	   if(root==null)
	   {
		   return null;
	   }
	   if(rect==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   Element temp = root;
	   List<Point2D> pointList = new ArrayList<>();
	   List<Element> queueList = new ArrayList<>();
	   //if the point is inside the rectangle; then add both the nodes to the priority queue
	   TempPoint minPoint = new TempPoint(rect.xmin(),rect.ymin());
	   TempPoint maxPoint = new TempPoint(rect.xmax(),rect.ymax());
	   //the point is inside the rectangle
	   queueList.add(temp);
	   while(!queueList.isEmpty())
	   {
		   Element point = queueList.get(0);
		   int height = queueList.get(0).height;
		   queueList.remove(0);
		   exploreNode(point,height,pointList,minPoint,maxPoint,queueList);		  
	   }
	   Iterable<Point2D> iterable = pointList;
	   return iterable;
   }
   
   private void exploreNode(Element temp, int height, List<Point2D> pointList,TempPoint minPoint,TempPoint maxPoint,List<Element> queueList)
   {
	   //System.out.println("Exploring the point "+temp.data.toString());
	   
	   //the node is inside the rectangle
	   if(height%2==0 && compareX(temp.data,minPoint) && compareX(maxPoint,temp.data)
			   || height%2!=0 && compareY(temp.data,minPoint) && compareY(maxPoint,temp.data))
	   {
		  if(compareX(temp.data,minPoint) && compareX(maxPoint,temp.data) && compareY(temp.data,minPoint) && compareY(maxPoint,temp.data))
		   pointList.add(temp.data);
		  if(temp.left!=null)
		  {
			  
			  height = height+1;
			  temp.left.setHeight(height);
			  queueList.add(temp.left);

		  }
		  if(temp.right!=null)
		  {
			  temp.right.setHeight(height);
			  queueList.add(temp.right);
		  }
		  
		   return;
	   }
	 //the point is to the left
	   if((height%2==0 && compareX(temp.data,maxPoint))
			   || height%2!=0 && compareY(temp.data,maxPoint))
	   {
		  if(temp.left!=null)
		  {
			  height = height+1;
			  temp.left.setHeight(height);
			  queueList.add(temp.left);
		  }
		  return;
	   }
	 //the point is to the right
	   if((height%2==0 && compareX(minPoint,temp.data))
			   || height%2!=0 && compareY(minPoint,temp.data))
	   {
		   if(temp.right!=null)
		   {
			  height = height+1;
			  temp.right.setHeight(height);
			  queueList.add(temp.right);
		   }
		   return;
	   }
   }
   
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
	   if(root==null)
	   {
		   return null;
	   }
	   if(p==null)
	   {
		   throw new java.lang.IllegalArgumentException();
	   }
	   Element temp = root;
	   savePoint = root.data;
	   minimum = Double.POSITIVE_INFINITY;
	   List<Element> queueList = new ArrayList<>();
	   //the point is inside the rectangle
	   RectHV rect = new RectHV(0,0,1,1);
	   temp.setRect(rect);
	   queueList.add(temp);
	   while(!queueList.isEmpty())
	   {
		   Element point = queueList.get(0);
		   int height = point.height;
		   queueList.remove(0);
		   traverseNode(point,height,queueList,p);		  
	   }
	   return savePoint;
   }
   
   private void cleanList(List<Element> queueList, Double distance,Point2D p)
   {
	   for(int i=0;i<queueList.size();i++)
	   {
		   Element current = queueList.get(i);
		   if(current.rect!=null)
		   {
			   RectHV rect = current.rect;
			   
			   if(distance<rect.distanceSquaredTo(p))
			   {
				   queueList.remove(i);
			   }
		   }   
	   }
   }
   
   private void traverseNode(Element point,int height,List<Element> queueList,Point2D p)
   {
	   Double distance = point.data.distanceSquaredTo(p);
	   cleanList(queueList, distance,p);
	   if(distance<minimum)
	   {
		   minimum = distance;
		   savePoint = point.data;
	   }
	   Point2D tempPoint;
	   		height++;
	   	   RectHV parentRect = point.rect;
	   	   RectHV rect = null;
		   if(point.left!=null)
		   {
			   rect = createLeftRect(point, height, parentRect); 
			   tempPoint = point.left.data;
			   point.left.setDistance(tempPoint.distanceSquaredTo(p));
			   point.left.setHeight(height);
			   point.left.setRect(rect);
			   queueList.add(point.left);
		   }
		   if(point.right!=null)
		   {
			   rect = createRightRect(point, height, parentRect);
			   tempPoint = point.right.data;
			   point.right.setDistance(tempPoint.distanceSquaredTo(p));
			   point.right.setHeight(height);
			   point.right.setRect(rect);
			   queueList.add(point.right);
		   }   
   }
	private RectHV createRightRect(Element point, int height, RectHV parentRect) {
		RectHV rect;
		if(height%2!=0)
			{
			   rect = new RectHV(point.data.x(),parentRect.ymin(),parentRect.xmax(),parentRect.ymax());
			}
		   else {
			   rect = new RectHV(parentRect.xmin(),point.data.y(),parentRect.xmax(),parentRect.ymax());
		   }
		return rect;
	}
	private RectHV createLeftRect(Element point, int height, RectHV parentRect) {
		RectHV rect;
		if(height%2!=0)
			{
			   rect = new RectHV(parentRect.xmin(),parentRect.ymin(),point.data.x(),parentRect.ymax());
			}
		   else {
			   rect = new RectHV(parentRect.xmin(),parentRect.ymin(),parentRect.xmax(),point.data.y());
		   }
		return rect;
	}
   
   
   public static void main(String[] args)  {                // unit testing of the methods (optional) 
	   KdTree pointSET = new KdTree();
	   RectHV rectHV = new RectHV(0,0,0.5,0.5);
	   // pointSET.insert(new Point2D(0.372,0.497));
	   //pointSET.insert(new Point2D(0.564,0.413));
	   //pointSET.insert(new Point2D(0.564,0.413));
	   
	   pointSET.insert(new Point2D(1.0 ,0.0));
	   pointSET.insert(new Point2D(1.0, 1.1));
	   pointSET.insert(new Point2D(1.0, 0.0));
	   System.out.println("the size is "+pointSET.size());
		/*
		 * pointSET.insert(new Point2D(0.144, 0.179)); pointSET.insert(new
		 * Point2D(0.083, 0.51)); pointSET.insert(new Point2D(0.32 ,0.708));
		 * pointSET.insert(new Point2D(0.417, 0.362)); pointSET.insert(new
		 * Point2D(0.862, 0.825)); pointSET.insert(new Point2D(0.785, 0.725));
		 * pointSET.insert(new Point2D(0.499 ,0.208));
		 */
	   System.out.println(pointSET.traversal(pointSET.root));
	   System.out.println(pointSET.root.data.toString());
	   System.out.println(pointSET.contains(new Point2D(8,7)));
	   System.out.println(pointSET.contains(new Point2D(4,7)));
	   Iterable<Point2D> iterable = pointSET.range(rectHV);
	   for(Point2D points : iterable)
	   {
		   System.out.println("the inside points are "+points.toString());
	   }
	   System.out.println("the closest point is "+pointSET.nearest(new Point2D(0.284, 0.302)).toString());
	   pointSET.draw();
   }
}
