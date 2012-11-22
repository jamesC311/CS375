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

	//String regex = "[^\\p{Alpha}|^\\p{Digit}]"; // Regular Expressions to handle
												// scanner
	private final String regex = "\\W"; // Regular Expressions to handle scanner
	private WordFrequencyCollection words = new WordFrequencyCollection();
	private ReportGenerator reportGen;
	private Scanner inFileScanner;
	
	/**
	 * 
	 * @param scan reads from the text file
	 */
	WordFrequencyAnalyzer(Scanner scan) {
		inFileScanner = scan.useDelimiter(regex);
		populateCollection();
		reportGen = new ReportGenerator(words);
	}
	/**
	 * Based on selection perform the operation
	 * 
	 * @param arg0 the users selection.
	 */
	public void outputAnalysis(Object arg0){
		switch(getSubstring(arg0.getClass().toString(), '.')){
			case "printstream":
				reportGen.exportToPrintStream((PrintStream)arg0);break;
			case "jtextarea":
				reportGen.exportToJTextArea((JTextArea)arg0);break;
			default:
				//This is an if because File.class is different depending on OS
				if(arg0 instanceof File){
					String extension = getSubstring(((File)arg0).getName(), '.');
					switch (extension) {
			        case "txt":
			            reportGen.exportToTXTFile((File)arg0);break;
			        case "html":
			        	reportGen.exportToHTMLFile((File)arg0);break;
			        default:
			            throw new IllegalArgumentException("Unaccepted File format: " + extension);
					}
				}
				else{
	            throw new IllegalArgumentException("Unaccepted Output Source: " + arg0.getClass());
				}

		}
	}
	
	private void populateCollection() {
		String temp = null;
		while (inFileScanner.hasNext()) {
			temp = inFileScanner.next();
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
