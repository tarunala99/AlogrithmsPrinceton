import java.util.Arrays;
import java.util.Comparator;

class Node{
	String string;
	int index;
	
	Node(String string, int index)
	{
		this.string = string;
		this.index = index;
	}
}

public class CircularSuffixArray {

	private int length = 0;
	private Integer[] resultList;
	
    private class StringComparator implements Comparator<Integer> {
    	
    	String string;
    	int length;
    	
    	public StringComparator(String string1)
    	{
    		this.string = string1;
    		this.length=string1.length();
    	}
    	
        @Override
        public int compare(Integer offset1,Integer offset2) {
        	int count = 0;
        	while(count<string.length())
        	{
        		Character char1 = string.charAt((count+offset1)%length);
        		Character char2 = string.charAt((count+offset2)%length);
        		int response = char1.compareTo(char2);
        		if(response!=0)
        		{
        			return response;
        		}
        		count++;
        	}
        	return 0;
        }
    }
    // circular suffix array of s
    public CircularSuffixArray(String s)
    {
    	if(s==null)
    		throw new IllegalArgumentException();
    	length = s.length();
    	if(length==0)
    		return;
    	resultList = new Integer[length];
    	for(int i=0;i<length;i++)
    	{
    		resultList[i]=i;
    	}
    	Arrays.sort(resultList,new StringComparator(s));
    }

    // length of s
    public int length()
    {
    	return length;
    }

    // returns index of ith sorted suffix
    public int index(int i)
    {
    	if(i<0 || i>=length ||length==0)
    		throw new IllegalArgumentException();
    	return resultList[i];
    }

    // unit testing (required)
    public static void main(String[] args)
    {
    	CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
    	for(Integer string : circularSuffixArray.resultList)
    	{
    		System.out.println(string);
    	}
    }

}