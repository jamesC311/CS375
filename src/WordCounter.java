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

public class WordCounter{
	private Scanner scanner; //Scanner that will be used to getNext 
    String regex = "[^\\p{Alpha}|^\\p{Digit}]"; //Regular Expressions to dictate the scanner
	//private Pattern ignorePattern = new Pattern(null, 0) ;
	public WordCounter(File filePath){
		try {
			scanner = new Scanner(filePath).useDelimiter(regex);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getNextWord(){
		if(scanner.hasNext()){
			String temp = scanner.next();
			if(temp.length() > 0)
				return temp;
		}
		return null;
	}
	
	public boolean hasNext(){
		return scanner.hasNext();
	}


}// end wordCounter
