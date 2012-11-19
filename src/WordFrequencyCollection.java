import java.util.ArrayList;
import java.util.Formatter;
/**
 * This class will be maintaining a collection of words from a text file
 * It will also be keeping track of the number of times the word is seen.
 * 
 * @author James Celona
 *@author Joe Young
 */
public class WordFrequencyCollection extends ArrayList<WordFrequency> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //TODO: Find out what this is..
	public int indexOf(String s) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(s))
				return i;
		}
		return -1;
	}
	/**
	 * 
	 * @param s the word attempting to be added to the collection
	 * @return boolean if the word exists already in the collection.
	 */
	public boolean contains(String s) {
		if (size() <= 0)
			return false;
		for (int i = 0; i < size(); i++) {
			if (get(i).equals(s))
				return true;
		}
		return false;
	}
	
	public void sort(){
		int i, j;
		WordFrequency temp = null;
		for (i = 0; i < size(); i++) {
			for (j = 1; j < (size() - i); j++) {
				if (get(j - 1).getFrequency() < this.get(j).getFrequency()) {
					temp = get(j);
					set(j, get(j - 1));
					set(j - 1, temp);
				}
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param s 
	 * @return
	 */
	public boolean add(String s) {
		if (s == null)
			return false;
		if (this.contains(s)) {
			this.get(indexOf(s)).incrementFrequency();
			return true;
		}
		
		return addAlphabetically(s);
		//generic add uncomment
	//	return this.add(new WordFrequency(s));
	}
	
	public boolean addAlphabetically(String word )
    {
        int low = 0;
        int high = this.size() - 1;
        int mid;
        
        if(high == -1) //ArrayList is empty
        	this.add(new WordFrequency(word));
        while( low <= high ){
            mid = ( low + high ) / 2;
            if(word.compareTo(this.get(mid).getWord()) > 0 )
             low = mid + 1;
            else if(word.compareTo(this.get(mid).getWord()) < 0 ) 
            	 high = mid - 1;
        }       

        this.add(low, new WordFrequency(word)); 
        return true;
    }
}

/**
 * This file keeps track of all words in the text file
 * and the amount of times it appears in the file.
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

	public void decrementFrequency() {
		frequency--;
	}

	// toString overriding default toString
	public String toString() {
		Formatter f = new Formatter();
		String s = f.format("%-40s\t%d", this.word, this.frequency).toString();
		f.close();
		return s;
	}

	// equals overriding default object equals
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
	public int compareTo(Object o) {
		return this.frequency - ((WordFrequency) o).frequency;
	}

}
