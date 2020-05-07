import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

class Data{
    Character value;
    int index;
    Data(Character value, int index)
    {
        this.value=value;
        this.index=index;
    }
}

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    
    public static void transform()
    {
        String input = BinaryStdIn.readString();
        StringBuilder sb = new StringBuilder();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(input);
        int result =0;
        int length = input.length();
        for(int i=0;i<circularSuffixArray.length();i++)
        {
            int offset = circularSuffixArray.index(i);
            if(offset==0)
                result = i;
            int index = (length -1 + offset)%length;
            sb.append(input.charAt(index));
        }
        BinaryStdOut.write(result);
        BinaryStdOut.write(sb.toString());
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform()
    {
    	Integer first = BinaryStdIn.readInt(32);
        String string =BinaryStdIn.readString();        
        Data[] charList = new Data[string.length()];
        for(int i=0;i<charList.length;i++)
        {
            Character tempChar = string.charAt(i);
            Data node = new Data(tempChar,i);
            charList[i]=node;
        }   
        Arrays.sort(charList,new CharComparator());
        StringBuilder sb = new StringBuilder();
        sb = new StringBuilder();
        while(sb.length()<charList.length)
        {       
            Data node = charList[first];
            sb.append(node.value);
            first = node.index;
        }
        BinaryStdOut.write(sb.toString());
        BinaryStdOut.flush();
    }

    private static class CharComparator implements Comparator<Data> {
        @Override
        public int compare(Data string1, Data string2) {
            Character temp1 = string1.value;
            Character temp2 = string2.value;
            return temp1.compareTo(temp2);
        }
    }
    
    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args)
    {
    	if (args[0].compareTo("-") == 0)
            transform();
        else if (args[0].compareTo("+") == 0)
        	inverseTransform();
        else
            throw new IllegalArgumentException("Not allowed");
        
    }

}