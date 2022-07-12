package Level_2_1;

public class Solution {
    public static String solution(int[] xs) {
     	if(xs.length == 1) {
     		return String.valueOf(xs[0]);
     	}
    	
    	int negNumber = 0, posNumber = 0;
    	java.math.BigInteger result = new java.math.BigInteger("1");
    	java.util.ArrayList<Integer> neglist = new java.util.ArrayList<Integer>();
    	for(int i : xs) {
    		if(i == 0) {continue;}
    		if(i < 0) {
    			negNumber++;
    			neglist.add(Math.abs(i));
    		}
    		else {
    			result = result.multiply(new java.math.BigInteger(Integer.toString(i)));
    			posNumber++;
    		}
    	}
    	
    	if(posNumber == 0) {
    		return "0";
    	}
    	
    	if(negNumber % 2 == 0) {
    		for(Integer i : neglist)
    			result = result.multiply(new java.math.BigInteger(Integer.toString(i)));
    		
    		return String.valueOf(result);
    	}
    	java.util.Collections.sort(neglist);
    	if(neglist.size() > 1)
    		neglist.remove(0);
    	for(Integer i : neglist) {
    		result = result.multiply(new java.math.BigInteger(Integer.toString(i)));
    	}
    	
    	return String.valueOf(result);
    }
}
