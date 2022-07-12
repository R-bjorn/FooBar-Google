package Level_1;

public class Solution {
	public static int solution(int[] x, int[] y) {
		// code here
		if(x.length > y.length) {
			int t[] = x; x = y; y = t;
		}
		
		java.util.HashSet<Integer> set = new java.util.HashSet<>();
		for (int i = 0; i < x.length; i++)
            set.add(x[i]);
		
		for (int i = 0; i < y.length; i++) {
            if (!set.contains(y[i]))
                return y[i];
		}
		
		return -1;
        
	}
}
