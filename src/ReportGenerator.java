import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JTextArea;

/**
 * This class will produce different forms of reports based on user requests
 * reports will contain all words within the text file and their frequency
 * 
 * @author James Celona
 * @author Joe Young
 * 
 */
public class ReportGenerator {
	String newline = System.lineSeparator();
	int uniqueWords;
	int averageOccurance;
	int totalOccurances;
	Collection<WordFrequency> words;
	String format = "%-30s\t%s";  //Uniform String format.
	ArrayList<WordFrequency> mostFrequent = new ArrayList<WordFrequency>();
	ArrayList<WordFrequency> leastFrequent = new ArrayList<WordFrequency>();
	WordFrequency[] wordsArray;

	/**
	 * constructor 
	 * 
	 * @param wordsInput a collection (of wordFrequency) 
	 */
	public ReportGenerator(Collection<WordFrequency> wordsInput) {
		words = wordsInput;
		if(words != null && words.size() > 0){
			wordsArray = new WordFrequency[words.size()];
			wordsArray = words.toArray(wordsArray);
			WordFrequency lastWord, firstWord;
			Iterator<WordFrequency> getKeyWords = words.iterator();
			Iterator<WordFrequency> itr = words.iterator();
			uniqueWords = words.size();
			averageOccurance = totalOccurances = 0;
			
			firstWord = lastWord = getKeyWords.next(); //handles case in which there is only one word
			totalOccurances+=firstWord.getFrequency();
			while(getKeyWords.hasNext()){
				lastWord = getKeyWords.next();
				totalOccurances+=lastWord.getFrequency();
			}
			averageOccurance = totalOccurances/uniqueWords;
			//populates leastFrequnt and MostFrequent lists
			while(itr.hasNext()){
				WordFrequency temp = itr.next();
				if(temp.getFrequency() == firstWord.getFrequency()){
					mostFrequent.add(temp);
				}
				if(temp.getFrequency() == lastWord.getFrequency()){
					leastFrequent.add(temp);
				}
			}
		}
	}// report generator constructor

	/**This is a report that gives information about the text file
	 * 
	 * @return String generic output for targets that do not require specific
	 *         format
	 */
	public String getReport() {		
		return ("*********************************************" + newline
				+ "* Unique Words:           " + uniqueWords + newline
				+ "* Total Occurances:       " + totalOccurances + newline
				+ "* Average Occurences:     " + averageOccurance + newline
				+ "* Most Frequent Word(s):  " + mostFrequent.size() +" words occur "+mostFrequent.get(0).getFrequency()+" times" + newline 
				+ "* [See top of List]" + newline
				+ "* Least Frequent Word(s): " + leastFrequent.size() +" words occur "+leastFrequent.get(0).getFrequency()+" times " + newline
				+ "* [See bottom of List]" + newline
				+ "*********************************************" + newline);
	}
	
	/**
	 * Has the report saved as a .txt which can be opened by any number of programs. 
	 * 
	 * @param txtFile the generated report 
	 */
	public void exportToTXTFile(File txtFile) {
		FileWriter writeFile;
		Iterator<WordFrequency> tempItr = words.iterator();
		WordFrequency tempWF;
		try {
			writeFile = new FileWriter(txtFile, false);
			writeFile.write(getReport());
			writeFile.append(String.format(format, "Word", "Frequency")+ newline);
		
			while(tempItr.hasNext()){
				tempWF = tempItr.next();
				writeFile.append(String.format(format, tempWF.getWord(), tempWF.getFrequency() + newline));
			}
			writeFile.flush();
			writeFile.close();
		} catch (IOException e) {
			System.out
					.println("The program encountered an error, please restart the program");
			System.exit(0); 
			e.printStackTrace();
		}
	}
	
