import java.util.ArrayList;
import java.util.Formatter;

public class WordFrequencyCollection extends ArrayList<WordFrequency> {

	public int indexOf(String s) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(s))
				return i;
		}
		return -1;
	}

	public boolean contains(String s) {
		if (this.size() <= 0)
			return false;
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(s))
				return true;
		}
		return false;
	}

	public boolean add(String s) {
		if (s == null)
			return false;
		if (this.contains(s)) {
			this.get(indexOf(s)).incrementFrequency();
			return true;
		}
		return this.add(new WordFrequency(s));
	}

}

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
		return f.format("%-40s\t%d", this.word, this.frequency).toString();
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
