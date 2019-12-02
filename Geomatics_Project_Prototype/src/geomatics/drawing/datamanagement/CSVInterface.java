package geomatics.drawing.datamanagement;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import geomatics.drawing.components.ObjectManager;
import static geomatics.drawing.components.ToolFrame.toolframe;

@SuppressWarnings("serial")
public class CSVInterface extends JFrame implements ActionListener {
	
	public static ObjectManager objectmanager;
	
	JButton save;
	JButton open;
	
	ObjectManager newobjectmanager;
	
	static String filePath;
	String fileName;
	
	/**
	 * Setting setLayout() method to the CSV interface  which contains the elements of CSV interface 
	 * @author ghsa1011
	 */
	public CSVInterface() {	
		setLayout();
	}
	
	/**
	 * Defining the Layout of CSV interface 
	 * @author ghsa1011
	 */
	private void setLayout() {
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		
		save 	= new JButton("Save");
		save.setBackground(Color.decode("#e6ffb3"));
		save.setPreferredSize(new Dimension(130, 75));
		add(save);
		
		open 	= new JButton("Open");
		open.setBackground(Color.decode("#a3bbf7"));
		open.setPreferredSize(new Dimension(130, 75));
		add(open);
		
		save.addActionListener(this);
		open.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,123);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eTarget = e.getSource();
		
		if ( eTarget.equals(save)) {
			try {
				saveFileChooserDialog();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				saveObjectsToCSV();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		if ( eTarget.equals(open)) {
			try {
				openFileChooserDialog();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				displayObjectFromCSV();
			} catch (Exception e1) {
				e1.printStackTrace();
			}	
		}
	}
	
	/**
	 * Defining openFileChooserDialog for opening the CSV file
	 * @throws Exception Throws Exception when the file couldn't be opened
	 * @author ghsa1011
	 */
	public void openFileChooserDialog() throws Exception {
		JFileChooser jfilechooser = new JFileChooser();
		int filechoose = jfilechooser.showOpenDialog(null);
		while( filechoose == JFileChooser.APPROVE_OPTION && !jfilechooser.getSelectedFile().getName().endsWith(".csv")) {
			JOptionPane.showMessageDialog(null, "The file " + jfilechooser.getSelectedFile() + " is not a csv file!","Error", JOptionPane.ERROR_MESSAGE);
			filechoose = jfilechooser.showOpenDialog(this);
		}
		
		if ( filechoose == JFileChooser.APPROVE_OPTION) {
			File f = jfilechooser.getSelectedFile();
			filePath = f.getAbsolutePath();
			fileName = f.getName();
		}
	}
	
	/**
	 * Defining saveFileChooserDialog for saving the CSV file 
	 * @throws Exception Throws Exception when the file couldn't be saved
	 * @author ghsa1011
	 */
	public void saveFileChooserDialog() throws Exception {
		JFileChooser jfilechooser = new JFileChooser();
		int filechoose = jfilechooser.showSaveDialog(null);
		while( filechoose == JFileChooser.APPROVE_OPTION && !jfilechooser.getSelectedFile().getName().endsWith(".csv")) {
			JOptionPane.showMessageDialog(null, "The file " + jfilechooser.getSelectedFile() + " is not a csv file!","Error", JOptionPane.ERROR_MESSAGE);
			filechoose = jfilechooser.showOpenDialog(this);
		}
		
		if ( filechoose == JFileChooser.APPROVE_OPTION) {
			File f = jfilechooser.getSelectedFile();
			filePath = f.getAbsolutePath();
			fileName = f.getName();
		}
	}
	
	/**
	 * Saving objects to the CSV file with name and path 
	 * @throws Exception Throws Exception when there were any failings by opening, filling or closing the file
	 * @author ghsa1011
	 */
	public void saveObjectsToCSV() throws Exception {
		CSVFileCreator csvFileCreator = new CSVFileCreator();
		csvFileCreator.openFile();
		csvFileCreator.fillFile(filePath);
		csvFileCreator.closeFile();
		JOptionPane.showMessageDialog(null, "Saved as " + fileName + " in " + filePath);
	}
	
	/**
	 * Displaying objects from CSV file 
	 * @throws Exception Throws Exception when there were any failings by displaying or overwriting objects
	 * @author ghsa1011
	 */
	public void displayObjectFromCSV() throws Exception {
		CSVFileDisplayer  csvfiledisplayer = new CSVFileDisplayer(filePath);
		newobjectmanager = csvfiledisplayer.displayObjects();
		toolframe.overwriteObjects(newobjectmanager);
	}
	
	/**
	 * Getting file path 
	 * @return Returns the file path
	 * @author ghsa1011
	 */
	public static String getFilePath() {
		return  filePath;
	}

}