	/**This will generate an .html which can be opened by any browser, and will
	 * display a table of the words and frequency
	 * 
	 * @param htmlFile the report being saved to an html
	 */
	public void exportToHTMLFile(File htmlFile) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(htmlFile, false);
			writeFile
					.write("<head>"
							+ newline
							+ "<title>Word Frequnecy Report</title>"
							+ newline
							+ "<style type=\"text/css\">"
							+ newline
							+ "table {border-spacing: 0; border-collapse: collapse;}"
							+ newline
							+ "td, th {padding: 0.25em; border: 1px solid black;}"
							+ newline
							+ "th{text-align: center;backround-color: #BBB;}"
							+ newline
							+ "div{margin-top: 1em;margin-left: 3em;float: left;}"
							+ newline
							+ ".word{border-left:thick solid black;}"
							+ newline
							+ ".num{border-right:thick solid black;}"
							+ newline
							+ "</style>"
							+ newline
							+ "</head>"
							+ newline
							+ "<body>"
							+ newline
							+ "<header>"
							+ newline
							+ "<h1> Generated Report From Collection</h1>"
							+ newline
							+ "<p> This is a generated report based on a word collection, focusing on the frequency of words </p>"
							+ newline
							+ "</header>"
							+ newline
							+ "<div id=\"report\">"
							+ newline
							+ "<table>"
							+ newline
							+ "    <tr>"
							+ newline
							+ "        <th colspan=\"3\" class=\"word num\">Report Statistics</th>"
							+ newline
							+ "	</tr>"
							+ newline
							+ "    <tr>"
							+ newline
							+ "        <td class=\"word\">Unique Words</td>"
							+ newline
							+ "        <td colspan=\"2\" class=\"num\">"
							+ uniqueWords
							+ "</td>"
							+ newline
							+ "    </tr>"
							+ newline
							+ "    <tr>"
							+ newline
							+ "        <td class=\"word\">Total Occurrences</td>"
							+ newline
							+ "        <td colspan=\"2\" class=\"num\">"
							+ totalOccurances
							+ "</td>"
							+ newline
							+ "    </tr>"
							+ newline
							+ "    <tr>"
							+ newline
							+ "        <td class=\"word\">Average Occurrences</td>"
							+ newline
							+ "        <td colspan=\"2\" class=\"num\">"
							+ averageOccurance
							+ "</td>"
							+ newline
							+ "    </tr>"
							+ newline
							+ "    <tr>"
							+ newline
							+ "        <th colspan=\"3\" class=\"word num\">Most Frequent Word(s)</th>"
							+ newline + "    </tr>" + newline);
			for (int i = 0; i < mostFrequent.size(); i++) {
				writeFile.append("    <tr>" + newline
						+ "        <td class=\"word\">"
						+ mostFrequent.get(i).getWord() + "</td>" + newline
						+ "		 <td colspan=\"2\" class=\"num\">"
						+ mostFrequent.get(i).getFrequency() + " times</td>"
						+ newline + "    </tr>" + newline);
			}
			writeFile
					.append("    <tr >"
							+ newline
							+ "        <th colspan=\"3\" class=\"word num\">Least Frequent Word(s)</th>"
							+ newline + "    </tr>" + newline);
			for (int i = leastFrequent.size() - 1; i >= 0; i--) {
				writeFile.append("    <tr>" + newline
						+ "        <td class=\"word\">"
						+ leastFrequent.get(i).getWord() + "</td>" + newline
						+ "		 <td colspan=\"2\" class=\"num\">"
						+ leastFrequent.get(i).getFrequency() + " times</td>"
						+ newline + "    </tr>" + newline);
			}
			writeFile.append("</table>" + newline + "</div>");
			writeFile
					.append("<div id=\"list\">"
							+ newline
							+ "<table>"
							+ newline
							+ "    <tr >"
							+ newline
							+ "        <th colspan=\"6\" class=\"word num\">List of Words</th>"
							+ newline + "    </tr>" + newline + "<tr>"
							+ newline);
			for (int i = 0; i < wordsArray.length; i++) {
				writeFile.append("        <td class=\"word\">"
						+ wordsArray[i].getWord() + "</td>" + newline
						+ "		 <td  class=\"num\">"
						+ wordsArray[i].getFrequency() + " times</td>"
						+ newline);
				if ((i + 1) % 3 == 0) {
					writeFile.append("    </tr>" + newline);
				}
			}
			writeFile.append("</table>" + newline + "</div>");
			writeFile.append("</body>" + newline + "</html>");
			writeFile.flush();
			writeFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Has the report saved as a .csv (Which can be opened in Excel)
	 * 
	 * @param csvFile the generated report to be created
	 */
	public void exportToCSVFile(File csvFile) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(csvFile, false);
			PrintWriter pw = new PrintWriter(writeFile);
			writeFile.write(getReport());
			writeFile.append("Word\t" + "," + "Frequency" + newline + newline);
			for (int i = 0; i < wordsArray.length; i++) {
				writeFile.append(wordsArray[i].getWord() + ","
						+ wordsArray[i].getFrequency() + "" + newline);
			}
			writeFile.flush();
			writeFile.close();
			pw.flush();
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param stream What will be printed out
	 */
	public void exportToPrintStream(PrintStream stream) {
		Iterator<WordFrequency> tempItr = words.iterator();
		WordFrequency tempWF;
		stream.print(getReport());
		stream.print(String.format(format, "Word","Frequency"+newline));
		while(tempItr.hasNext()){
			tempWF = tempItr.next();
			stream.print(String.format(format, tempWF.getWord(),
					tempWF.getFrequency() + newline));
		}
	}
	
	/**This has the report that was generated print to the screen
	 * 
	 * @param textArea where the text is going to be printed
	 */
	public void exportToJTextArea(JTextArea textArea) {
		Iterator<WordFrequency> tempItr = words.iterator();
		WordFrequency tempWF;
		
		textArea.setText(getReport());
		textArea.append(String.format(format, "Word","Frequency"+newline));
		while(tempItr.hasNext()){
			tempWF = tempItr.next();
			textArea.append(String.format(format, tempWF.getWord(),
					tempWF.getFrequency() + newline));
			tempWF = null;
		}
	}

	/**
	 * 
	 * @return an array of the word collected
	 */
	public WordFrequency[] getArray() {
		return wordsArray;
	}
} // reportGenerator