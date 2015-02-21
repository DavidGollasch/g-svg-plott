/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

/**
 * @author David Gollasch
 *
 */
public class Helpline {
	private double intersection;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Helpline [intersection=" + intersection + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(intersection);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Helpline other = (Helpline) obj;
		if (Double.doubleToLongBits(intersection) != Double
				.doubleToLongBits(other.intersection))
			return false;
		return true;
	}

	/**
	 * @param intersection
	 */
	public Helpline(double intersection) {
		super();
		this.intersection = intersection;
	}

	/**
	 * @return the intersection
	 */
	public double getIntersection() {
		return intersection;
	}

	/**
	 * @param intersection the intersection to set
	 */
	public void setIntersection(double intersection) {
		this.intersection = intersection;
	}
}
