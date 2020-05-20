class Node{
	char charVal;
	int value;
	Node nodeList[];
	
	Node()
	{
		nodeList = new Node[26];
	}
}

class Result{
	String contains ="NO VALUE";
	Node nodeValue;
}
public class Dictionary {
	Node root;
	Node currentNode;
	
	public Dictionary()
	{
		root = new Node();
		root.value=0;//change the value if required
		currentNode = root;
	}
	
	public void addString(String currentString, int pointScore)
	{
		Node temp = root;
		for(int i=0;i<currentString.length();i++)
		{
			Character x = currentString.charAt(i);
			int index = x-65;
			Node newNode;
			if(temp.nodeList[index]==null)
			{
				newNode = new Node();
				temp.nodeList[index] = newNode;
				newNode.charVal = x;
			}
			else {
				newNode = temp.nodeList[index];
			}
			temp = newNode;
		}
		temp.value=pointScore;
	}
	
	public boolean equalsString(String currentString)
	{
		//Do it for each of the strings
		//needs to be reset if the other doesContain method is called
		currentNode = root;
		Result result = containsString(currentString);
		if(result.contains=="VALUES ARE EQUAL")
			return true;
		return false;
	}
	
	public boolean doesContain(String currentString)
	{
		currentNode = root;
		Result result = containsString(currentString);
		if(result.contains=="DOES CONTAIN VALUE")
			return true;
		return false;
	}
	
	private Result containsString(String currentString)
	{
		Node temp = currentNode;
		Result result = new Result();
		for(int i=0;i<currentString.length();i++)
		{
			Character x = currentString.charAt(i);
			int index = x-65;
			//has to check the index; not the current value
			Node newNode = temp.nodeList[index];
			//breaks when an equals value is found
			if(newNode!=null && newNode.charVal==x && newNode.value>0 && currentString.length()-1 == i)
			{
				result.contains = "VALUES ARE EQUAL";
				break;
			}
			//breaks when the dictionary runs out
			//breaks when the value is not equal
			if(newNode == null || newNode.charVal != x)
			{
				result.contains = "DOES NOT CONTAIN";
				break;
			}
			temp = newNode;
		}
		if(!result.contains.equals("DOES NOT CONTAIN") 
			&& !result.contains.equals("VALUES ARE EQUAL"))
		{
			result.contains = "DOES CONTAIN VALUE";
			result.nodeValue = temp;
		}
		return result;
	}
	
	public static void main(String[] args)
	{
		Dictionary dictionary = new Dictionary();
		dictionary.addString("AT",10);
		dictionary.addString("ATOM",45);
		Boolean value = dictionary.doesContain("AT");
		System.out.println(value);
		value = dictionary.equalsString("AT");
		System.out.println(value);
		
	}
}
