import java.awt.Font;
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
	String format = "%-30s\t%s";
	ArrayList<WordFrequency> mostFrequent = new ArrayList<WordFrequency>();
	ArrayList<WordFrequency> leastFrequent = new ArrayList<WordFrequency>();
	WordFrequency[] wordsArray;

	public ReportGenerator(Collection<WordFrequency> wordsInput) {
		words = wordsInput;
		if(words != null && words.size() > 0){
			wordsArray = new WordFrequency[words.size()];
			wordsArray = words.toArray(wordsArray);
			WordFrequency lastWord, firstWord;
			Iterator<WordFrequency> getKeyWords = words.iterator();
			Iterator<WordFrequency> itr = words.iterator();
			sort(this.wordsArray);
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

	/**
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

	public void exportToTXTFile(File file) {
		FileWriter writeFile;
		Iterator<WordFrequency> tempItr = words.iterator();
		WordFrequency tempWF;
		try {
			writeFile = new FileWriter(file, false);
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
			System.exit(0); // TODO should we just exit the program when it runs
							// into I/O? Or just send them back?
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportToHTMLFile(File file) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(file, false);
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
							+ "        <td class=\"word\">Total Occurenes</td>"
							+ newline
							+ "        <td colspan=\"2\" class=\"num\">"
							+ totalOccurances
							+ "</td>"
							+ newline
							+ "    </tr>"
							+ newline
							+ "    <tr>"
							+ newline
							+ "        <td class=\"word\">Average Occurences</td>"
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void exportToCSVFile(File file) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(file, false);
			PrintWriter pw = new PrintWriter(writeFile);
			writeFile.write(getReport());
			writeFile.append("Word\t Frequency" + newline + newline);
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
		/*for(int i = 0; i < wordsArray.length; i++){
			textArea.append(wordsArray[i].getWord() + "\t \t" +
			wordsArray[i].getFrequency() +newline);
			textArea.append("\t" + wordsArray[i].getFrequency() + "\n");*/
		}
	}

	/**
	 * 
	 * @return an array of the word collected
	 */
	public WordFrequency[] getArray() {
		return wordsArray;
	}

	/**
	 * this method sorts words by frequency.
	 * 
	 * @param words
	 *            a sorted collection of words from the file //TODO: aids (sorts with ITERATOR)
	 */
	public void sort(WordFrequency[] words) {
		int i, j;
		WordFrequency temp = null;
		for (i = 0; i < words.length; i++) {
			for (j = 1; j < (words.length - i); j++) {
				if (words[j - 1].getFrequency() < words[j].getFrequency()) {
					temp = words[j];
					words[j] = words[j - 1];
					words[j - 1] = temp;
				}
			}
		}
	}// sort
} // reportGenerator