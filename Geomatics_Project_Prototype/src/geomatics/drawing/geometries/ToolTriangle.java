package geomatics.drawing.geometries;

import java.awt.geom.Path2D;

/**
 * Class for TollTriangles that stores triangle coordinates and provides methods for creating drawable triangle geometries for the ToolDrawPanel,
 * or for importing/exporting the triangle coordinates.
 * Inherits from ObjectFundamenals. 
 * @author heol1015
 */
public class ToolTriangle extends ObjectFundamentals {
	
	/**
	 * Stores the first, middle and last ToolPoint of a ToolTriangle object
	 */
	public ToolPoint[] triangleElements = new ToolPoint[3];
	
	/**
	 * The constructor defines the type of geometry
	 * (see: constructor of ObjectFundamentals)
	 * @author heol1015
	 */
	public ToolTriangle() {		
		super("Triangle");
	}
	
	/**
	 * Adds the first point of the triangle as the first entry of the List of triangle elements
	 * @author heol1015
	 * @param point ToolPoint object representing the first corner of the triangle
	 */
	public void addTriangleStart(ToolPoint point) {
		triangleElements[0] = point;
	}
	
	/**
	 * Adds the second point of the triangle as the second entry of the List of triangle elements
	 * @author heol1015 
	 * @param point ToolPoint object representing the second corner of the triangle
	 */
	public void addTriangleMid(ToolPoint point) {
		triangleElements[1] = point;
	}
	
	/**
	 * Adds the last point of the triangle as the third entry of the List of triangle elements
	 * @author heol1015
	 * @param point ToolPoint object representing the last corner of the triangle
	 */
	public void addTriangleEnd(ToolPoint point) {
		triangleElements[2] = point;		
	}
	
	/**
	 * Creates a drawable Path2D object with the points of the ToolTriangle object
	 * @author heol1015
	 * @return Drawable triangle-shaped geometry
	 */
	public Path2D createToolTriangle() {		
		Path2D pathTriangle = new Path2D.Double();
		pathTriangle.moveTo((int)this.triangleElements[0].x, (int)this.triangleElements[0].y);
		pathTriangle.lineTo((int)this.triangleElements[1].x, (int)this.triangleElements[1].y);
		pathTriangle.lineTo((int)this.triangleElements[2].x, (int)this.triangleElements[2].y);
		pathTriangle.lineTo((int)this.triangleElements[0].x, (int)this.triangleElements[0].y);
		return pathTriangle;
	}
	
	/**
	 * Returns the coordinates of the ToolTriangle object as a String
	 * @author heol1015
	 * @return String of coordinates
	 */
	public String getGeometryAsText() {
		String textGeometry 	= 	String.valueOf(this.triangleElements[0].x) + " " + String.valueOf(this.triangleElements[0].y) + " " +
									String.valueOf(this.triangleElements[1].x) + " " + String.valueOf(this.triangleElements[1].y) + " " +
									String.valueOf(this.triangleElements[2].x) + " " + String.valueOf(this.triangleElements[2].y) + " " +
									String.valueOf(this.triangleElements[0].x) + " " + String.valueOf(this.triangleElements[0].y);
		return textGeometry;	
	}
	
	/**
	 * Sets the Triangle from a String (provided by database of '.csv').
	 * @author heol1015
	 * @param csvGeometry String containing the triangle coordinates
	 * @return Whether the operation was successfully operated or not
	 */
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinates = csvGeometry.split(" ");			
			ToolPoint startpoint = new ToolPoint();
			ToolPoint midpoint = new ToolPoint();
			ToolPoint endpoint = new ToolPoint();			
			
			startpoint.setPoint(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			triangleElements[0] = startpoint;
			midpoint.setPoint(Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
			triangleElements[1] = midpoint;
			endpoint.setPoint(Double.parseDouble(coordinates[4]), Double.parseDouble(coordinates[5]));
			triangleElements[2] = endpoint;			
			
			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}
		

}
