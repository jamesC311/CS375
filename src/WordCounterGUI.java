import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * This class is designed to creat a GUI for WordCounter, nothing more than an interface
 */
public class WordCounterGUI implements ActionListener {
	JTextField reportStatus;
	JTextArea reportResults;
	JScrollPane scrollPane;
	JButton fileSelect, generateReport, exportReport;
	String newLine = System.getProperty("line.separator");
	WordCounter wc;

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
			wc = new WordCounter();
			reportStatus.setText("File For Analysis Selected:'"
					+ wc.getInFile().toString() + "'");
		}
		if (action.equals("Generate Report")) {
			if (wc.getInFile() != null) {
				wc.exportResults(reportResults);
				reportStatus.setText("Analyzed:'" + wc.getInFile().toString() + "'");
			} else
				JOptionPane
						.showMessageDialog(null,
								"You must select a file to be analyzed before you can generate a report");
		}
		if (action.equals("Export Report")) {
			wc.startFileSave();
			if (wc.getOutFile() == null)
				JOptionPane
						.showMessageDialog(null,
								"You must select a file to be analyzed before you can export a report");

			else{
				reportStatus.setText("Exporting Results to:'" + wc.getOutFile().toString() + "'");
				wc.exportResults(wc.getOutFile());
			}
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
		WordCounterGUI userInterface = new WordCounterGUI();
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