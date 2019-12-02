package geomatics.drawing.components;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import geomatics.drawing.geometries.ToolLine;
import geomatics.drawing.geometries.ToolPoint;
import geomatics.drawing.geometries.ToolRectangle;
import geomatics.drawing.geometries.ToolTriangle;

/**
 * The ObjectManager class stores all objects that are drawn and provides methods for 
 * adding, selecting and deleting them.
 * @author heol1015
 *
 */
public class ObjectManager {
	
	/**
	 * ArrayList of ToolPoints objects that are drawn
	 */
	public ArrayList<ToolPoint> managedToolPoints = new ArrayList<>();
	/**
	 * ArrayList of ToolLines objects that are drawn
	 */
	public ArrayList<ToolLine> managedToolLines = new ArrayList<>();
	/**
	 * ArrayList of ToolTriangles objects that are drawn
	 */
	public ArrayList<ToolTriangle> managedToolTriangles = new ArrayList<>();
	/**
	 * ArrayList of ToolRectangles objects that are drawn
	 */
	public ArrayList<ToolRectangle> managedToolRectangles = new ArrayList<>();
	
	
	/**
	 * ArrayList of ToolPoints Objects that are currently selected
	 */
	public ArrayList<ToolPoint> selectedToolPoints = new ArrayList<>();
	/**
	 * ArrayList of ToolLines Objects that are currently selected
	 */
	public ArrayList<ToolLine> selectedToolLines = new ArrayList<>();
	/**
	 * ArrayList of ToolTriangles Objects that are currently selected
	 */
	public ArrayList<ToolTriangle> selectedToolTriangles = new ArrayList<>();
	/**
	 * ArrayList of ToolRectangles Objects that are currently selected
	 */
	public ArrayList<ToolRectangle> selectedToolRectangles = new ArrayList<>();		
	
	/**
	 * Adds a ToolPoint at the end of the corresponding ArrayList.
	 * @author heol1015
	 * @param toolpoint Type of Geometry
	 */
	public void storePoint( ToolPoint toolpoint) {
		managedToolPoints.add(toolpoint);
	}
	
	/**
	 * Adds a ToolLine at the end of the corresponding ArrayList.
	 * @author heol1015
	 * @param toolline Type of Geometry
	 */	
	public void storeLineElements(ToolLine toolline) {
		managedToolLines.add(toolline);
	}
	
	/**
	 * Adds a ToolTriangle at the end of the corresponding ArrayList.
	 * @author heol1015
	 * @param tooltriangle Type of Geometry
	 */	
	public void storeTriangleElements(ToolTriangle tooltriangle) {
		managedToolTriangles.add(tooltriangle);
	}
	
	/**
	 * Adds a ToolRectangle at the end of the corresponding ArrayList.
	 * @author heol1015
	 * @param toolrectangle Type of Geometry
	 */	
	public void storeRectangleElements(ToolRectangle toolrectangle) {
		managedToolRectangles.add(toolrectangle);
	}
	
	/**
	 * Clears the ArrayLists that contain the objects that are added by the selection rectangle
	 * @author heol1015
	 */	
	public void clearCurrentSelection() {
		selectedToolPoints = new ArrayList<>();
		selectedToolLines = new ArrayList<>();
		selectedToolTriangles = new ArrayList<>();
		selectedToolRectangles = new ArrayList<>();
	}
	
	/**
	 * Iterates through every object of every type of geometry and calculates whether the objects lies 
	 * inside the selection rectangle or outside.
	 * If the objects lies inside the selection rectangle it will be added to the ArrayList of selected objects.
	 * The function contains() is provided by Java.
	 * @author heol1015
	 * @param selection_rectangle Selection rectangle defined by the user
	 */
	public void selectAffectedToolObjects(Rectangle2D selection_rectangle) {
		managedToolPoints.forEach((ToolPoint point) -> {
			Ellipse2D point_object 		= point.createToolPoint();
			Rectangle2D queryArea		= point_object.getBounds2D();
			
			//if selection_rectangle contains the object then add this object so the selectedToolPoints
			if (selection_rectangle.contains(queryArea) == true) {
				selectedToolPoints.add(point);
			}
		});
		
		managedToolLines.forEach((ToolLine line) -> {
			Line2D line_object 			= line.createToolLine();
			Rectangle2D queryArea		= line_object.getBounds2D();
			
			//if selection_rectangle contains the object then add this object so the selectedToolLines
			if ( selection_rectangle.contains(queryArea) == true) {
				selectedToolLines.add(line);
			}
		});
		
		managedToolTriangles.forEach((ToolTriangle triangle) -> {
			Path2D triangle_object 		= triangle.createToolTriangle();
			Rectangle2D queryArea		= triangle_object.getBounds2D();
			
			//if selection_rectangle contains the object then add this object so the selectedToolTriangles
			if (selection_rectangle.contains(queryArea) == true) {
				selectedToolTriangles.add(triangle);
			}
		});
		
		managedToolRectangles.forEach((ToolRectangle rectangle) -> {
			Rectangle2D rectangle_object 			= rectangle.createToolRectangle();
			Rectangle2D queryArea		= rectangle_object.getBounds2D();
			
			//if selection_rectangle contains the object then add this object so the selectedToolRectangles
			if ( selection_rectangle.contains(queryArea) == true) {
				selectedToolRectangles.add(rectangle);
			}
		});
	}
	
