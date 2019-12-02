package geomatics.drawing.datamanagement;

import static geomatics.drawing.components.ToolFrame.toolframe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import geomatics.drawing.components.ObjectManager;
import geomatics.drawing.geometries.ToolLine;
import geomatics.drawing.geometries.ToolPoint;
import geomatics.drawing.geometries.ToolRectangle;
import geomatics.drawing.geometries.ToolTriangle;

/**
 * Displaying CSV file
 * @author ghsa1011
 *
 */

public class CSVFileDisplayer {
	
	String filePath;
	
	public static ObjectManager objectmanager;
	
	int countIdentifier = 0;
	
	/**
	 * Getting path from the file for Displaying 
	 * @param filePath String that contains the file path that shall be displayed
	 * @author ghsa1011
	 */
	public CSVFileDisplayer(String filePath) {
		this.filePath = filePath;
		CSVFileDisplayer.objectmanager = toolframe.objectmanager;	
	}
	
	/**
	 * Displaying objects by reading them from CSV file 
	 * @return Returns ObjectManager object where the geometry objects from the '.csv' file are written in 
	 * @throws FileNotFoundException Throws Exception when the file couldn't be found
	 * @throws IOException Throws Exception when there were any failings when reading data
	 * @author ghsa1011
	 */
	public ObjectManager displayObjects() throws FileNotFoundException, IOException {
		ObjectManager objectmanager = new ObjectManager();
		
		boolean containsHeader = true;
		String fileLine = "";
		
		try ( BufferedReader bufferedreader = Files.newBufferedReader(Paths.get(filePath))) {
			
			while ((fileLine = bufferedreader.readLine()) != null) {
				
				if (containsHeader == true) {
					containsHeader = false;
					continue;
				}
				
				String[] fileRow = fileLine.split(";");
				
				String objectType = fileRow[0];
				String objectGeometry = fileRow[1];
				System.out.println(objectType);
				System.out.println(fileRow[1]);
				
				
				
				switch(objectType) {
					case "Point":
						ToolPoint point = new ToolPoint();
						point.setGeometryFromCSV(objectGeometry);
						objectmanager.storePoint(point);
	
						countIdentifier++;
						point.identifier = countIdentifier;
						System.out.println(point.identifier);
						break;	
					
					case "Line":
						ToolLine line = new ToolLine();
						line.setGeometryFromCSV(objectGeometry);
					    objectmanager.storeLineElements(line);
					    
					    countIdentifier++;
					    line.identifier = countIdentifier;
					    System.out.println(line.identifier);
					    break;
									    
					case "Triangle":
						ToolTriangle triangle = new ToolTriangle();
						triangle.setGeometryFromCSV(objectGeometry);
						objectmanager.storeTriangleElements(triangle);
						
						countIdentifier++;
					    triangle.identifier = countIdentifier;
					    System.out.println(triangle.identifier);
						break;
						
						
					case "Rectangle":
						ToolRectangle rectangle = new ToolRectangle();
						rectangle.setGeometryFromCSV(objectGeometry);
						objectmanager.storeRectangleElements(rectangle);
						
						countIdentifier++;
					    rectangle.identifier = countIdentifier;
					    System.out.println(rectangle.identifier);
						break;											
															
				}				
				
			}
			
			/*
			if (objectmanager.managedToolLines.size() >=2) {
				System.out.println(objectmanager.managedToolLines.size());
				objectmanager.managedToolLines.remove(objectmanager.managedToolLines.size()-1);
			}
			
			if (objectmanager.managedToolTriangles.size() >=2) {
				objectmanager.managedToolTriangles.remove(objectmanager.managedToolTriangles.size()-1);
			}
			
			if (objectmanager.managedToolRectangles.size() >=2) {
				objectmanager.managedToolRectangles.remove(objectmanager.managedToolRectangles.size()-1);
			}	
			*/
			
		}
		countIdentifier=0;
		return objectmanager;
	}

}
