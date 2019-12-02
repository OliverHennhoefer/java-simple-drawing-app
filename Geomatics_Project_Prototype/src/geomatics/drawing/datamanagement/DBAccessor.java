package geomatics.drawing.datamanagement;

import geomatics.drawing.components.ObjectManager;
import geomatics.drawing.geometries.ToolPoint;
import geomatics.drawing.geometries.ToolLine;
import geomatics.drawing.geometries.ToolTriangle;
import geomatics.drawing.geometries.ToolRectangle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBAccessor {
	
	public String DBMS;
	public String dbName;
	public String dbHost;
	public String dbPort;
	public String dbUser;
	public String dbPassword;
	public String tableName;	
	
	int countIdentifier = 0;
	
	/**
	 * Making connection with database based on database user and password and database port 
	 * @return Connection object for connecting to, an operating with the database
	 * @throws SQLException Throws Exception when there were any failings by operating with SQL commands or with the MySQL database
	 * @author ghsa1011
	 */
	public Connection accessorConnection() throws SQLException {
		String connection =  "jdbc:" + DBMS +"://" + dbHost + ":" + dbPort + "/" +	dbName + "?autoReconnect=true&useSSL=false&serverTimezone=UTC";
		Connection dbmsConnection = DriverManager.getConnection(connection, dbUser,  dbPassword);
		return dbmsConnection;
	}
	
	/**
	 * Inserting objects in to the toolobjects_db with information of object type and object geometry 
	 * @param objectmanager ObjectManager object for storing the geometry object
	 * @throws SQLException Throws Exception when there were any failings by operating with SQL commands or with the MySQL database
	 * @author ghsa1011
	 */
	public void insertToolObjects(ObjectManager objectmanager) throws SQLException {
		Connection connection = accessorConnection();
		
		String objectType;
		String objectGeometry;
		
		for (ToolPoint point : objectmanager.managedToolPoints) {
			objectType = point.getType();
			objectGeometry = point.getGeometryAsText();
			PreparedStatement insertToolPoint = connection.prepareStatement("INSERT INTO toolobjects_db"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertToolPoint.executeUpdate();
		}
		
		for (ToolLine line : objectmanager.managedToolLines) {
			objectType = line.getType();
			objectGeometry = line.getGeometryAsText();
			PreparedStatement insertToolLine = connection.prepareStatement("INSERT INTO toolobjects_db"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertToolLine.executeUpdate();
		}
		
		for (ToolTriangle triangle : objectmanager.managedToolTriangles) {
			objectType = triangle.getType();
			objectGeometry = triangle.getGeometryAsText();
			PreparedStatement insertToolTriangle = connection.prepareStatement("INSERT INTO toolobjects_db"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertToolTriangle.executeUpdate();
		}
		
		for (ToolRectangle rectangle : objectmanager.managedToolRectangles) {
			objectType = rectangle.getType();
			objectGeometry = rectangle.getGeometryAsText();
			PreparedStatement insertToolRectangle = connection.prepareStatement("INSERT INTO toolobjects_db"+
					"(type, geom) VALUES('" + objectType + "','" + objectGeometry + "')");
			insertToolRectangle.executeUpdate();
		}
	}
	
	/**
	 * Extract objects from toolobjects_db based on object type and object geometry and overwite to the old objects if they exist 
	 * @return ObjectManager object containing the geometry objects extracted from the database
	 * @throws SQLException Throws Exception when there were any failings by operating with SQL commands or with the MySQL database
	 * @author ghsa1011
	 */
	public ObjectManager extractToolObject() throws SQLException {
		ObjectManager newobjectmanager = new ObjectManager();
		
		Connection connection = accessorConnection();
		java.sql.ResultSet resultSet;
		PreparedStatement displayObjects;
		
		displayObjects = connection.prepareStatement("SELECT * FROM toolobjects_db");
		resultSet = displayObjects.executeQuery();
		
		while (resultSet.next()) {
			String objectType = resultSet.getString("type");
			String objectGeometry = resultSet.getString("geom");
			
			switch(objectType) {
			
				case "Point":
					ToolPoint point = new ToolPoint();
					point.setGeometryFromCSV(objectGeometry);
					newobjectmanager.storePoint(point);
					
					countIdentifier++;
					point.identifier = countIdentifier;
					break;
					
				case "Line":
					ToolLine line = new ToolLine();
					line.setGeometryFromCSV(objectGeometry);
					newobjectmanager.storeLineElements(line);
					
					countIdentifier++;
				    line.identifier = countIdentifier;
					break;
					
				case "Triangle":
					ToolTriangle triangle = new ToolTriangle();
					triangle.setGeometryFromCSV(objectGeometry);
					newobjectmanager.storeTriangleElements(triangle);
					
					countIdentifier++;
				    triangle.identifier = countIdentifier;
					break;
					
				case "Rectangle":
					ToolRectangle rectangle = new ToolRectangle();
					rectangle.setGeometryFromCSV(objectGeometry);
					newobjectmanager.storeRectangleElements(rectangle);
					
					countIdentifier++;
				    rectangle.identifier = countIdentifier;
					break;
			}
			
		}
		/*
		if (newobjectmanager.managedToolLines.size() >=2) {
			System.out.println(newobjectmanager.managedToolLines.size());
			newobjectmanager.managedToolLines.remove(newobjectmanager.managedToolLines.size()-1);
		}
		
		if (newobjectmanager.managedToolTriangles.size() >=2) {
			newobjectmanager.managedToolTriangles.remove(newobjectmanager.managedToolTriangles.size()-1);
		}
		
		if (newobjectmanager.managedToolRectangles.size() >=2) {
			newobjectmanager.managedToolRectangles.remove(newobjectmanager.managedToolRectangles.size()-1);
		}	
		*/
		
		countIdentifier = 0;
		resultSet.close();
		displayObjects.close();
		return newobjectmanager;
	}
	

}
