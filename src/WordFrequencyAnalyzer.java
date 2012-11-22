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

//TODO: If we want to do a dictionary, we will have to store words in a DB.
public class WordFrequencyAnalyzer {
	String regex = "[^\\p{Alpha}|^\\p{Digit}]"; // Regular Expressions to handle
												// scanner
	private WordFrequencyCollection words = new WordFrequencyCollection();
	private ReportGenerator reportGen;
	private Scanner inFile;
	
	/**
	 * 
	 * @param scan reads from the text file
	 */
	WordFrequencyAnalyzer(Scanner scan) {
		inFile = scan.useDelimiter(regex);
		populateCollection();
		reportGen = new ReportGenerator(words);
	}
	/**
	 * Based on selection displays and/or saves different
	 * types of files
	 * @param selection the format chosen by the user
	 */
	public void outputAnalysis(Object selection) {
		switch (getSubstring(selection.getClass().toString(), '.')) {
		case "printstream":
			reportGen.exportToPrintStream((PrintStream) selection);
			break;

		case "jtextarea":
			reportGen.exportToJTextArea((JTextArea) selection);
			break;
		case "win32shellfolder2":
			String extension = getSubstring(((File) selection).getName(), '.');

			switch (extension) {
			case "txt":
				reportGen.exportToTXTFile((File) selection);
				break;
			case "html":
				reportGen.exportToHTMLFile((File) selection);
				break;
			default:
				throw new IllegalArgumentException("Unaccepted File format: "
						+ extension);
			}
			break;
		default:
			throw new IllegalArgumentException("Unaccepted Output Source: "
					+ selection.getClass());
		}
	}
	
	private void populateCollection() {
		String temp = null;
		while (inFile.hasNext()) {
			temp = inFile.next();
			if (temp.length() > 0)
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
