import java.util.Iterator;

/**
 * @author Kenneth Marenco and Jeffrey Cheng
 */

public class LinkStrand implements IDnaStrand {
	private class Node {
		String info;
		Node next;
	    public Node(String s) {
	    	info = s;
	    	next = null;
	    }
	    public Node(String s, Node n) {
	    	info = s;
	    	next = n;
	    }
	}
	//private String myInfo;
	private Node myFirst, myLast;
	private long mySize;
    private int myAppends;
    private Node myCurrent;
    private int myCurrentIndex;

	/**
	 * Create a strand representing s. No error checking is done to see if s
	 * represents valid genomic/DNA data.
	 *
	 * @param s is the source of cgat data for this strand
	 */
    public LinkStrand(){
        this("");

    }


	/**
	 * Initialize this strand so that it represents the value of source. No
	 * error checking is performed.
	 *
	 * @param //s is the source of this enzyme to initialize
	 * 			it then runs the initialize method to set the
	 * 			private variables
	 */
    public LinkStrand(String s) {
        initialize(s);

    }

	/**
	 *
	 * @return
	 */
	@Override
    public long size() {
        //return myInfo.length();
    	return mySize;
    }

	/**
	 *  Takes the info of each node and adds them to StringBuilder.
	 *  It then reiterates for each node until null.
	 * @return String which contains all the info of each node
	 * 			in order from myFirst to myLast
	 */
    @Override
    public String toString() {
    	//return myInfo.toString();
    	StringBuilder sb = new StringBuilder();
    	Node iterate = myFirst;
    	if(iterate == null)	return null;
    	while (iterate != null) {
    		sb.append(iterate.info);
    		if(iterate.next != null)	iterate = iterate.next;
    		else	break;
    	}
        return sb.toString();
    }

	/**
	 *
	 * @param source is the string from the param LinkStrand
	 *               it takes this to create a new Node which
	 *               is used to set several private variables
	 *               which some are (such as myFirst) invariants
	 */
	@Override
    public void initialize(String source) {
        //myInfo = new String(source);
        myLast = new Node(source);
        myFirst = myLast;
        myCurrent = myFirst;
        myCurrentIndex = 0;
        myAppends = 0;
        mySize = source.length();
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

	/**
	 *
	 * @param dna is the string appended to this strand
	 *        It then updates the private variables and
	 *        maintains the invariants
	 * @return this which is an IDnaStand object which is
	 * 			our updated LinkStrand
	 */
	@Override
    public IDnaStrand append(String dna) {
        //myInfo = myInfo + dna;
        Node temp = new Node(dna);
        myLast.next = temp;
        myLast = myLast.next;
        mySize += dna.length();
        myAppends++;
        return this;
    }

	/**
	 *
	 * @return Returns a new LinkStrand which has the same number of nodes 
	 *			as the original LinkStrand but with the order of the nodes 
	 *			and every string inside the node reversed.
	 */
	@Override
    public IDnaStrand reverse() {
		/*StringBuilder copy = new StringBuilder(myInfo);
		copy.reverse();
		LinkStrand ss = new LinkStrand(copy.toString());
		return ss;*/
    	Node iterate = myFirst;
    	Node build = new Node(new StringBuilder(myFirst.info).reverse().toString());
    	while (iterate.next != null) {
    		build = new Node(new StringBuilder(iterate.next.info).reverse().toString(),build);
    		iterate = iterate.next;
    	}
    	Node iterate2 = build;
    	LinkStrand ret = new LinkStrand(iterate2.info);
    	while(iterate2.next!= null) {
    		ret.append(iterate2.next.info);
    		iterate2=iterate2.next;
    	}
    	return ret;
    }
	/**
	 * @return Returns how many nodes there are in the LinkStrand
	 */
    @Override
    public int getAppendCount() {
        return myAppends;
    }
    
    /**
     * @param An index as an integer that a character is searched for at.
     * @return Returns the character at a given index, throws an exception if index is invalide
     */
    @Override
    public char charAt(int index) {
        //return myInfo.charAt(index);
    	if(index >= mySize || index < 0) {
    		throw new IndexOutOfBoundsException();
    	}
    	else {
    		Node iterate;
    		int count;
    		int dex;
    		if(index < myCurrentIndex) {
    			iterate = myFirst;
    			count = 0;
    			dex = index;
    		}
    		else {
	    		iterate = myCurrent;
	    		count = myCurrentIndex;
	    		dex = index - count;
	    	}
	    	while(iterate != null) {
	    		if(dex >= iterate.info.length()) {
	    			count += iterate.info.length();
	    			dex -= iterate.info.length();
	    			iterate = iterate.next;
	    		}
	    		else {
	    			myCurrentIndex = count;
	    			myCurrent = iterate;
	    			return iterate.info.charAt(dex);
	    		}
	    	}
	    	return 'a';
    	}
    }
 
}
