package Level_2_0;

import java.util.Arrays;

public class Main {
	public static void main(String args[]) {
		String[] x = {"1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"};		
		System.out.println(Arrays.toString(Solution.solution(x)));	
		
		String[] x1 = {"1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"};
		System.out.println(Arrays.toString(Solution.solution(x1)));	
	}
}