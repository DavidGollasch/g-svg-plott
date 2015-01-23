/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

import java.util.ArrayList;
import java.util.List;

import tud.tangram.svgplot.coordinatesystem.Point;

/**
 * @author David Gollasch
 *
 */
public class PointList {
	
	private List<Point> points;
	private String listTitle;
	
	public PointList() {
		points = new ArrayList<Point>();
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}
	
	public void addPoint(Point p) {
		points.add(p);
	}
	
	public void addPoint(double x, double y) {
		points.add(new Point(x, y));
	}
}
