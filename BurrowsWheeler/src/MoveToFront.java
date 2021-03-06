import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private List<Character> initList()
    {
        List<Character> charList = new LinkedList<Character>();
        for(int i=0;i<256;i++)
        {
            charList.add((char) i);
        }
        return charList;
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        MoveToFront moveToFront = new MoveToFront();
        List<Character> charList = moveToFront.initList();
        while(!BinaryStdIn.isEmpty())
        {
            Character temp =  BinaryStdIn.readChar();
            int index = charList.indexOf(temp);
            charList.remove(index);
            charList.add(0, temp);
            BinaryStdOut.write((char)index);
        }   
        BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        MoveToFront moveToFront = new MoveToFront();
        List<Character> charList = moveToFront.initList();
        while(!BinaryStdIn.isEmpty())
        {
            Character temp12 =  BinaryStdIn.readChar();
            int index =  (int) temp12;
            char temp14 = charList.get(index);
            int result = (int)temp14;
            BinaryStdOut.write((char)result);
            charList.remove(index);
            charList.add(0, temp14);
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args)
    {
        if (args[0].compareTo("-") == 0)
            encode();
        else if (args[0].compareTo("+") == 0)
            decode();
        else
            throw new IllegalArgumentException("Not allowed");
    }

}