/**
 * This is responsible for opening the text file (GUI), 
 * creating a scanner object and passing said object to
 * WordFrequencyAnalyzer.java
 * 
 * 
 * @author James Celona
 *@author Joe Young
 */

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;

public class WordCounter {
	public static void main(String args[]) {

		JFileChooser filePicker;
		filePicker = new JFileChooser();
		// ensures user does not select a directory
		filePicker.setFileSelectionMode(JFileChooser.FILES_ONLY);

		try {
			int nState = filePicker.showOpenDialog(null);
			if (nState == JFileChooser.APPROVE_OPTION) {
				// save the file path
				File pathOfFile = filePicker.getSelectedFile();
				//System.out.println(pathOfFile);
			}

			else {
				JOptionPane.showMessageDialog(null,
						"You must select a file to be analyzed");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}