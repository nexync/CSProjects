import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientWordMarkov extends BaseWordMarkov {
	private Map<WordGram,ArrayList<String>> myMap;

	public EfficientWordMarkov() {
		this(2); //Sets the order to three if no other parameter is passed in
	}
	public EfficientWordMarkov(int order){
		super(order); //Sets the order to whatever order is passed in by calling the inherited method
		myMap = new HashMap<>();
	}
	
	@Override
	public void setTraining(String text) {
		myWords = text.split("\\s+"); //Prepares the text by splitting it into a string array by spaces
		myMap.clear(); //Clears the map from previous runs of EfficientWordMarkov
		for(int i=0; i<myWords.length-myOrder; i++) {
			myMap.putIfAbsent(new WordGram(myWords,i,myOrder), new ArrayList<String>()); //Adds a key if the myOrder-WordGram does not exist as a key and points it to an empty ArrayList
			myMap.get(new WordGram(myWords,i,myOrder)).add(myWords[i+myOrder]); //Adds the next string following the myOrder-WordGram to the ArrayList it points to
		}
		myMap.putIfAbsent(new WordGram(myWords, myWords.length-myOrder, myOrder), new ArrayList<String>()); //Adds the last myOrder-WordGram that ends with the string in myWords if it doesn't exist
		myMap.get(new WordGram(myWords, myWords.length-myOrder,myOrder)).add(PSEUDO_EOS); //Adds the PSEUDO_EOS to the ArrayList associated with the last myOrder-WordGram
	}
	
	@Override
	public ArrayList<String> getFollows(WordGram key) {
		if (!myMap.containsKey(key))	throw new NoSuchElementException(key+"not in map"); //Checks if the key is in a map and throws an exception if returns false
		return myMap.get(key); //Returns the ArrayList associated with the specific key
	}	
}
