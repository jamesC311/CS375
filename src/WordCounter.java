/**
 * This is responsible for opening the text file (GUI), 
 * creating a scanner object and passing said object to
 * WordFrequencyAnalyzer.java * 
 * 
 * @author James Celona
 *@author Joe Young
 *@version 11/15/2012
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordCounter {
	private Scanner scanner; // Scanner that will be used to getNext
	private WordFrequencyAnalyzer analyzer;
	
	public WordCounter(File filePath) {
		try {
			scanner = new Scanner(filePath);
			analyzer = new WordFrequencyAnalyzer(scanner);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exportResults(Object o){
		analyzer.outputAnalysis(o);
	}

}// end wordCounter
