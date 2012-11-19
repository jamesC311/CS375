import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.filechooser.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

/**
 * This class is just like MenuLookDemo, except the menu items
 * actually do something, thanks to event listeners.
 */
public class InitApp implements ActionListener {
	JTextArea reportOutput;
	JScrollPane scrollPane;
	JButton fileSelect, generateReport;
	File filePath = null;
	String newLine = System.getProperty("line.separator");

	// Functional classes
	WordFrequencyAnalyzer analyzer;
	/**
	 * 
	 * @return the menu bar for the file selector.
	 */
	public JMenuBar createFileBar() {
		JMenuBar menuBar; // File bar
		JMenu menu; // Place holders for respective menus
		JMenuItem menuItem; // Used as a placeholder to create menu items

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("New Report");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Export Report");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		return menuBar;
	}

	public Container createContentPane() {
		// Create the content-pane-to-be.
		// JPanel contentPane = new JPanel(new BorderLayout());
		JPanel contentPane = new JPanel(new GridBagLayout());
		GridBagConstraints gridSettings = new GridBagConstraints();
		contentPane.setOpaque(true);

		fileSelect = new JButton("Select File");
		fileSelect.addActionListener(this);
		gridSettings.fill = GridBagConstraints.HORIZONTAL;
		gridSettings.weightx = 0.5;
		gridSettings.gridx = 0;
		gridSettings.gridy = 0;
		// buttonPane.add(fileSelect, gridSettings);
		contentPane.add(fileSelect, gridSettings);

		generateReport = new JButton("Generate Report");
		generateReport.addActionListener(this);
		gridSettings.gridx = 1;
		gridSettings.gridy = 0;
		// buttonPane.add(generateReport, gridSettings);
		contentPane.add(generateReport, gridSettings);

		reportOutput = new JTextArea();
		reportOutput.setEditable(false);
		scrollPane = new JScrollPane(reportOutput);
		gridSettings.fill = GridBagConstraints.BOTH;
		gridSettings.weightx = 0.5;
		gridSettings.weighty = 0.5;
		gridSettings.gridheight = 2;
		gridSettings.gridwidth = 3;
		gridSettings.gridx = 0;
		gridSettings.gridy = 1;
		// buttonPane.add(scrollPane, gridSettings);
		contentPane.add(scrollPane, gridSettings);
		// Add the text area to the content pane.
		// contentPane.add(buttonPane, BorderLayout.CENTER);
		// contentPane.add(scrollPane, BorderLayout.CENTER);

		return contentPane;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Select File")) {
			startFileOpen();
			if (filePath != null)
				analyzer = new WordFrequencyAnalyzer(filePath, reportOutput);
		}
		if (action.equals("Generate Report")) {
			if (filePath != null)
				analyzer.executeAnalysis();
			else
				JOptionPane
						.showMessageDialog(null,
								"You must select a file to be analyzed before you can generate a report");
		}
		if (action.equals("New Report")) {
			reportOutput.setText("");
			filePath = null;
		}
		if (action.equals("Export Report")) {
			if (filePath == null)
				JOptionPane
						.showMessageDialog(null,
								"You must select a file to be analyzed before you can export a report");
			else
				startFileSave();
		}
	}
	/**Have the user select the a file to be analyzed and assure
	 * they are only opening a file 
	 */
	public void startFileOpen() {
		JFileChooser filePicker = new JFileChooser();
	
		// ensures user does not select a directory
		filePicker.setFileSelectionMode(JFileChooser.FILES_ONLY);
		try {
			if (filePicker.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				filePath = filePicker.getSelectedFile();
			} else {
				if (filePath == null)
					JOptionPane.showMessageDialog(null,
							"You must select a file to be analyzed");
				else
					JOptionPane.showMessageDialog(
							null,
							"File not change, current file: \""
									+ filePath.toString() + "\"");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 *If the user selects .txt or .html save the file to the specified output filepath
	 */
	public void startFileSave() {
		JFileChooser fileOpener = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Accepted Report Formats", "txt", "html");
		// ensures user does not select a directory
		fileOpener.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileOpener.setFileFilter(filter);
		try {
			if (fileOpener.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				// save the file path
				filePath = fileOpener.getSelectedFile();
				if (getExtension(filePath).equals("html")) {
					writeHTML(filePath);
				} else if (getExtension(filePath).equals("txt")) {
					writeTXT(filePath);
				}
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
	 * Create the GUI and display it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Text File Analysis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		InitApp demo = new InitApp();
		frame.setJMenuBar(demo.createFileBar());
		frame.setContentPane(demo.createContentPane());

		// Display the window.
		frame.setSize(250, 400);
		frame.setVisible(true);
	}
	
	/**Outputs to an HTML File
	 * 
	 * @param outputFile file to the output file
	 */
	private void writeHTML(File outputFile) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(filePath, false);
			writeFile.write(reportOutput.getText());
			writeFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * writes a .txt report file
	 * 
	 * @param file the filepath of the output file.
	 */
	private void writeTXT(File file) {
		FileWriter writeFile;
		try {
			writeFile = new FileWriter(filePath, false);
			writeFile.write(reportOutput.getText());
			writeFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}