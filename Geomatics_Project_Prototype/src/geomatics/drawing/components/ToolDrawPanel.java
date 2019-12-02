package geomatics.drawing.components;

import java.awt.Color;
import java.awt.Graphics; 
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import geomatics.drawing.geometries.ToolLine;
import geomatics.drawing.geometries.ToolPoint;
import geomatics.drawing.geometries.ToolRectangle;
import geomatics.drawing.geometries.ToolTriangle;

/**
 * The ToolDrawPanel class requests ArrayLists with geometry objects from an ObjectManager object and displays them.
 * @author heol1015
 *
 */
@SuppressWarnings("serial")
public class ToolDrawPanel extends JPanel{
	
	/**
	 * ArrayList of ToolPoint objects to be drawn
	 */
	private ArrayList<ToolPoint> toolPointList = new ArrayList<>();
	/**
	 * ArrayList of ToolLine objects to be drawn
	 */
	private ArrayList<ToolLine> toolLineList = new ArrayList<>();
	/**
	 * ArrayList of ToolTriangle objects to be drawn
	 */
	private ArrayList<ToolTriangle> toolTriangleList = new ArrayList<>();
	/**
	 * ArrayList of ToolRectangle objects to be drawn
	 */
	private ArrayList<ToolRectangle> toolRectangleList = new ArrayList<>();
	
	
	
	/**
	 * ArrayList of ToolPoint objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<ToolPoint> pointList_selection = new ArrayList<>();
	/**
	 * ArrayList of ToolLine objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<ToolLine> lineList_selection = new ArrayList<>();
	/**
	 * ArrayList of ToolTriangle objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<ToolTriangle> triangleList_selection = new ArrayList<>();
	/**
	 * ArrayList of ToolRectangle objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<ToolRectangle> rectangleList_selection = new ArrayList<>();
	
	/*
	 * [Added in the course of the Module Integration Test]
	 * @author heol1015
	 */
	
	/**
	 * ArrayList of the different states of ToolLine objects that modified by 'move' or 'selected'
	 * [Added in the course of the Module Integration Test]
	 */
	private ArrayList<ToolLine> toolLine_drawing = new ArrayList<>();
	/**
	 * ArrayList of the different states of ToolTriangle objects that modified by 'move' or 'selected'
	 * [Added in the course of the Module Integration Test]
	 */
	private ArrayList<ToolTriangle> toolTriangle_drawing = new ArrayList<>();
	/**
	 * ArrayList of the different states of ToolRectangle objects that modified by 'move' or 'selected'
	 * [Added in the course of the Module Integration Test]
	 */
	private ArrayList<ToolRectangle> toolRectangle_drawing = new ArrayList<>();
	
	/**
	 * Defines the selection rectangle for displaying it
	 */
	private Rectangle2D selectionRectangle = null;
	
	/**
	 * Replaces the current contents of the ArrayLists in the ToolDrawPanel
	 * with the contents of the corresponding ArrayLists in the ObjectManager
	 * @author heol1015
	 * @param objectmanager ObjectManager object which provides the object lists
	 */
	public void requestToolObjectLists( ObjectManager objectmanager) {
		//Draw Objects
		toolPointList = objectmanager.managedToolPoints;
		toolLineList = objectmanager.managedToolLines;
		toolTriangleList = objectmanager.managedToolTriangles;
		toolRectangleList = objectmanager.managedToolRectangles;	
		//Selection Objects
		pointList_selection = objectmanager.selectedToolPoints;
		lineList_selection = objectmanager.selectedToolLines;
		triangleList_selection = objectmanager.selectedToolTriangles;
		rectangleList_selection = objectmanager.selectedToolRectangles;
	}	
	
	/**
	 * Adds a ToolLine at the end of the corresponding ArrayList.
	 * [Added in the course of the Module Integration Test]
	 * @author heol1015
	 * @param toolline ToolLine object that shall be stored
	 */
	public void storeDrawingLineElements(ToolLine toolline) {
		toolLine_drawing.add(toolline);
	}
	
