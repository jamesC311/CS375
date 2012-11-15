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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class WordCounter {
	private static File filePath;

	public static void main(String args[]) throws FileNotFoundException {

		JFileChooser filePicker;
		filePicker = new JFileChooser();
		// ensures user does not select a directory
		filePicker.setFileSelectionMode(JFileChooser.FILES_ONLY);

		try {
			int pickerFlag = filePicker.showOpenDialog(null);
			if (pickerFlag == JFileChooser.APPROVE_OPTION) {
				// save the file path
				filePath = filePicker.getSelectedFile();
				// System.out.println(pathOfFile);
			} // end filePicker try

			else {
				JOptionPane.showMessageDialog(null,
						"You must select a file to be analyzed");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}// end Exception e
		
		if(filePicker != null) {
			creatScannerForWFA();
		}
	}// end Main

	/**
	 * 
	 * @return scanner object reading the text file
	 * @throws FileNotFoundException
	 *             user selected a file doesn't exists or deleted in the process
	 */
	public static Scanner creatScannerForWFA()
	// TODO: actually deal with try/catch instead of just throwing
			throws FileNotFoundException {
		Scanner scanner = new Scanner(filePath);
		System.out.println(filePath);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);
		}
		scanner.close();
		return scanner;
	} //

}// end wordCounter
