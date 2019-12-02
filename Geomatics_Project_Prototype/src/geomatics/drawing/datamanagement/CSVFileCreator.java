package geomatics.drawing.datamanagement;

import geomatics.drawing.components.ObjectManager;
import geomatics.drawing.datamanagement.CSVInterface;
import geomatics.drawing.geometries.ToolPoint;
import geomatics.drawing.geometries.ToolLine;
import geomatics.drawing.geometries.ToolTriangle;
import geomatics.drawing.geometries.ToolRectangle;

import static geomatics.drawing.components.ToolFrame.toolframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

/**
 * Creating CSV file
 * @author ghsa1011
 * 
 */

public class CSVFileCreator {
	
	public CSVInterface csv_interface;
	public static ObjectManager objectmanager;
	Formatter fileFormatter;
	
	FileWriter csvWriter;
	
	String objectType;
	String objectGeometry;
	
	String filePath;
	
	public CSVFileCreator() {
		CSVFileCreator.objectmanager = toolframe.objectmanager;		
	}
	
	/**
	 * Opening CSV file with defining path and format 
	 * @author ghsa1011
	 */
	public void openFile() {		
		try {
			filePath =  CSVInterface.getFilePath();
			fileFormatter = new Formatter(filePath);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("process of saving was unexpectedly interupted");
		}
	}
	
	/**
	 * Close the CSV file 
	 * @author ghsa1011
	 */
	public void closeFile() {
		fileFormatter.close();
	}
	
	/**
	 * Writing objects (point,line,triangle,rectangle) to the CSV file with the type and geometry information with ';' delimiter and save it as a format of .csv
	 * @param filePath String that contains the path of the file in what the geometries should be stored in 
	 * @author ghsa1011
	 */
	public boolean fillFile(String filePath) {
		try {
			csvWriter = new FileWriter(new File(filePath));
			csvWriter.write("object_type;object_geometry" + "\n");	
			
			/*
			if (objectmanager.managedToolLines.size() >=2) {
				objectmanager.managedToolLines.remove(objectmanager.managedToolLines.size()-1);
			}
			
			if (objectmanager.managedToolTriangles.size() >=2) {
				objectmanager.managedToolTriangles.remove(objectmanager.managedToolTriangles.size()-1);
			}
			
			if (objectmanager.managedToolRectangles.size() >=2) {
				objectmanager.managedToolRectangles.remove(objectmanager.managedToolRectangles.size()-1);
			}	
			*/		
			
			objectmanager.managedToolPoints.forEach((ToolPoint point) -> {
				objectType = point.getType();
				objectGeometry = point.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			objectmanager.managedToolLines.forEach((ToolLine line) -> {
				objectType = line.getType();
				objectGeometry = line.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});	
			
			objectmanager.managedToolTriangles.forEach((ToolTriangle triangle) -> {
				objectType = triangle.getType();
				objectGeometry = triangle.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			objectmanager.managedToolRectangles.forEach((ToolRectangle rectangle) -> {
				objectType = rectangle.getType();
				objectGeometry = rectangle.getGeometryAsText();
				try {
					csvWriter.write(objectType + ";" + objectGeometry + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			csvWriter.close();						
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
