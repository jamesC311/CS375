import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	WordFrequencyCollection words;

	public String executeReport(WordFrequencyCollection words) {
		this.words = words;
		uniqueWords = words.size();
		averageOccurance = totalOccurances = 0;
		leastFrequent.add(words.get(words.size() - 1));
		mostFrequent.add(words.get(0));
		int i = 1;
		while (true) {
			if (words.get(i).getFrequency() == mostFrequent.get(0).getFrequency()) {
				mostFrequent.add(words.get(i++));
			}
			else break;
		}
		i = uniqueWords;
		for(i = 2; i < uniqueWords; i++) {
			if (words.get(uniqueWords-i).getFrequency() == leastFrequent.get(0).getFrequency()) {
				leastFrequent.add(words.get(uniqueWords-i));
			}
			else break;
		}

		for (i = 0; i < uniqueWords; i++)
			totalOccurances += words.get(i).getFrequency();

		averageOccurance = totalOccurances / uniqueWords;

		return getReport();
	}

	public String getReport() {
		return ("|********************************************" + newline
				+ "| Unique Words:       " + uniqueWords + newline
				+ "| Total Occurances:   " + totalOccurances + newline
				+ "| Average Occurences: " + averageOccurance + newline
				+ "| Most Frequent Word: " + mostFrequent + newline
				+ "| Least Frequent Word:" + leastFrequent + newline
				+ "|********************************************" + newline);
	}

	public void printToTXTFile(File file) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(file, false);
			writeFile.write(getReport());
			writeFile.flush();
			writeFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printToHTMLFile(File file) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(file, false);
			writeFile
					.write("<head>"	+ newline
							+ "<title>Word Frequnecy Report</title>" + newline
							+ "<style type=\"text/css\">" + newline
							+ "table {border-spacing: 0; border-collapse: collapse;}" + newline
							+ "td, th {padding: 0.25em; border: 1px solid black !important;}" + newline
							+ "th{text-align: center;}" + newline
							+ "div{margin-top: 1em;}" + newline
							+ "</style>" + newline + "</head>" + newline + "<body>"+ newline
							+ "<header>"+ newline
							+ "<h1> Generated Report From Collection</h1>"+ newline
							+ "<p> This is a generated report based on a word collection, focusing on the frequency of words </p>"+ newline
							+ "<nav>"+ newline
							+ "	<ul>"+ newline
							+ "		<li><a href=\"#report\">Go to Report</a></li>"+ newline
							+ "		<li><a href=\"#List\">Go to List</a></li>"+ newline
							+ "	</ul>"+ newline
							+ "</nav>"+ newline
							+ "</header>"+ newline
							+ "<div id=\"report\">"+ newline
							+ "<table>"+ newline
							+ "    <tr>"+ newline
							+ "        <th colspan=\"4\">Report Statistics</th>"+ newline
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
			for (int i = 0; i < leastFrequent.size(); i++) {
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
					+ "        <th colspan=\"6\">List of Words</th>" + newline
					+ "    </tr>" + newline);
			for (int i = 0; i < words.size(); i++) {
				if(i%2 == 0){
					writeFile.append("<tr>" + newline); 
				}
				writeFile.append("        <td>"
						+ words.get(i).getWord() + "</td>" + newline
						+ "		 <td >"
						+ words.get(i).getFrequency() + " times</td>"
						+ newline);
					if(i%2==0 && i!=1){
						writeFile.append("    </tr>" + newline);
					}
				}
			writeFile.append("</table>" + newline + "</div>");
			
			writeFile.flush();
			writeFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}