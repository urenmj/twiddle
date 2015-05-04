package au.id.michaeluren.ass3.util;

import java.util.Collection;

/**
 * wrapped as Command pattern, to highlight that in a more functional world 
 * (Scala, Java8, etc), this would be a first-class function instead 
 */
public class SumFunction implements Function1<Collection<Integer>, Integer> {

	public Integer execute(Collection<Integer> values) {
		int sum = 0;
		for(int i: values) {
			sum += i;
		}
		return sum;
	}
	
}
