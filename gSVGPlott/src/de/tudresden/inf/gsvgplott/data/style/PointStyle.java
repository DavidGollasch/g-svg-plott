/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style;

import java.awt.Color;

/**
 * @author David Gollasch
 *
 */
public class PointStyle {
	private String style;
	private boolean showBorder;
	private int size;
	private Color color;
	
	/**
	 * @param style SVG symbol representation (use the PointTypePalette here)
	 * @param showBorder
	 * @param size
	 * @param color
	 */
	public PointStyle(String style, boolean showBorder, int size, Color color) {
		super();
		this.style = style;
		this.showBorder = showBorder;
		this.size = size;
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PointStyle [style=" + style + ", showBorder=" + showBorder
				+ ", size=" + size + ", color=" + color + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (showBorder ? 1231 : 1237);
		result = prime * result + size;
		result = prime * result + ((style == null) ? 0 : style.hashCode());
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
		PointStyle other = (PointStyle) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (showBorder != other.showBorder)
			return false;
		if (size != other.size)
			return false;
		if (style != other.style)
			return false;
		return true;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set (based on the PointTypePalette)
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return the showBorder
	 */
	public boolean isShowBorder() {
		return showBorder;
	}
	/**
	 * @param showBorder the showBorder to set
	 */
	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
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
