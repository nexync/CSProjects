import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;
	
	public EfficientMarkov() {
		this(3); //Sets the order to three if no other parameter is passed in
	}
	public EfficientMarkov(int order) {
		super(order); //Sets the order to whatever order is passed in by calling the inherited method
		myMap = new HashMap<>();
	}
	
	@Override
	public void setTraining(String text) {
		super.setTraining(text); //Sets the text to the text passed in
		myMap.clear(); //Clears the map from previous runs of EfficientMarkov
		for(int i=0; i<text.length()-myOrder; i++) {
			myMap.putIfAbsent(text.substring(i, i+myOrder), new ArrayList<String>()); //Adds a key if the myOrder-letter combination does not exist as a key and points it to an empty ArrayList
			myMap.get(text.substring(i,i+myOrder)).add(String.valueOf(text.charAt(i+myOrder))); //Adds the string value of the character following the myOrder-letter combination to the ArrayList it points to
		}
		myMap.putIfAbsent(text.substring(text.length()-myOrder, text.length()), new ArrayList<String>()); //Adds the last myOrder-letter combination that ends with the last character in myText
		myMap.get(text.substring(text.length()-myOrder,text.length())).add(PSEUDO_EOS); //Adds the PSEUDO_EOS to the ArrayList associated with the last myOrder-letter combination

	}
	
	@Override
	public ArrayList<String> getFollows(String key) {
		if (!myMap.containsKey(key))	throw new NoSuchElementException(key+"not in map"); //Checks if the key is in a map and throws an exception if returns false
		return myMap.get(key); // Returns the ArrayList associated with the specific key
	}
}	
