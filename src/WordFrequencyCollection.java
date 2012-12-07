import java.util.ArrayList;
import java.util.Formatter;

/**
 * This class will be maintaining a collection of words from a text file It will
 * also be keeping track of the number of times the word is seen.
 * 
 * @author James Celona
 * @author Joe Young
 */
public class WordFrequencyCollection extends ArrayList<WordFrequency> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int indexOf(String s) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(s))
				return i;
		}
		return -1;
	}

	/**
	 * 
	 * @param s
	 *            the word attempting to be added to the collection
	 * @return boolean if the word exists already in the collection.
	 */
	private boolean contains(String s) {
		if (size() <= 0)
			return false;
		for (int i = 0; i < size(); i++) {
			if (get(i).equals(s))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param word the word being added to the collection
	 * @return whether or not the word was sorted
	 */
	public boolean add(String word) {
		if (word == null)
			return false;
		if (contains(word)) {
			System.out.println("Incrementing Frequency of: "+word);
			get(indexOf(word)).incrementFrequency();
			sort();
			return true;
		}
		else{
		return addSorted(word);
		}
	}
	/**
	 * @return boolean if the word is successfully added to the collection
	 */
	public boolean add(WordFrequency word) {
		return add(word.getWord());
	}
	/**
	 * adds the word to the collection sorted by frequency and
	 * if required alphabetically
	 * @param word the word being added
	 * @return boolean if the collection is sorted
	 */
	public boolean addSorted(String word) {
		int low = 0;
		int high = this.size() - 1;
		int mid;

		if (high == -1){ // ArrayList is empty
			this.add(0, new WordFrequency(word));
			return true;
		}
		else{ //Array list is not empty and there for needs to be added elsewhere
			while (low <= high) {
				mid = (low + high) / 2;
				if (word.compareTo(this.get(mid).getWord()) > 0)
					low = mid + 1;
				else if (word.compareTo(this.get(mid).getWord()) < 0)
					high = mid - 1;
			}
		}
		// Low position always gives the index at which word should be inserted
		this.add(low, new WordFrequency(word));
		sort();
		return true;
	}
	
	/**
	 * this method sorts words by frequency.
	 * 
	 * @param words
	 *            a sorted collection of words from the file
	 */
	private void sort() {
		int i, j;
		for (i = 0; i < size(); i++){
			for (j = 1; j < (size() - i); j++) {
				if (get(j-1).getFrequency() < get(j).getFrequency()) {
					add(j-1, remove(j));		
				}
			}
		}
		System.out.println("Done Sort");
	}// sort
}

/**
 * This class keeps track of all words in the text file and the amount of times
 * it appears in the file.
 * 
 * @author James Celona
 * @author Joe Young
 * 
 */
class WordFrequency implements Comparable<Object> {
	private String word;
	private int frequency;

	public WordFrequency(String word) {
		this.word = word;
		frequency = 1;
	}

	// Mutators and Accessors

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequency() {
		return frequency;
	}

	public void incrementFrequency() {
		frequency++;
	}

	// toString overriding default toString
	@Override
	public String toString() {
		Formatter f = new Formatter();
		String s = f.format("%s, Occurs %d times", this.word, this.frequency)
				.toString();
		f.close();
		return s;
	}
	@Override
	public boolean equals(Object testWord) {
		if (testWord == this)
			return true;
		else if (testWord == null)
			return false;
		else if ((testWord instanceof String))
			return (this.word.equalsIgnoreCase((String) testWord));
		else if (!(testWord instanceof WordFrequency))
			return false;
		WordFrequency word = (WordFrequency) testWord;
		if (this.word.equalsIgnoreCase(word.getWord())
				&& this.frequency == word.getFrequency()) {
			return true;
		}
		return false;
	}

	// @implements Comparable<Object> :: requires this method
	// Compares by the frequency integer
	@Override
	public int compareTo(Object o) {
		return this.frequency - ((WordFrequency) o).frequency;
	}
	

}
