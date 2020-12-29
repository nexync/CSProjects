import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SlowBruteAutocomplete extends BruteAutocomplete {
	/**
	 * The runtime efficiency is O(N*log N) + O(k), which isn't too bad for small N,
	 * but grows much too fast for large N
	 */
	public SlowBruteAutocomplete(String[] terms, double[] weights) {
		super(terms, weights);
	}
	@Override
	public List<Term> topMatches(String prefix, int k) {
		if (k < 0) {
			throw new IllegalArgumentException("Illegal value of k:"+k);
		}
		List<Term> list = new LinkedList<>();
		for (Term t : myTerms) {
			if (t.getWord().startsWith(prefix)) {
				list.add(t);
			}
		}
		Collections.sort(list,Comparator.comparing(Term::getWeight).reversed());
		List<Term> ret = new LinkedList<>();
		for(int i = 0; i<Math.min(k,list.size()); i++) {
			ret.add(list.get(i));
		}
		return ret;
	}
}
