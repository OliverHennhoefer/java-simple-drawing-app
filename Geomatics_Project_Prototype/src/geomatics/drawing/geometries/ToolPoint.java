package geomatics.drawing.geometries;

import java.awt.geom.Ellipse2D;

/**
 * Class for ToolPoints that stores point coordinates and provides methods for creating drawable point geometries for the ToolDrawPanel,
 * or for importing/exporting the point coordinates.
 * Inherits from ObjectFundamenals. 
 * @author heol1015
 */
public class ToolPoint extends ObjectFundamentals {
	
	/**
	 * Stores the x-coordinate of the ToolPoint object
	 */
	public double x = 0;	
	/**
	 * Stores the y-coordinate of the ToolPoint object
	 */
	public double y = 0;
	
	/**
	 * The constructor defines the type of geometry
	 * (see: constructor of ObjectFundamentals)
	 * @author heol1015
	 */
	public ToolPoint() {		
		super("Point");
	}
	
	/**
	 * Creates a drawable Ellipse2D object with the points of the ToolPoint object
	 * @author heol1015
	 * @return Drawable point geometry
	 */
	public Ellipse2D createToolPoint() {
		Ellipse2D point = new Ellipse2D.Double(this.x, this.y, 3, 3);
		return point;
	}
	
	/**
	 * Setter for x- and y-coordinate of every ToolPoint
	 * @author heol1015
	 * @param x Sets x-coordinate
	 * @param y Sets y-coordinate
	 * @return 	Whether the coordinates were set successfully
	 */
	public boolean setPoint( double x, double y) {		
		this.x = x;
		this.y = y;
		return true;		
	}
	
	/**
	 * Returns the coordinates of the ToolPoint object as a String
	 * @author heol1015
	 * @return String of coordinates
	 */
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.x) + " " + String.valueOf(this.y);
		return textGeometry;
	}
	
	/**
	 * Sets the coordinates from a String (provided by database or '.csv').
	 * @author heol1015
	 * @param csvGeometry String containing the point coordinate
	 * @return 	Whether the operation was successfully operated or not
	 */
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinatesPoint = csvGeometry.split(" ");
			this.x = Double.parseDouble(coordinatesPoint[0]);
			this.y = Double.parseDouble(coordinatesPoint[1]);
			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}

}
