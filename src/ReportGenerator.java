/**
 * This class will produce different forms of reports based on user requests
 * reports will contain all words within the text file and their frequency
 * 
 * @author James Celona
 * @author Joe Young
 *
 */
public class ReportGenerator {
	private WordFrequencyCollection words;
	/**
	 * 
	 * @param words the words within the file file.
	 */
	ReportGenerator(WordFrequencyCollection words) {
		this.words = words;
	}
}
