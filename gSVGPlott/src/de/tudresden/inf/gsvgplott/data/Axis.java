/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.gsvgplott.data.style.LineStyle;

/**
 * @author David Gollasch
 *
 */
public abstract class Axis {
	protected String title;
	protected int rangeFrom;
	protected int rangeTo;
	protected List<Helpline> helplines;
	
	protected LineStyle axisScreenStyle;
	protected LineStyle helplineScreenStyle;
	
	protected LineStyle axisPrintStyle;
	protected LineStyle helplinePrintStyle;
	
	/**
	 * @param title
	 * @param rangeFrom
	 * @param rangeTo
	 */
	public Axis(String title, int rangeFrom, int rangeTo) {
		super();
		this.title = title;
		this.rangeFrom = rangeFrom;
		this.rangeTo = rangeTo;
		this.helplines = new ArrayList<Helpline>();
		
		this.axisScreenStyle = null;
		this.helplineScreenStyle = null;
		this.axisPrintStyle = null;
		this.helplinePrintStyle = null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Axis [title=" + title + ", rangeFrom=" + rangeFrom
				+ ", rangeTo=" + rangeTo + ", helplines=" + helplines + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((helplines == null) ? 0 : helplines.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rangeFrom);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rangeTo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Axis other = (Axis) obj;
		if (helplines == null) {
			if (other.helplines != null)
				return false;
		} else if (!helplines.equals(other.helplines))
			return false;
		if (Double.doubleToLongBits(rangeFrom) != Double
				.doubleToLongBits(other.rangeFrom))
			return false;
		if (Double.doubleToLongBits(rangeTo) != Double
				.doubleToLongBits(other.rangeTo))
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
	 * @return the rangeFrom
	 */
	public int getRangeFrom() {
		return rangeFrom;
	}

	/**
	 * @param rangeFrom the rangeFrom to set
	 */
	public void setRangeFrom(int rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	/**
	 * @return the rangeTo
	 */
	public int getRangeTo() {
		return rangeTo;
	}

	/**
	 * @param rangeTo the rangeTo to set
	 */
	public void setRangeTo(int rangeTo) {
		this.rangeTo = rangeTo;
	}

	/**
	 * @return the helplines
	 */
	public List<Helpline> getHelplines() {
		return helplines;
	}

	/**
	 * @param helplines the helplines to set
	 */
	public void setHelplines(ArrayList<Helpline> helplines) {
		this.helplines = helplines;
	}

	/**
	 * @return the axisScreenStyle
	 */
	public LineStyle getAxisScreenStyle() {
		return axisScreenStyle;
	}

	/**
	 * @param axisScreenStyle the axisScreenStyle to set
	 */
	public void setAxisScreenStyle(LineStyle axisScreenStyle) {
		this.axisScreenStyle = axisScreenStyle;
	}

	/**
	 * @return the helplineScreenStyle
	 */
	public LineStyle getHelplineScreenStyle() {
		return helplineScreenStyle;
	}

	/**
	 * @param helplineScreenStyle the helplineScreenStyle to set
	 */
	public void setHelplineScreenStyle(LineStyle helplineScreenStyle) {
		this.helplineScreenStyle = helplineScreenStyle;
	}

	/**
	 * @return the axisPrintStyle
	 */
	public LineStyle getAxisPrintStyle() {
		return axisPrintStyle;
	}

	/**
	 * @param axisPrintStyle the axisPrintStyle to set
	 */
	public void setAxisPrintStyle(LineStyle axisPrintStyle) {
		this.axisPrintStyle = axisPrintStyle;
	}

	/**
	 * @return the helplinePrintStyle
	 */
	public LineStyle getHelplinePrintStyle() {
		return helplinePrintStyle;
	}

	/**
	 * @param helplinePrintStyle the helplinePrintStyle to set
	 */
	public void setHelplinePrintStyle(LineStyle helplinePrintStyle) {
		this.helplinePrintStyle = helplinePrintStyle;
	}
}
