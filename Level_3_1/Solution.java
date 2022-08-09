package Level_3_0;

public class Solution {
    public static int solution(int start, int length) {
//    	if(start < 0 || length <= 0) { return 0;}
//    	if(((length*10) + (start-1)) > 2000000000) { return 0; }
    	if(length++ == 0) { return 0; }
    	
    	int bunnyLen = length - 1, j = 0, i = 0;
    	java.math.BigInteger checksum = new java.math.BigInteger("0");
    	boolean toggle = true;
    	
    	while(j <= bunnyLen) {
    		if(i == bunnyLen + 1) {
    			System.out.println();
    			toggle = true;
    			i = 0; j++;
    			if(j == bunnyLen)
    				break;
    		}
    		if(i++ == length-1) {
    			toggle = false;
    			System.out.printf("    \\");
    			length--;
    		}else {
    			System.out.printf("%15d",start);
    			start++;
    			if(toggle)
    				checksum = new java.math.BigInteger( String.valueOf(Integer.parseInt(checksum.toString()) ^ (start - 1)));
    		}	
    	}
    	
    	return Integer.parseInt(checksum.toString());
    }
    
    
    public static void main(String[] args) {
//    	java.util.Random rand = new java.util.Random(); 
//    	int j;
//    	for(int i = 0 ; i < 1000 ; i++) {
//    		j = rand.nextInt(20000000); 
//    		System.out.println("i : "+ j +", j : " + i + " XOR->" + solution(j, i));
//    	}
    	System.out.println(solution(2000000000, 2));
		
	}
}