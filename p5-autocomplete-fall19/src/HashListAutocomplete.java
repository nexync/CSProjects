import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {
	private static final int MAX_PREFIX = 10;
	private Map<String, List<Term>> myMap;
	private int mySize;
	
	public HashListAutocomplete(String[] terms, double[] weights) {

		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
	}	
	
	/**
	 * Returns an array containing the k words in the LinkedList 
	 * with the largest weight which match the given prefix,
	 * in descending weight order. If less than k words exist matching the given
	 * prefix (including if no words exist), then the array instead contains all
	 * those words.
	 * 
	 * @param prefix
	 *            - A prefix which all returned words must start with
	 * @param k
	 *            - The (maximum) number of words to be returned
	 * @return An array of the k words with the largest weights among all words
	 *         starting with prefix, in descending weight order. If less than k
	 *         such words exist, return an array containing all those words If
	 *         no such words exist, return an empty array
	 * @throws NullPointerException if prefix is null
	 */
	@Override
	public List<Term> topMatches(String prefix, int k) {
		if(prefix.length() > MAX_PREFIX) {
			prefix = prefix.substring(0,MAX_PREFIX);
		}
		if(myMap.containsKey(prefix)) {
			List<Term> all = myMap.get(prefix);
			List<Term> list = all.subList(0, Math.min(k, all.size()));
			return list;
		}
		return new ArrayList<>();
	}
	
	/**
	 * Initializes the HashList by adding keys associated with each prefix
	 * and adding all terms in a LinkedList that begin with that prefix. Afterwards
	 * sort each array list in descending order by weight of each term.
	 * 
	 * @param terms - the string portion of each term
	 * @param weights - the weight portion of each term
	 */
	@Override
	public void initialize(String[] terms, double[] weights) {
		myMap = new HashMap<>();
		for(int j = 0; j<terms.length; j++) {
			for(int i = 0; i <= Math.min(terms[j].length(), MAX_PREFIX); i++) {
				String prefix = terms[j].substring(0,i);
				myMap.putIfAbsent(prefix, new LinkedList<Term>());
				myMap.get(prefix).add(new Term(terms[j], weights[j])); 
			}
		}
		for(String key: myMap.keySet()) {
			Collections.sort(myMap.get(key),Comparator.comparing(Term::getWeight).reversed());
		}
	}
	@Override
	public int sizeInBytes() {
		for(String key: myMap.keySet()) {
			for(Term t: myMap.get(key)) {
				mySize += BYTES_PER_DOUBLE + BYTES_PER_CHAR*t.getWord().length();					
			}
			mySize += BYTES_PER_CHAR*key.length();
		}
		return mySize;
	}
}
