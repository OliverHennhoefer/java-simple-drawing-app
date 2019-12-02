package geomatics.drawing.datamanagement;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static geomatics.drawing.components.ToolFrame.toolframe;
import geomatics.drawing.components.ObjectManager;
import geomatics.drawing.datamanagement.DBAccessor;

@SuppressWarnings("serial")
public class DBInterface extends JFrame implements ActionListener {
	
	DBAccessor dbaccessor;
	ObjectManager objectmanager;
	ObjectManager newobjectmanager;
	
	JLabel labelDBMS;
	JLabel labelHost;
	JLabel labelPort;
	JLabel labelUsername;
	JLabel labelPassword;
	JLabel labelDbName;
	JLabel labelDbTable;
	
	JTextField textDBMS;
	JTextField textHost;
	JTextField textPort;
	JTextField textUsername;
	JPasswordField textPassword;
	JTextField textDbName;
	JTextField textDbTable;
	
	JLabel textConnectCheck;
	
	JButton connectDb;
	JButton saveToDb;
	JButton importFromDb;
	
	/**
	 * Setting setLayout() method to the database interface  which contains the elements of database interface
	 * @author ghsa1011
	 */
	public DBInterface() {
		this.objectmanager = toolframe.objectmanager;
		setLayout();
	}
	
	/**
	 * Defining the Layout of Database interface by adding elements of database interface
	 * @author ghsa1011
	 */
	private void setLayout() {
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		
		textConnectCheck = new JLabel();
		add(textConnectCheck);
		textConnectCheck.setVisible(false);
		
		labelDBMS = new JLabel("Database Management System:");
		labelDBMS.setForeground(Color.decode("#3366ff"));
		add(labelDBMS);
		textDBMS = new JTextField("mysql", 20);		
		add(textDBMS);
		
		labelHost = new JLabel("Host:");
		labelHost.setForeground(Color.decode("#3366ff"));
		add(labelHost);
		textHost = new JTextField("localhost", 20);
		add(textHost);
		
		labelPort = new JLabel("Port:");
		labelPort.setForeground(Color.decode("#3366ff"));
		add(labelPort);
		textPort = new JTextField("3306", 20);
		add(textPort);
		
		labelUsername = new JLabel("Username:");
		labelUsername.setForeground(Color.decode("#3366ff"));
		add(labelUsername);
		textUsername = new JTextField("root", 20);
		add(textUsername);
		
		labelPassword = new JLabel("Password:");
		labelPassword.setForeground(Color.decode("#3366ff"));
		add(labelPassword);
		textPassword = new JPasswordField("1234.-abcABC", 20);
		add(textPassword);
		
		labelDbName = new JLabel("Database Name:");
		add(labelDbName);
		textDbName = new JTextField("test_database", 20);
		add(textDbName);
		
		labelDbTable = new JLabel("Table Name:");
		add(labelDbTable);
		textDbTable = new JTextField("toolobjects_db", 20);
		textDbTable.setEditable(false);
		add(textDbTable);
		
		connectDb = new JButton("Connect to Database");
		connectDb.setBackground(Color.decode("#a3bbf7"));
		add(connectDb);		
		saveToDb = new JButton("Export to Database");
		saveToDb.setBackground(Color.decode("#e6ffb3"));
		add(saveToDb);
		importFromDb = new JButton("Import from Database");
		importFromDb.setBackground(Color.decode("#e6ffb3"));
		add(importFromDb);
		
		connectDb.addActionListener(this);
		saveToDb.addActionListener(this);
		importFromDb.addActionListener(this);	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(265,500);
		setResizable(false);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eTarget = e.getSource();
		
		if( eTarget.equals(connectDb)) {
			connectDatabase();
		} else if ( eTarget.equals(saveToDb)) {
			try {
				saveToDb();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (eTarget.equals(importFromDb)) {
			try {
				newobjectmanager = dbaccessor.extractToolObject();
				toolframe.overwriteObjects(newobjectmanager);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * Checking the Database connection status
	 * @author ghsa1011
	 */
	public void connectDatabase() {
		try {
			getConnection();
			textConnectCheck.setVisible(true);
			textConnectCheck.setForeground(Color.GREEN);
			textConnectCheck.setText("successfully connected");
		} catch (SQLException e) {
			textConnectCheck.setText("no connection");
			textConnectCheck.setForeground(Color.RED);
			textConnectCheck.setVisible(true);
			e.printStackTrace();
		}
	}
	
	/**
	 * Getting connection from database by db name,host,port,username and password 
	 * @return Connection object for connecting to, an operating with the database
	 * @throws SQLException Throws Exception when there were any failings by operating with SQL commands or with the MySQL database
	 * @author ghsa1011
	 */
	public Connection getConnection() throws SQLException {
		dbaccessor = new DBAccessor();
		dbaccessor.DBMS = (String) textDBMS.getText();
		dbaccessor.dbName = (String) textDbName.getText();
		dbaccessor.dbHost = (String) textHost.getText();
		dbaccessor.dbPort = (String) textPort.getText();
		dbaccessor.dbUser = (String) textUsername.getText();
		dbaccessor.dbPassword = (String) textPassword.getText();
		
		return dbaccessor.accessorConnection();
	}
	
	/**
	 * Save objects to the database by creating table 
	 * @throws SQLException Throws Exception when there were any failings by operating with SQL commands or with the MySQL database
	 * @author ghsa1011
	 */
	public void saveToDb() throws SQLException {
		String dbName = textDbName.getText();
		createTable();
		deleteEntries();
		dbaccessor.insertToolObjects(objectmanager);
		JOptionPane.showMessageDialog(null, "Saved to toolobjects_db in " + dbName);
	}
	
	/**
	 * Creates a table 'toolobject_db' in the database if this table doesn't already exist
	 * [Added in the course of the Module Integration Test]
	 * @throws SQLException Throws Exception when there were any failings by operating with SQL commands or with the MySQL database
	 * @author heol1014
	 */
	public void createTable() throws SQLException {
		Connection connection = getConnection();
		PreparedStatement createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+
				"toolobjects_db (gid int NOT NULL AUTO_INCREMENT, "
					+ "type varchar(10), "
					+ "geom longtext, "
					+ "PRIMARY KEY(gid))");
		createTable.executeUpdate();
	}
	
	/**
	 * Delete Entries and truncate toolobjects_db database 
	 * @throws SQLException Throws Exception when there were any failings by operating with SQL commands or with the MySQL database
	 * @author ghsa1011
	 */
	public void deleteEntries() throws SQLException {
		Connection connection = getConnection();
		PreparedStatement truncateDatabase = connection.prepareStatement("TRUNCATE toolobjects_db");
		truncateDatabase.executeUpdate();
	}

}
