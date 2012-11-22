import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.filechooser.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * This class is just like MenuLookDemo, except the menu items actually do
 * something, thanks to event listeners.
 */
public class InitApp implements ActionListener {
	JTextField reportStatus;
	JTextArea reportResults;
	JScrollPane scrollPane;
	JButton fileSelect, generateReport, exportReport;
	File filePath = null;
	String newLine = System.getProperty("line.separator");
	WordCounter wordCounter;

	/**
	 * This method sets defaults for the content pane that the user will be
	 * using to select which file to use, whether or not to analyze it and where
	 * and what to export
	 * 
	 * @return the content pane from which the user can select the file to
	 *         analyze and export
	 */
	public Container createContentPane() {
		JPanel contentPane = new JPanel(new GridBagLayout());
		GridBagConstraints gridSettings = new GridBagConstraints();
		contentPane.setOpaque(true);

		fileSelect = new JButton("Select File");
		fileSelect.addActionListener(this);
		gridSettings.fill = GridBagConstraints.HORIZONTAL;
		gridSettings.weightx = 0.5;
		gridSettings.gridx = 0;
		gridSettings.gridy = 0;
		contentPane.add(fileSelect, gridSettings);

		generateReport = new JButton("Generate Report");
		generateReport.addActionListener(this);
		gridSettings.gridx = 1;
		gridSettings.gridy = 0;
		contentPane.add(generateReport, gridSettings);

		exportReport = new JButton("Export Report");
		exportReport.addActionListener(this);
		gridSettings.gridx = 2;
		gridSettings.gridy = 0;
		contentPane.add(exportReport, gridSettings);

		reportStatus = new JTextField();
		reportStatus.setEditable(false);
		gridSettings.fill = GridBagConstraints.BOTH;
		gridSettings.weightx = 0.5;
		gridSettings.gridheight = 1;
		gridSettings.gridwidth = 3;
		gridSettings.gridx = 0;
		gridSettings.gridy = 1;
		contentPane.add(reportStatus, gridSettings);

		reportResults = new JTextArea();
		reportResults.setEditable(false);
		scrollPane = new JScrollPane(reportResults);
		gridSettings.fill = GridBagConstraints.BOTH;
		gridSettings.weightx = 0.5;
		gridSettings.weighty = 0.5;
		gridSettings.gridheight = 1;
		gridSettings.gridwidth = 3;
		gridSettings.gridx = 0;
		gridSettings.gridy = 2;
		contentPane.add(scrollPane, gridSettings);

		return contentPane;
	}

	/**
	 * used to determine what the user wants to do with the file and relays the
	 * message accordingly.
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Select File")) {
			startFileOpen();
			if (filePath != null)
				wordCounter = new WordCounter(filePath);
			reportStatus.setText("File For Analysis Selected:'"
					+ filePath.toString() + "'");
		}
		if (action.equals("Generate Report")) {
			if (filePath != null) {
				System.out.println(reportResults.getClass());
				wordCounter.exportResults(reportResults);
				reportStatus.setText("Analyzed:'" + filePath.toString() + "'");
			} else
				JOptionPane
						.showMessageDialog(null,
								"You must select a file to be analyzed before you can generate a report");
		}
		if (action.equals("Export Report")) {
			startFileSave();
			if (filePath == null)
				JOptionPane
						.showMessageDialog(null,
								"You must select a file to be analyzed before you can export a report");
			else {
				reportStatus.setText("Exporting Results to:'"
						+ filePath.toString() + "'");
				System.out.println(filePath.getClass());
				wordCounter.exportResults(filePath);
			}
		}
	}

	/**
	 * Have the user select the a file to be analyzed and assure they are only
	 * opening a file
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
	 * If the user selects .txt or .html save the file to the specified output
	 * filepath
	 */
	public void startFileSave() {
		JFileChooser fileOpener = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Accepted Report Formats", "txt", "html");
		// filter = new FileNameExtensionFilter()
		// ensures user does not select a directory
		fileOpener.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileOpener.setFileFilter(filter);
		try {
			if (fileOpener.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				filePath = fileOpener.getSelectedFile();

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
		InitApp userInterface = new InitApp();
		frame.setContentPane(userInterface.createContentPane());

		// Display the window.
		frame.setSize(400, 400);
		frame.setVisible(true);
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