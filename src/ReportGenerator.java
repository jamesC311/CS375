import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

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
	ArrayList<WordFrequency> mostFrequent = new ArrayList<WordFrequency>();
	ArrayList<WordFrequency> leastFrequent = new ArrayList<WordFrequency>();
	WordFrequency[] wordsArray;

	public String executeReport(Collection<WordFrequency> words) {
		wordsArray = new WordFrequency[words.size()];
		wordsArray = words.toArray(wordsArray);
		sort(this.wordsArray);
		uniqueWords = words.size();
		averageOccurance = totalOccurances = 0;
		leastFrequent.add(wordsArray[wordsArray.length - 1]);
		mostFrequent.add(wordsArray[0]);
		int i = 1;
		while (true) {
			if (wordsArray[i].getFrequency() == mostFrequent.get(0).getFrequency()) {
				mostFrequent.add(wordsArray[i++]);
			}
			else break;
		}
		i = uniqueWords;
		for(i = 2; i < uniqueWords; i++) {
			if (wordsArray[uniqueWords-i].getFrequency() == leastFrequent.get(0).getFrequency()) {
				leastFrequent.add(wordsArray[uniqueWords-i]);
			}
			else break;
		}

		for (i = 0; i < uniqueWords; i++)
			totalOccurances += wordsArray[i].getFrequency();

		averageOccurance = totalOccurances / uniqueWords;
		System.out.println(getReport());
		return getReport();
	}

	public String getReport() {
		return ("*********************************************" + newline
				+ "* Unique Words:        " + uniqueWords + newline
				+ "* Total Occurances:    " + totalOccurances + newline
				+ "* Average Occurences:  " + averageOccurance + newline
				+ "* Most Frequent Word:  " + mostFrequent.get(0) + newline
				+ "* Least Frequent Word: " + leastFrequent.get(leastFrequent.size()-1) + newline
				+ "*********************************************" + newline);
	}

	public void exportToTXTFile(File file) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(file, false);
			writeFile.write(getReport());
			writeFile.append("Word\t-Frequency" + newline);
			for(int i = 0; i < wordsArray.length; i++){
				writeFile.append(wordsArray[i].getWord() + "\t" + wordsArray[i].getFrequency() +newline);
			}
			writeFile.flush();
			writeFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportToHTMLFile(File file) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(file, false);
			writeFile
					.write("<head>"	+ newline
							+ "<title>Word Frequnecy Report</title>" + newline
							+ "<style type=\"text/css\">" + newline
							+ "table {border-spacing: 0; border-collapse: collapse;}" + newline
							+ "td, th {padding: 0.25em; border: 1px solid black;}" + newline
							+ "th{text-align: center;backround-color: #BBB;}" + newline
							+ "div{margin-top: 1em;margin-left: 3em;float: left;}" + newline
							+ ".word{border-left:thick solid black;}" + newline
							+ ".num{border-right:thick solid black;}" + newline
							+ "</style>" + newline + "</head>" + newline + "<body>"+ newline
							+ "<header>"+ newline
							+ "<h1> Generated Report From Collection</h1>"+ newline
							+ "<p> This is a generated report based on a word collection, focusing on the frequency of words </p>"+ newline
							+ "<nav>"+ newline
							+ "	<ul>"+ newline
							+ "		<li><a href=\"#report\">Go to Report</a></li>"+ newline
							+ "		<li><a href=\"#list\">Go to List</a></li>"+ newline
							+ "	</ul>"+ newline
							+ "</nav>"+ newline
							+ "</header>"+ newline
							+ "<div id=\"report\">"+ newline
							+ "<table>"+ newline
							+ "    <tr>"+ newline
							+ "        <th colspan=\"3\">Report Statistics</th>"+ newline
							+ "	</tr>"+ newline
							+ "    <tr>"+ newline
							+ "        <td>Unique Words</td>"+ newline
							+ "        <td colspan=\"2\">"+ uniqueWords
							+ "</td>"+ newline
							+ "    </tr>"+ newline
							+ "    <tr>"+ newline
							+ "        <td>Total Occurenes</td>"+ newline
							+ "        <td colspan=\"2\">"+ totalOccurances
							+ "</td>"+ newline
							+ "    </tr>"+ newline
							+ "    <tr>"+ newline
							+ "        <td>Average Occurences</td>"+ newline
							+ "        <td colspan=\"2\">"+ averageOccurance
							+ "</td>"+ newline
							+ "    </tr>"+ newline
							+ "    <tr>"+ newline
							+ "        <th colspan=\"3\">Most Frequent Word(s)</th>"
							+ newline + "    </tr>" + newline);
			for (int i = 0; i < mostFrequent.size(); i++) {
				writeFile.append("    <tr>" + newline + "        <td>"
						+ mostFrequent.get(i).getWord() + "</td>" + newline
						+ "		 <td colspan=\"2\">"
						+ mostFrequent.get(i).getFrequency() + " times</td>"
						+ newline + "    </tr>" + newline);
			}
			writeFile.append("    <tr >" + newline
					+ "        <th colspan=\"3\">Least Frequent Word(s)</th>" + newline
					+ "    </tr>" + newline);
			for (int i = leastFrequent.size(); i <= 0; i--) {
				writeFile.append("    <tr>" + newline + "        <td>"
						+ leastFrequent.get(i).getWord() + "</td>" + newline
						+ "		 <td colspan=\"2\">"
						+ leastFrequent.get(i).getFrequency() + " times</td>"
						+ newline + "    </tr>" + newline);
				}
			writeFile.append("</table>" + newline + "</div>");
			writeFile.append("<div id=\"list\">"+ newline
					+ "<table>" + newline
					+ "    <tr >" + newline
					+ "        <th colspan=\"6\" class=\"word num\">List of Words</th>" + newline
					+ "    </tr>" + newline
					+ "<tr>" + newline);
			for (int i = 0; i < wordsArray.length; i++) {
				writeFile.append("        <td class=\"word\">"
						+ wordsArray[i].getWord() + "</td>" + newline
						+ "		 <td  class=\"num\">"
						+ wordsArray[i].getFrequency() + " times</td>"
						+ newline);
					if((i+1)%3==0){
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
	
	public WordFrequency[] getArray(){
		return wordsArray;
	}

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
	}
	
	/**
	 * identifies what type of file we are looking at eg (.txt, .pdf)
	 * @param f output file path.
	 * @return returns the extension of the specified file.
	 */
	private static String getExtension(File f) {
		String extension = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1)
			extension = s.substring(i + 1);
		if (extension == null)
			return "";
		return extension;
	}


}