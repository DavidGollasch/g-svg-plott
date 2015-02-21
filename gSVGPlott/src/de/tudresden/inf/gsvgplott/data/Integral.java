/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

/**
 * @author David Gollasch
 *
 */
public class Integral {
	private String title;
	private double rangeFrom;
	private double rangeTo;
	private Function border1;
	private Function border2;
	
	/**
	 * @param title
	 * @param rangeFrom
	 * @param rangeTo
	 * @param border1
	 * @param border2 if you wish to select the x axis as second border, set this parameter to null
	 */
	public Integral(String title, double rangeFrom, double rangeTo,
			Function border1, Function border2) {
		super();
		this.title = title;
		this.rangeFrom = rangeFrom;
		this.rangeTo = rangeTo;
		this.border1 = border1;
		this.border2 = border2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Integral [title=" + title + ", rangeFrom=" + rangeFrom
				+ ", rangeTo=" + rangeTo + ", border1=" + border1
				+ ", border2=" + border2 + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((border1 == null) ? 0 : border1.hashCode());
		result = prime * result + ((border2 == null) ? 0 : border2.hashCode());
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
		Integral other = (Integral) obj;
		if (border1 == null) {
			if (other.border1 != null)
				return false;
		} else if (!border1.equals(other.border1))
			return false;
		if (border2 == null) {
			if (other.border2 != null)
				return false;
		} else if (!border2.equals(other.border2))
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
	public double getRangeFrom() {
		return rangeFrom;
	}
	/**
	 * @param rangeFrom the rangeFrom to set
	 */
	public void setRangeFrom(double rangeFrom) {
		this.rangeFrom = rangeFrom;
	}
	/**
	 * @return the rangeTo
	 */
	public double getRangeTo() {
		return rangeTo;
	}
	/**
	 * @param rangeTo the rangeTo to set
	 */
	public void setRangeTo(double rangeTo) {
		this.rangeTo = rangeTo;
	}
	/**
	 * @return the border1
	 */
	public Function getBorder1() {
		return border1;
	}
	/**
	 * @param border1 the border1 to set
	 */
	public void setBorder1(Function border1) {
		this.border1 = border1;
	}
	/**
	 * @return the border2
	 */
	public Function getBorder2() {
		return border2;
	}
	/**
	 * @param border2 the border2 to set
	 */
	public void setBorder2(Function border2) {
		this.border2 = border2;
	}
	
	
}
