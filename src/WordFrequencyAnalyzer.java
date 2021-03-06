import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JTextArea;

/**
 * This class will be keeping track of the frequency of word occurrences within
 * a text file (a GUI will allow users to select the file).
 * 
 * Notable Classes that this class interacts with: WordCounter.java,
 * ReportGenerator.java, WordFrequencyCollection.java
 * 
 * @author James Celona
 * @author Joe Young
 * 
 */

public class WordFrequencyAnalyzer {

	private final String regex = "\\W"; // Regular Expressions to handle scanner
	private WordFrequencyCollection words = new WordFrequencyCollection();
	private ReportGenerator reportGen;
	private Scanner inFileScanner;

	/**
	 * 
	 * @param scan reads from the textfile
	 *            
	 */
	WordFrequencyAnalyzer(Scanner scan) {
		inFileScanner = scan.useDelimiter(regex);
		populateCollection();
		reportGen = new ReportGenerator(words);
	}

	/**
	 * Based on selection perform the operation
	 * 
	 * @param outputSource
	 *            the users output source selection
	 */
	public void outputAnalysis(Object outputSource) {
		switch (getSubstring(outputSource.getClass().toString(), '.')) {
		case "printstream":
			reportGen.exportToPrintStream((PrintStream) outputSource);
			break;
		case "jtextarea":
			reportGen.exportToJTextArea((JTextArea) outputSource);
			break;
		default:
			// This is an if because File.class is different depending on OS
			if (outputSource instanceof File) {
				String extension = getSubstring(((File) outputSource).getName(), '.');
				switch (extension) {
				case "txt":
					reportGen.exportToTXTFile((File) outputSource);
					break;

				case "html":
					reportGen.exportToHTMLFile((File) outputSource);
					break;

				case "csv":
					reportGen.exportToCSVFile((File) outputSource);
					break;

				default:
					throw new IllegalArgumentException(
							"Unaccepted File format: " + extension);
				}
			} else {
				throw new IllegalArgumentException("Unaccepted Output Source: "
						+ outputSource.getClass());
			}
		}
	}
	
	private void populateCollection() {
		String temp = null;
		while (inFileScanner.hasNext()) {
			temp = inFileScanner.next();
			if (temp.length() > 0) //fill till temp returns false
				words.add(temp);
		}
	}

	private static String getSubstring(String s, char token) {
		String subString = null;
		int i = s.lastIndexOf(token);

		if (i > 0 && i < s.length() - 1)
			subString = s.substring(i + 1);
		if (subString == null)
			return "";
		return subString.toLowerCase();
	}
}
