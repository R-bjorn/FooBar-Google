> <div id="top"></div>

# Free the Bunny Workers

##  Description

You need to free the bunny workers before Commander Lambda's space station explodes! Unfortunately, the Commander was very careful with the highest-value workers -- they all work in separate, maximum-security work rooms. The rooms are opened by putting keys into each console, then pressing the open button on each console simultaneously. When the open button is pressed, each key opens its corresponding lock on the work room. So, the union of the keys in all of the consoles must be all of the keys. The scheme may require multiple copies of one key given to different minions.

<details><summary>Details about this assignment</summary>
    
> The consoles are far enough apart that a separate minion is needed for each one. Fortunately, you have already relieved some bunnies to aid you - and even better, you were able to steal the keys while you were working as Commander Lambda's assistant. The problem is, you don't know which keys to use at which consoles. The consoles are programmed to know which keys each minion had, to prevent someone from just stealing all of the keys and using them blindly. There are signs by the consoles saying how many minions had some keys for the set of consoles. You suspect that Commander Lambda has a systematic way to decide which keys to give to each minion such that they could use the consoles.
> 
> You need to figure out the scheme that Commander Lambda used to distribute the keys. You know how many minions had keys, and how many consoles are by each work room. You know that Command Lambda wouldn't issue more keys than necessary (beyond what the key distribution scheme requires), and that you need as many bunnies with keys as there are consoles to open the work room.
> 
> Given the number of bunnies available and the number of locks required to open a work room, write a function solution(num_buns, num_required) which returns a specification of how to distribute the keys such that any num_required bunnies can open the locks, but no group of (num_required - 1) bunnies can.
> 
> Each lock is numbered starting from 0. The keys are numbered the same as the lock they open (so for a duplicate key, the number will repeat, since it opens the same lock). For a given bunny, the keys they get is represented as a sorted list of the numbers for the keys. To cover all of the bunnies, the final solution is represented by a sorted list of each individual bunny's list of keys. Find the lexicographically least such key distribution - that is, the first bunny should have keys sequentially starting from 0.
> 
> num_buns will always be between 1 and 9, and num_required will always be between 0 and 9 (both inclusive). For example, if you had 3 bunnies and required only 1 of them to open the cell, you would give each bunny the same key such that any of the 3 of them would be able to open it, like so: [ [0], [0], [0], ] If you had 2 bunnies and required both of them to open the cell, they would receive different keys (otherwise they wouldn't both actually be required), and your solution would be as follows: [ [0], [1], ] Finally, if you had 3 bunnies and required 2 of them to open the cell, then any 2 of the 3 bunnies should have all of the keys necessary to open the cell, but no single bunny would be able to do it. Thus, the solution would be: [ [0, 1], [0, 2], [1, 2], ]
> 
 <a href="#top">(Back to top)</a>
</details> 

## Test Cases

Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

### Python cases
### Test Case 1

Inputs:

    (int) a = 2
    (int) b = 1

Output:

    (int list) ans = [[0], [0]]
    
### Test Case 2

Inputs:

    (int) a = 4
    (int) b = 4

Output:

    (int list) ans = [[0], [1], [2], [3]]
    
### Test Case 3

Inputs:

    (int) a = 5
    (int) b = 3

Output:

    (int list) ans = [[0, 1, 2, 3, 4, 5], [0, 1, 2, 6, 7, 8], [0, 3, 4, 6, 7, 9], [1, 3, 5, 6, 8, 9], [2, 4, 5, 7, 8, 9]]

<a align="center" href="#top">(Back to top)</a>

## Languages

- To provide a Java solution, edit Solution.java
- To provide a Python solution, edit solution.py

## Solution 

```

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

```

<a align="center" href="#top">(Back to top)</a>
