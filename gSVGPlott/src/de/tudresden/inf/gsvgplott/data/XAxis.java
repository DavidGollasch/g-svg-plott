/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

/**
 * @author David Gollasch
 *
 */
public class XAxis extends Axis {
	
	private boolean piDivisioning;

	/**
	 * @param title
	 * @param rangeFrom
	 * @param rangeTo
	 * @param piDivisioning
	 */
	public XAxis(String title, int rangeFrom, int rangeTo,
			boolean piDivisioning) {
		super(title, rangeFrom, rangeTo);
		this.piDivisioning = piDivisioning;
	}

	/**
	 * @return the piDivisioning
	 */
	public boolean isPiDivisioning() {
		return piDivisioning;
	}

	/**
	 * @param piDivisioning the piDivisioning to set
	 */
	public void setPiDivisioning(boolean piDivisioning) {
		this.piDivisioning = piDivisioning;
	}

}
