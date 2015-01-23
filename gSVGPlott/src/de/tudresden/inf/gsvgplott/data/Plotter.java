package de.tudresden.inf.gsvgplott.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author David Gollasch
 *
 */

public class Plotter {

	private List<Function> functions;
	
	private String diagramTitle;
	private int frameWidth;
	private boolean showGrid;
	
	private Axis xaxis;
	private Axis yaxis;
	
	private Integral integral;
	private List<PointList> pointlists;
	
	
	public Plotter() {
		functions = new ArrayList<Function>();
		diagramTitle = "";
		frameWidth = 217;
		showGrid = true;
		
		xaxis = new Axis();
		yaxis = new Axis();
		
		integral = new Integral();
		
		pointlists = new ArrayList<PointList>();
	}
	
}
