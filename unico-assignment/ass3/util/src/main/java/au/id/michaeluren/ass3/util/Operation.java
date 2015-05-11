package au.id.michaeluren.ass3.util;

public class Operation {
	
	public static int gcd(int x, int y) {
		// iterative version to avoid stack overflow with recursive version
		// this is equivalent to a tail-recursive call in something like Scala
		int temp;
		while (y != 0) {
			temp = x;
			x = y;
			y = temp % y;
		}
		return x;
	}
	
}
