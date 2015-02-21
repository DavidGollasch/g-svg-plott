/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style;

import java.awt.Color;

/**
 * @author David Gollasch
 *
 */
public class LineStyle {
	private LineType style;
	private int width;
	private Color color;
	
	/**
	 * @param style
	 * @param width
	 * @param color
	 */
	public LineStyle(LineType style, int width, Color color) {
		super();
		this.style = style;
		this.width = width;
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LineStyle [style=" + style + ", width=" + width + ", color="
				+ color + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		result = prime * result + width;
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
		LineStyle other = (LineStyle) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (style != other.style)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	/**
	 * @return the style
	 */
	public LineType getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(LineType style) {
		this.style = style;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
