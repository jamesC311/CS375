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
import javax.swing.filechooser.FileNameExtensionFilter;

public class WordCounter {
	private Scanner scanner; // Scanner that will be used to getNext
	private WordFrequencyAnalyzer analyzer;
	private File inFile = null;
	private File outFile = null;
	
	public WordCounter() {
		startFileOpen();
		if(inFile != null){
			try {
				scanner = new Scanner(inFile);
				analyzer = new WordFrequencyAnalyzer(scanner);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This takes an object so that any output source can be implemented later
	 * 
	 * @param o the output source to be generated
	 */
	public void exportResults(Object o){
		analyzer.outputAnalysis(o);
	}
	
	/**
	 * Have the user select the a file to be analyzed and assure they are only
	 * opening a file
	 */
	public void startFileOpen() {
		JFileChooser filePicker = new JFileChooser();

		// ensures user does not select a directory
		filePicker.setFileSelectionMode(JFileChooser.FILES_ONLY); //user won't be able to select folders
		try {
			if (filePicker.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				inFile = filePicker.getSelectedFile();
			} else {
				if (inFile == null){
					JOptionPane.showMessageDialog(null,
							"You must select a file to be analyzed");
				}
				else
					JOptionPane.showMessageDialog(
							null,
							"File not change, current file: \\"
									+ inFile.toString() + "\\");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Have the user select the location for the output 
	 * opening a file
	 */
	public void startFileSave() {
		JFileChooser fileOpener = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Accepted Report Formats [txt, html, csv]", "txt", "html", "csv");
		fileOpener.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileOpener.setFileFilter(filter);
		try {
			if (fileOpener.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				outFile = fileOpener.getSelectedFile();

			} else {
				JOptionPane.showMessageDialog(null,
						"You must select a filename to save");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * 
	 * @return the file the user selected
	 */
	public File getInFile(){
		return inFile;
	}
	/**
	 * 
	 * @return the output file the user selected
	 */
	public File getOutFile(){
		return outFile;
	}

}// end wordCounter
