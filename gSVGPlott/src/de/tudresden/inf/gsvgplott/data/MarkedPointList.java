/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.gsvgplott.data.style.PointStyle;

/**
 * @author David Gollasch
 *
 */
public class MarkedPointList {
	private String title;
	private List<Point> points;
	
	private PointStyle pointStyle;
	
	/**
	 * @param title
	 */
	public MarkedPointList(String title) {
		super();
		this.title = title;
		this.points = new ArrayList<Point>();
		
		this.pointStyle = null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MarkedPointList [title=" + title + ", points=" + points + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkedPointList other = (MarkedPointList) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	/**
	 * @return the pointStyle
	 */
	public PointStyle getPointStyle() {
		return pointStyle;
	}

	/**
	 * @param pointStyle the pointStyle to set
	 */
	public void setPointStyle(PointStyle pointStyle) {
		this.pointStyle = pointStyle;
	}
}
