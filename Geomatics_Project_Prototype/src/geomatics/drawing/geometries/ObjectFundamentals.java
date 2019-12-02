package geomatics.drawing.geometries;

import java.util.ArrayList;
import java.util.Collections;

import geomatics.drawing.components.ObjectManager;
import static geomatics.drawing.components.ToolFrame.toolframe;

/**
 * Parent class of ToolPoint, ToolLine, ToolTriangle and ToolRectangle that provides methods 
 * for the common parameters ID and Geometry Type
 * @author heol1015
 */
public class ObjectFundamentals {
	
	/**
	 * A unique ID for every geometry object
	 */
	public int identifier = setIdentifier(toolframe.objectmanager);
	
	/**
	 * The type of geometry
	 * (see constructor of ObjectFundamentals and constructor of ToolPoint, ToolLine, ToolTriangle and ToolRectangle
	 */
	public String objectType;
	
	/**
	 * Constructor sets the type of the objects.
	 * (see: Constructors of ToolLine, ToolTriangle, ToolRectangle)
	 * @author heol1015
	 * @param objectType String provided by the constructor of the geometrie objects when created
	 */
	public ObjectFundamentals (String objectType) {
		this.objectType = objectType;
	}
	
	/**
	 * Returns the identifier of an object.
	 * @author heol1015
	 * @return Identifier of the object
	 */
	public int getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Returns the type of an object.
	 * @author heol1015
	 * @return Type of the object
	 */
	public String getType() {
		return this.objectType;
	}

	/**
	 * Iterates through every objects of every type of geometry and adds every identifier
	 * to the identifierList, as long as the length of the identifierList is greater 0, the identifier that 
	 * is returned is the length of the identifierList + 1
	 * @author heol1015
	 * @param objectmanager ObjectManager object that provides the ArrayLists of geometry objects to iterate through
	 * @return New unique identifier
	 */
	public int setIdentifier(ObjectManager objectmanager) {
		ArrayList<Integer> identifierList = new ArrayList<Integer>();
		int latestIdentifier;
		
		objectmanager.managedToolPoints.forEach((ToolPoint point) -> {
			identifierList.add(point.getIdentifier());
		});
		objectmanager.managedToolLines.forEach((ToolLine line) -> {
			identifierList.add(line.getIdentifier());
		});
		objectmanager.managedToolTriangles.forEach((ToolTriangle triangle) -> {
			identifierList.add(triangle.getIdentifier());
		});
		objectmanager.managedToolRectangles.forEach((ToolRectangle rectangle) -> {
			identifierList.add(rectangle.getIdentifier());
		});
		objectmanager.selectedToolPoints.forEach((ToolPoint point) -> {
			identifierList.add(point.getIdentifier());
		});
		objectmanager.selectedToolLines.forEach((ToolLine line) -> {
			identifierList.add(line.getIdentifier());
		});
		objectmanager.selectedToolTriangles.forEach((ToolTriangle triangle) -> {
			identifierList.add(triangle.getIdentifier());
		});
		objectmanager.selectedToolRectangles.forEach((ToolRectangle rectangle) -> {
			identifierList.add(rectangle.getIdentifier());
		});
		
		if (identifierList.isEmpty()) {
			latestIdentifier = 0;
		} else {
			latestIdentifier = Collections.max(identifierList);
		}
		return latestIdentifier+1;
	}

}
