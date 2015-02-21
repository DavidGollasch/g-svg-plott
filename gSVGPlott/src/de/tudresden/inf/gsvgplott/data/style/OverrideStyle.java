/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style;

/**
 * @author David Gollasch
 *
 */
public class OverrideStyle {
	private String cssStyle;

	/**
	 * @param cssStyle
	 */
	public OverrideStyle(String cssStyle) {
		super();
		this.cssStyle = cssStyle;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OverrideStyle [cssStyle=" + cssStyle + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cssStyle == null) ? 0 : cssStyle.hashCode());
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
		OverrideStyle other = (OverrideStyle) obj;
		if (cssStyle == null) {
			if (other.cssStyle != null)
				return false;
		} else if (!cssStyle.equals(other.cssStyle))
			return false;
		return true;
	}

	/**
	 * @return the cssStyle
	 */
	public String getCssStyle() {
		return cssStyle;
	}

	/**
	 * @param cssStyle the cssStyle to set
	 */
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
}