	/**
	 * Iterates through every object of every type of geometry in the ArrayList of the selected objects
	 * and requests the id of the objects. After that it iterates through the ArrayLists of objects that
	 * are currently displayed and deletes objects with the same id as the object in the ArrayLists of selected objects.
	 * @author heol1015
	 */
	public void deleteAffectedToolObjects() {
		selectedToolPoints.forEach((ToolPoint point) -> {
			int identifier = point.getIdentifier();
			deletePoint(identifier);
		});
		
		selectedToolLines.forEach((ToolLine line) -> {
			int identifier = line.getIdentifier();
			deleteLine(identifier);
		});
		
		selectedToolTriangles.forEach((ToolTriangle triangle) -> {
			int identifier = triangle.getIdentifier();
			deleteTriangle(identifier);
		});
		
		selectedToolRectangles.forEach((ToolRectangle rectangle) -> {
			int identifier = rectangle.getIdentifier();
			deleteRectangle(identifier);
		});
	}
	
	/**
	 * Iterates through every drawn ToolPoint in the corresponding ArrayList and deletes the object with the 
	 * same id as the provided id in the parameter.
	 * @author heol1015
	 * @param identifier Identifies the point that should be deleted
	 */
	private void deletePoint(int identifier) {
		//loop through every point of managedToolPoints
		for ( int i=0; i<managedToolPoints.size(); i++) {
			ToolPoint point = managedToolPoints.get(i);
			int pointIdentifier = point.getIdentifier();
			//if identifier from selectedToolPoints and from managedToolPoints match then delete this point
			if (pointIdentifier == identifier) {
				managedToolPoints.remove(i);
				}
			}
	}
	
	/**
	 * Iterates through every drawn ToolLine in the corresponding ArrayList and deletes the object with the 
	 * same id as the provided id in the parameter.
	 * @author heol1015
	 * @param identifier Identifies the line that should be deleted
	 */
	private void deleteLine(int identifier) {
		//loop through every line of managedToolLines
		for ( int i=0; i<managedToolLines.size(); i++) {
			ToolLine line = managedToolLines.get(i);
			int lineIdentifier = line.getIdentifier();
			//if identifier from selectedToolLines and from managedToolLines match then delete this line
			if (lineIdentifier == identifier) {
				managedToolLines.remove(i);
			}
		}
	}
	
	/**
	 * Iterates through every drawn ToolTriangle in the corresponding ArrayList and deletes the object with the 
	 * same id as the provided id in the parameter.
	 * @author heol1015
	 * @param identifier Identifies the triangle that should be deleted
	 */
	private void deleteTriangle(int identifier) {
		//loop through every triangle of managedToolTriangles
		for ( int i=0; i<managedToolTriangles.size(); i++) {
			ToolTriangle triangle = managedToolTriangles.get(i);
			int triangleIdentifier = triangle.getIdentifier();
			//if identifier from selectedToolTriangles and from managedToolTriangles match then delete this triangle
			if (triangleIdentifier == identifier) {
				managedToolTriangles.remove(i);
			}
		}
	}
	
	/**
	 * Iterates through every drawn ToolRectangle in the corresponding ArrayList and deletes the object with the 
	 * same id as the provided id in the parameter.
	 * @author heol1015
	 * @param identifier Identifies the rectangle that should be deleted
	 */
	private void deleteRectangle(int identifier) {
		//loop through every rectangle of managedToolRectangles
		for ( int i=0; i<managedToolRectangles.size(); i++) {
			ToolRectangle rectangle = managedToolRectangles.get(i);
			int triangleIdentifier = rectangle.getIdentifier();
			//if identifier from selectedToolRectangle and from managedToolRectangles match then delete this rectangle
			if (triangleIdentifier == identifier) {
				managedToolRectangles.remove(i);
			}
		}
	}

}
