import java.math.*;
class Sample {
    public int reverse(int x) {
        BigInteger sign = BigInteger.valueOf(1);
        if(x!=0)
        {
            sign = BigInteger.valueOf(Math.abs(x)/x);
        }
        int number = Math.abs(x);
        int remain = number%10;
        int quotient = number /10;
        StringBuilder sb = new StringBuilder();
        sb.append(remain);
        number = quotient;
        while(quotient!=0)
        {
            remain = number%10;
            quotient = number /10;
            number = quotient;
            sb.append(remain);
        }
        return (new BigInteger(sb.toString())).multiply(sign);
    }
}