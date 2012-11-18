import java.io.File;

/**
 * This class will be keeping track of the frequency of word occurrences within
 * a text file (a GUI will allow users to select the file).
 * 
 * Notable Classes that this class interacts with: WordCounter.java
 * ReportGenerator.java WordFrequencyCollection.java
 * 
 * CRC Card: | ------------------------------------| |Word Frequency Collection
 * | |-------------------------------------| |Ability to add a word to
 * collection | |given a word, report frequency | |iterator |
 * |-------------------------------------|
 * 
 * 
 * @author James Celona
 * @author Joe Young
 * 
 */
public class WordFrequencyAnalyzer {
	private WordFrequencyCollection words = new WordFrequencyCollection();
	private WordCounter wordCounter;
	private ReportGenerator reportGen;
	private File filePath = null;
	private Object outputSource;

	WordFrequencyAnalyzer(File filePath) {
		this.filePath = filePath;
		wordCounter = new WordCounter(this.filePath);
		outputSource = System.out;
	}

	WordFrequencyAnalyzer(File filePath, Object outputSouce) {
		this(filePath);
		this.outputSource = outputSouce;
	}

	/**
	 * This method executes the adding of words to WFC and the utilization of
	 * the WordCounter
	 */
	public void executeAnalysis() {
		populateCollection();
		sortWords(words, words.size());
		outputAnalysis();
	}

	private void outputAnalysis() {
		if (outputSource instanceof java.io.PrintStream) {
			((java.io.PrintStream) outputSource).printf("%-40s\t%s\n%-40s\t%s",
					"Word", "Frequency", "----", "---------");
			for (int i = 0; i < words.size(); i++) {
				((java.io.PrintStream) outputSource).print(words.get(i)
						.toString() + "\n");
			}
		}
		if (outputSource instanceof javax.swing.JTextArea) {
			for (int i = 0; i < words.size(); i++) {
				((javax.swing.JTextArea) outputSource).append("  "
						+ words.get(i).toString() + "\n");
			}
		}
	}

	private void populateCollection() {
		while (wordCounter.hasNext())
			words.add(wordCounter.getNextWord());
	}

	private void sortWords(WordFrequencyCollection word, int n) {
		int i, j;
		WordFrequency temp = null;
		for (i = 0; i < n; i++) {
			for (j = 1; j < (n - i); j++) {
				if (word.get(j - 1).getWord().compareTo(word.get(j).getWord()) > 0) {
					temp = word.get(j);
					word.set(j, word.get(j - 1));
					word.set(j - 1, temp);
				}
			}
		}
		for (i = 0; i < n; i++) {
			for (j = 1; j < (n - i); j++) {
				if (word.get(j - 1).getFrequency() < word.get(j).getFrequency()) {
					temp = word.get(j);
					word.set(j, word.get(j - 1));
					word.set(j - 1, temp);
				}
			}
		}
	}

}
