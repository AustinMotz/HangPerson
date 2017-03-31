package Bang;
import java.util.*;

public class SearchEngine {
	private String myURL;
    // holds the name for the "url" (file name in our Bang search engine)

	private HashMap<String, ArrayList<String>> myIndex;
    // holds the word index
	
	/*  Saves url in myUrl
	 *  Initializes myIndex to an empty HashMap with initial capacity of 20,000
	 *  (note - this constructor doesn't load the file, that's done in the Bang class
	*/
	public SearchEngine(String url) {
		myURL = url;
		myIndex = new HashMap<String, ArrayList<String>>();
	}
	
	/*  Called from Bang to display the name of the file in which hits were found
	 *  Returns myUrl
	*/
	public String getURL() {
		return myURL;
	}

	/*  Extracts all words from line, and for each word, adds line to its list of lines in myIndex
	 *  Obtains a set of all words in line by calling a private method parseWords(line) described below.
	 *  Use an ArrayList<String> object to represent a list of lines associated with a word.
	*/
	public void add(String line) {
		String[] words = parseWords(line);
		for(String elem: words) {
			ArrayList<String> lines = new ArrayList<String>();
			if(myIndex.containsKey(elem)) {
				lines = myIndex.get(elem);
			}
			lines.add(line);
			myIndex.put(elem, lines);
		}
	}

	/*  Returns the list of lines associated with word in myIndex
	*/
	public ArrayList<String> getHits(String word) {
		return myIndex.get(word);
	}
	
	/*  Extracts all the words from line... try line.split("\\W+") to split a string into words
	 *         and return in an array
	 *  Remove empty words and convert each word to lowercase
	 *
	 *  Returns an array of all words in line
	*/
	private String[] parseWords(String line) {
		return line.toLowerCase().split("\\W+");
	}
}
