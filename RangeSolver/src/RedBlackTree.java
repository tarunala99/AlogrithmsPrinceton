class Node {
	int data;
	boolean red = false;
	Node left = null;
	Node right = null;
	Node parent = null;
	
	Node(int value)
	{
		data = value;
	}
	
	public boolean isRed(Node node)
	{
		if(node==null)
			return false;
		return node.red==true;
	}
}
public class RedBlackTree {
	
	Node root = null;
	
	public RedBlackTree()
	{
		
	}
	
	public Node searchValue(int value)
	{
		Node temp = root;
		Node prevTemp = null;
		while(temp!=null)
		{
			prevTemp = temp;
			if(value<temp.data)
			{
				temp = temp.left;
			}
			else {
				temp = temp.right;
			}
		}
		return prevTemp;
	}
	
	public String traversal(Node current)
	{
		if(current==null)
			return null;
		StringBuilder sb = new StringBuilder();
		String temp = traversal(current.left);
		if(temp!=null)
			sb.append(temp);
		sb.append((char)(current.data));
		temp = traversal(current.right);
		if(temp!=null)
			sb.append(temp);
		return sb.toString();
	}
	
	
	public void insertValue(int value)
	{
		Node currentNode = null;
		if(root==null)
		{
			root = new Node(value);
			return;
		}
		else {
			currentNode = searchValue(value);
			Node tempNode = new Node(value);
			tempNode.red=true;
			if(currentNode.data>value)
			{
				currentNode.left = tempNode;
			}
			else {
				currentNode.right=tempNode;
			}
		}
		if(currentNode.isRed(currentNode.right) && !currentNode.isRed(currentNode.left)) {
			
		}
		if(currentNode.isRed(currentNode.right) && !currentNode.isRed(currentNode.left)) {
			
		}
		
		return;
		
	}
	
	public static void main(String[] args)
	{
		RedBlackTree redBlackTree = new RedBlackTree();
		redBlackTree.insertValue('S');
		redBlackTree.insertValue('E');
		redBlackTree.insertValue('A');
		redBlackTree.insertValue('R');
		redBlackTree.insertValue('C');
		System.out.println(redBlackTree.traversal(redBlackTree.root));
		
	}

}
