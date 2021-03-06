import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

   private Picture image;
   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture)
   {
	   if(picture==null)
		   throw new IllegalArgumentException();
	   image = new Picture(picture);
   }

   // current picture
   public Picture picture()
   {
	   return image;
   }

   // width of current picture
   public int width()
   {
	   return image.width();
   }

   // height of current picture
   public int height()
   {
	   return image.height();
   }

   // energy of pixel at column x and row y
   public double energy(int x, int y)
   {
	   if(x>=width() || y>=height() ||x<0||y<0)
		   throw new IllegalArgumentException();
	   if(x==0 || y==0 || x==width()-1 || y==height()-1)
	   {
		   return 1000;
	   }
	   Color colour1 = image.get(x-1, y);
	   Color colour2 = image.get(x+1, y);
	   double xdistance = calculateDistance(colour1,colour2);
	   colour1 = image.get(x, y-1);
	   colour2 = image.get(x, y+1);
	   double ydistance = calculateDistance(colour1,colour2);
	   return Math.sqrt(xdistance+ydistance);
   }
   
   private double calculateDistance(Color colour1,Color colour2)
   {
	   int redDistance = colour1.getRed()-colour2.getRed();
	   int greenDistance = colour1.getBlue()-colour2.getBlue();
	   int blueDistance = colour1.getGreen()-colour2.getGreen();
	   double distance = Math.pow(redDistance, 2)+Math.pow(greenDistance, 2)+Math.pow(blueDistance, 2);
	   return distance;	
   }

   // sequence of indices for horizontal seam
   public int[] findHorizontalSeam()
   {
	   double[][] distanceArray = createDistanceArray();
	   int[][] parentArray = new int[height()][width()];
	   //reversing the order to ensure that all the prev data are filled out
	   for(int j=1;j<width();j++)
       {
		   for(int i=1;i<height();i++)
    	   {
    		   double energy1 = distanceArray[i-1][j-1];
    		   double energy2 = distanceArray[i][j-1];
    		   double energy3 = Double.POSITIVE_INFINITY;
    		   if(i+1<height())
    			   energy3 = distanceArray[i+1][j-1];
    		   double tempEnergy = 0;
    		   if(j!=1)
    		   {
    			   tempEnergy = distanceArray[calcEnergy(energy1,energy2,energy3,i)][j-1];
    			   parentArray[i][j]=calcEnergy(energy1,energy2,energy3,i);
    		   }
    		   else
    		   {
    			   tempEnergy = 1000;
    			   parentArray[i][j]=i;
    		   }
    		   distanceArray[i][j]= tempEnergy+energy(j,i);
    	   }
       }		
		/*
		 * for(int i=0;i<height();i++) { for(int j=0;j<width();j++) {
		 * System.out.print(distanceArray[i][j]+"\t"); } System.out.println(); } for(int
		 * i=0;i<height();i++) { for(int j=0;j<width();j++) {
		 * System.out.print(parentArray[i][j]+"\t"); } System.out.println(); }
		 */
		 
		 
	   double minimum = distanceArray[0][width()-1];
	   int minIndex = 0;
	 //find the minimum index in the last row
	   for(int j=1;j<height();j++)
	   {
		   double tempDistance = distanceArray[j][width()-1];
		   if(tempDistance<minimum)
		   {
			   
			   minimum = tempDistance;
			   minIndex = j;
		   }
	   }
	   int[] resultArray = new int[width()];
	   int width = width()-1;
	   //build the stack of results
	   while(minIndex!=0)
	   {
		   resultArray[width] = minIndex;
		   minIndex = parentArray[minIndex][width];
		   width = width-1;
	   }
	   return resultArray;
   }
   
   private double[][] createDistanceArray()
   {
	 //reverse the sizes to make them normal
	   double[][] distanceArray = new double[height()][width()];
	   for(int i=0;i<height();i++)
       {
    	   for(int j=0;j<width();j++)
    	   {
    		   distanceArray[i][j]=Double.POSITIVE_INFINITY;
    	   }
       }
	   return distanceArray;
   }

   // sequence of indices for vertical seam
   public int[] findVerticalSeam()
   {
	   double[][] distanceArray = createDistanceArray();
	   int[][] parentArray = new int[height()][width()];
	   for(int i=1;i<height();i++)
       {
    	   for(int j=1;j<width();j++)
    	   {
    		   double energy1 = distanceArray[i-1][j-1];
    		   double energy2 = distanceArray[i-1][j];
    		   double energy3 = Double.POSITIVE_INFINITY;
    		   if(j+1<width())
    			   energy3 = distanceArray[i-1][j+1];
    		   double tempEnergy = 0;
    		   if(i!=1)
    		   {
    			   tempEnergy = distanceArray[i-1][calcEnergy(energy1,energy2,energy3,j)];
    			   parentArray[i][j]=calcEnergy(energy1,energy2,energy3,j);
    		   }
    		   else
    		   {
    			   tempEnergy = 1000;
    			   parentArray[i][j]=j;
    		   }
    		   distanceArray[i][j]= tempEnergy+energy(j,i);
    	   }
       } 
		 
	   double minimum = distanceArray[height()-1][0];
	   int minIndex = 0;
	 //find the minimum index in the last row
	   for(int j=1;j<width();j++)
	   {
		   double tempDistance = distanceArray[height()-1][j];
		   if(tempDistance<minimum)
		   {
			   minimum = tempDistance;
			   minIndex = j;
		   }
	   }
	   int[] resultArray = new int[height()];
	   int height = height()-1;
	   //build the stack of results
	   while(minIndex!=0)
	   {
		   resultArray[height] = minIndex;
		   minIndex = parentArray[height][minIndex];
		   height = height-1;
	   }
	   return resultArray;
   }
   
   private int calcEnergy(Double energy1,Double energy2,Double energy3,int j)
   {
	   if(energy1<=energy2 && energy1<=energy3)
	   {
		   return j-1;
	   }
	   if(energy2<=energy1 && energy2<=energy3)
	   {
		   return j;
	   }
	   if(energy3<=energy2 && energy3<=energy1)
	   {
		   return j+1;
	   }
	   return 0;
   }

   private boolean compareVal(int temp,int prev)
   {
	   int diff = Math.abs(temp-prev);
	   if(diff>=2)
	   {
		   return true;
	   }
	   return false;
   }
   // remove horizontal seam from current picture
   //not complete yet; requires to be adjusted to horizontal
   public void removeHorizontalSeam(int[] seam)
   {
	   if(seam==null || seam.length!=image.width())
		   throw new IllegalArgumentException();
	   Picture clonePicture = new Picture(width(),height()-1);
	   int count = 0;
	   int prev = seam[0];
	   for(int i=0;i<width();i++)
       {
		   int temp =seam[count];
		   if(compareVal(temp,prev) || temp>=height()||temp<0)
		   {
			   throw new IllegalArgumentException();
		   }
		   prev = temp;
    	   for(int j=-1;j<height()-2;)
    	   {
    		   //j is the width element
    		   j++;
    		   Color color = null;
    		   if(j>=temp)
    			   color = image.get(i,j+1);	
    		   else
    			   color = image.get(i,j);
    		   clonePicture.set(i,j, color);   		   
    		   
    	   }
    	   count++;
       }
	   image = clonePicture;
   }

   // remove vertical seam from current picture
   public void removeVerticalSeam(int[] seam)
   {
	   if(seam==null ||seam.length!=image.height())
		   throw new IllegalArgumentException();
	   Picture clonePicture = new Picture(width()-1,height());
	   int count = 0;
	   int prev = seam[0];
	   for(int i=0;i<height();i++)
       {
		   int temp =seam[count];
		   if(compareVal(temp,prev) || temp>=width() ||temp<0)
		   {
			   throw new IllegalArgumentException();
		   }
		   prev = temp;
    	   for(int j=-1;j<width()-2;)
    	   {
    		   //j is the width element
    		   j++;
    		   Color color = null;
    		   if(j>=temp)
    			   color = image.get(j+1, i);	
    		   else
    			   color = image.get(j, i);
    		   clonePicture.set(j,i, color);   		   
    		   
    	   }
    	   count++;
       }
	   image = clonePicture;
   }

   //  unit testing (optional)
   public static void main(String[] args)
   {	   
       Picture picture = new Picture("stripes.png");
       System.out.println("The width is "+picture.width());
       SeamCarver seamCarver = new SeamCarver(picture);
       int[] resultArray = seamCarver.findHorizontalSeam();
       System.out.println(Arrays.toString(resultArray));
       int[] resultArray1 = seamCarver.findVerticalSeam();
       double totalEnergy = 0;
	   for(int j=0;j<picture.height();j++) 
	   {
		   totalEnergy = totalEnergy + seamCarver.energy(resultArray1[j], j); 
		   System.out.println(totalEnergy+" the columns is "+resultArray1[j]+" the row is "+j);
    	}
	   for(int i=0;i<picture.height();i++) 
       {
    	   for(int j=0;j<picture.width();j++) 
    	   {
	    	System.out.print(seamCarver.energy(j, i)+"\t"); 
	    	}
    	   System.out.println(); 
    	}
	   	System.out.println("The total energy is "+totalEnergy);
       seamCarver.removeVerticalSeam(resultArray1);
       System.out.println(Arrays.toString(resultArray1));
       picture=seamCarver.picture();
       
       
		/*
		 * for(int i=0;i<picture.height();i++) { for(int j=0;j<picture.width();j++) {
		 * //System.out.print(picture.get(i,j).getRed());
		 * 
		 */
   }

}
