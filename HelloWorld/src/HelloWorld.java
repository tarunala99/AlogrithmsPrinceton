import java.util.Arrays;

public class HelloWorld {

	public static void main(String[] args)
	{
		int[] temp = {1,4,2,7,5,6};
		Arrays.sort(temp);
		for(int i : temp)
		{
			System.out.println(i);
		}
	}
}
