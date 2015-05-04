package au.id.michaeluren.ass3.util;

/**
 * wrapped as Command pattern, to highlight that in a more functional world 
 * (Scala, Java8, etc), this would be a first-class function instead 
 */
public class GcdFunction implements Function2<Integer, Integer> {

	public Integer execute(Integer x, Integer y) {
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
