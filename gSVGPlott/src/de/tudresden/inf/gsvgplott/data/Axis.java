/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

import java.util.List;

/**
 * @author David Gollasch
 *
 */
public class Axis {
	
	private String title;
	private int rangeFrom;
	private int rangeTo;
	private boolean piDivisioning;
	
	private List<Double> helplinesAxisIntersections;
	
	public Axis() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(int rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public int getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(int rangeTo) {
		this.rangeTo = rangeTo;
	}

	public boolean isPiDivisioning() {
		return piDivisioning;
	}

	public void setPiDivisioning(boolean piDivisioning) {
		this.piDivisioning = piDivisioning;
	}

	public List<Double> getHelplinesAxisIntersections() {
		return helplinesAxisIntersections;
	}

	public void setHelplinesAxisIntersections(
			List<Double> helplinesAxisIntersections) {
		this.helplinesAxisIntersections = helplinesAxisIntersections;
	}
}