	/**
	 * Adds a ToolTriangle at the end of the corresponding ArrayList.
	 * [Added in the course of the Module Integration Test]
	 * @author heol1015
	 * @param tooltriangle ToolTriangle object that shall be stored
	 */
	public void storeDrawingTriangleElements(ToolTriangle tooltriangle) {
		toolTriangle_drawing.add(tooltriangle);
	}
	
	/**
	 * Adds a ToolRectangle at the end of the corresponding ArrayList.
	 * [Added in the course of the Module Integration Test]
	 * @author heol1015
	 * @param toolrectangle ToolRectangle object that shall be stored
	 */
	public void storeDrawingRectangleElements(ToolRectangle toolrectangle) {
		toolRectangle_drawing.add(toolrectangle);
	}
	
	/**
	 * Clears all ArrayLists for live-displaying objects when drawn.
	 * [Added in the course of the Module Integration Test]
	 * @author heol1015
	 */
	public void clearDrawingElements() {
		toolLine_drawing.clear();
		toolTriangle_drawing.clear();
		toolRectangle_drawing.clear();
	}
	
	/**
	 * Sets the selection Rectangle
	 * @author heol1015
	 * @param Rectangle Rectangle2D object drawn by user, that serves as selection rectangle
	 */
	public void defineSelectionRectangle(Rectangle2D Rectangle) {
		this.selectionRectangle = Rectangle;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color drawColor = new Color(0, 0, 0, 0.75f);
		g2d.setPaint(drawColor);
		
		//Draw Points
		toolPointList.forEach((ToolPoint point) -> {
			Ellipse2D pt = point.createToolPoint();
			g2d.fill(pt);
		});
		
		//Draw Lines
		toolLineList.forEach((ToolLine line) -> {	
			Line2D ln = line.createToolLine();
			g2d.draw(ln);
		});
		
		//Draw Triangles
		toolTriangleList.forEach((ToolTriangle triangle) -> {
			Path2D pth_t = triangle.createToolTriangle();
			g2d.draw(pth_t);
		});
		
		//Draw Rectangles
		toolRectangleList.forEach((ToolRectangle rectangle) -> {
			Rectangle2D rct = rectangle.createToolRectangle();
			g2d.draw(rct);
		});
		
		//Draw Selections
		Color selectionColor = new Color(1, 0, 0, 1f);
		g2d.setPaint(selectionColor);
		
		if ( selectionRectangle != null) {
			g2d.draw(selectionRectangle);
		}
		
		//Draw selected Objects in Red
		pointList_selection.forEach((ToolPoint point) -> {
			Ellipse2D ptS = point.createToolPoint();
			g2d.fill(ptS);
		});
		
		lineList_selection.forEach((ToolLine line) -> {
			Line2D lnS = line.createToolLine();
			g2d.draw(lnS);
		});
		
		triangleList_selection.forEach((ToolTriangle triangle) -> {
			Path2D trS = triangle.createToolTriangle();
			g2d.draw(trS);
		});
		
		rectangleList_selection.forEach((ToolRectangle rectangle) -> {
			Rectangle2D rcS = rectangle.createToolRectangle();
			g2d.draw(rcS);
		});
		
		Color drawingColor = Color.white;
		g2d.setPaint(drawingColor);
		
		//Live Display of states of objects modified by 'move' or 'change'
		toolLine_drawing.forEach((ToolLine drawLine) -> {
			Line2D dln = drawLine.createToolLine();
			g2d.draw(dln);		
		});
		
		toolTriangle_drawing.forEach((ToolTriangle drawtriangle) -> {
			Path2D dtr = drawtriangle.createToolTriangle();
			g2d.draw(dtr);		
		});
		
		toolRectangle_drawing.forEach((ToolRectangle drawRectangle) -> {
			Rectangle2D drc = drawRectangle.createToolRectangle();
			g2d.draw(drc);		
		});
							 
	}

}
