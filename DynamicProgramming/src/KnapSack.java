import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
	List<Integer> resultList;
	int result;
	
	public Node(List<Integer> list, int result)
	{
		resultList = list;
		this.result = result;
	}
	
	public void ammendList(List<Integer> list)
	{
		resultList.addAll(list);
	}
}

public class KnapSack {
	
	private int arr[][];
	private Map<Integer,Integer> map; 
	private int totalSpace;
	private int number;
			
	public KnapSack(int n, int space)
	{
		arr=new int[n][space];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<space;j++)
			{
				arr[i][j]=Integer.MIN_VALUE;
			}
		}
		totalSpace = space;
		number = n;
		map = new HashMap<>();
		map.put(0,2);
		map.put(1,5);
		map.put(2,6);
		map.put(3,8);
	}
	public Node value(int n,int space, int currentSpace)
	{
		//n is the numberof iteration left
		int temp =currentSpace;
		int result = 0;
		if(n==-1)
		{
			return new Node(new ArrayList<Integer>(),0);
		}
		//System.out.println(n-1);
		currentSpace = currentSpace + map.get(n);
		List<Integer> list1 = new ArrayList<Integer>();
		if(arr[n][space]!=Integer.MIN_VALUE)
		{
			List<Integer> tempList = new ArrayList<>();
			tempList.add(n);
			return new Node(tempList,arr[n][space]);
		}
		if(currentSpace > totalSpace)
		{
			Node temp3 = value(n-1,space, temp);
			result = temp3.result;
			list1.addAll(temp3.resultList);
		}
		else
		{
			Node temp4 = value(n-1,space-temp,currentSpace);
			Node temp5 = value(n-1,space,currentSpace);
			int temp1 = temp4.result;
			int temp6= temp5.result;
			int temp2 = map.get(n);
			if(temp2+temp1>temp6)
			{
				result = temp2+temp1;
				list1.addAll(temp4.resultList);
				list1.add(n);
			}
			else
			{
				list1.addAll(temp5.resultList);
				result = temp6;
			}
			result = Math.max(temp2 + temp1,temp6);
		}
		arr[n][space] = result;
		return new Node(list1,result);
	}
	
	public static void main(String[] args)
	{
		KnapSack test = new KnapSack(4,17);
		Node result = test.value(test.number-1,test.totalSpace-1,0);
		System.out.println("the answer is "+result.result);
		System.out.println(result.resultList);
	}
}