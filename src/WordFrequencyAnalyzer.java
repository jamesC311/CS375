import java.io.File;
import java.util.Formatter;

/**
 * This class will be keeping track of the frequency of word occurrences within
 * a text file (a GUI will allow users to select the file).
 * 
 * Notable Classes that this class interacts with: WordCounter.java
 * ReportGenerator.java WordFrequencyCollection.java
 * 

 * @author James Celona
 * @author Joe Young
 * 
 */
public class WordFrequencyAnalyzer {
	
	private WordFrequencyCollection words = new WordFrequencyCollection();
	private WordCounter wordCounter;
	private ReportGenerator reportGen = new ReportGenerator();
	private File filePath = null;  //unless the user adds a file we can assume there is nothing.
	private Object outputSource;
	
	/**
	 * 
	 * @param filePath the path to the file the user wants analyzed. 
	 */
	WordFrequencyAnalyzer(File filePath) {
		this.filePath = filePath;
		wordCounter = new WordCounter(this.filePath);
		outputSource = System.out;
	}
	/**
	 * 
	 * @param filePath the path to the file the user wants analyzed.
	 * @param outputSouce //TODO: what is outputsource? just where we are sending it?
	 */
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
		words.sort();
		reportGen.executeReport(words);
		outputAnalysis();
	}
	
	public void outputToFile(String extension, File file){
		System.out.println(extension);
		if(extension.contains("html")){
			reportGen.printToHTMLFile(file);
		}
		else if(extension.contains("txt")){
			reportGen.printToTXTFile(file);
		}
	}
	/**
	 * Creates a basic excel like file displaying all words and their frequency.
	 */
	private void outputAnalysis() {
		Formatter f = new Formatter();
		String newline = System.lineSeparator();
		f.format("%-30s\t%s%s%-30s\t%s%s","Word","Frequency", newline, 
					"----", "---------",  newline);
		String header =	f.toString();
		f.close();
		if (outputSource instanceof java.io.PrintStream) {
			((java.io.PrintStream) outputSource).print(header);
			for (int i = 0; i < words.size(); i++) {
				((java.io.PrintStream) outputSource).print(words.get(i)
						.toString() + newline);
			}
		}
		if (outputSource instanceof javax.swing.JTextArea) {
			((javax.swing.JTextArea) outputSource).append(header);
			for (int i = 0; i < words.size(); i++) {
				((javax.swing.JTextArea) outputSource).append("  "
						+ words.get(i).toString() + newline);
			}
		}
	}
/**
 * Add all words to the word collection //TODO: does this even need to be documented?
 */
	private void populateCollection() {
		while (wordCounter.hasNext())
			words.add(wordCounter.getNextWord());
	}

}
