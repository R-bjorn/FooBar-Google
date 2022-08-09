interface TwoArgInterface {
    public void operation(int a[], int index);
}


public class Solution {
	static int freeKeyIndex = 0;

    static void myCombinations(int n, int destination[], int destStartIdx, int sourceStartIdx, TwoArgInterface lambda ) {
    	if (destStartIdx == destination.length) {
    		lambda.operation(destination, freeKeyIndex);
    		--freeKeyIndex;
    		return;
    	}
    	
    	for (int sourceIdx = sourceStartIdx; sourceIdx < n; ++sourceIdx) {
    		destination[destStartIdx] = sourceIdx;
    		myCombinations(n, destination, destStartIdx+1, sourceIdx+1, lambda);
    	}
    }
    
    static void makeCombinations(int n, int r, TwoArgInterface lambda)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];
        myCombinations(n, data, 0, 0, lambda);
    }
 
    public static int[][] solution(int num_buns, int num_required) {
    	int unique_keys = binomialInt(num_buns, num_required - 1);
    	boolean bunnyKeyArray[][] = new boolean[num_buns][unique_keys];
    	freeKeyIndex = unique_keys - 1;
    	
    	makeCombinations(num_buns, num_required-1, (combination, keyIdx) -> {
    		for (int bunnyIdx = 0; bunnyIdx < num_buns; ++bunnyIdx) {
    			bunnyKeyArray[bunnyIdx][keyIdx] = true;
    		}
    		for (int idx = 0; idx < combination.length; ++idx) {
    			bunnyKeyArray[combination[idx]][keyIdx] = false;
    		}
    	});
		
		int keysPerBunny = unique_keys * (num_buns - num_required + 1) / num_buns;
		int result[][] = new int[num_buns][keysPerBunny];
		
		for (int bunnyIdx = 0; bunnyIdx < num_buns; ++bunnyIdx) {
			int bunnyKeyIdx = 0;
			for (int keyIdx = 0; keyIdx < unique_keys; ++keyIdx) {
				if(bunnyKeyArray[bunnyIdx][keyIdx]) {
					result[bunnyIdx][bunnyKeyIdx] = keyIdx;
					bunnyKeyIdx++;
				}
			}
		}
		
		return result;
		
    }
    
    private static int binomialInt(int n, int k) {
        if (k > n - k)
            k = n - k;
 
        int binom = 1;
        for (int i = 1; i <= k; i++)
            binom = binom * (n + 1 - i) / i;
        return binom;
    }
    
    public static void main (String[] args) {
        solution(2, 1);
    }
}