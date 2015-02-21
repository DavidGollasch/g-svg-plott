/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style;

import java.awt.Color;

/**
 * @author David Gollasch
 *
 */
public class TextStyle {
	private String font;
	private int size;
	private Color color;
	private boolean isBold;
	private boolean isItalic;
	private boolean isUnderlined;
	
	/**
	 * @param font
	 * @param size
	 * @param color
	 * @param isBold
	 * @param isItalic
	 * @param isUnderlined
	 */
	public TextStyle(String font, int size, Color color, boolean isBold,
			boolean isItalic, boolean isUnderlined) {
		super();
		this.font = font;
		this.size = size;
		this.color = color;
		this.isBold = isBold;
		this.isItalic = isItalic;
		this.isUnderlined = isUnderlined;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TextStyle [font=" + font + ", size=" + size + ", color="
				+ color + ", isBold=" + isBold + ", isItalic=" + isItalic
				+ ", isUnderlined=" + isUnderlined + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((font == null) ? 0 : font.hashCode());
		result = prime * result + (isBold ? 1231 : 1237);
		result = prime * result + (isItalic ? 1231 : 1237);
		result = prime * result + (isUnderlined ? 1231 : 1237);
		result = prime * result + size;
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
		TextStyle other = (TextStyle) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (font == null) {
			if (other.font != null)
				return false;
		} else if (!font.equals(other.font))
			return false;
		if (isBold != other.isBold)
			return false;
		if (isItalic != other.isItalic)
			return false;
		if (isUnderlined != other.isUnderlined)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return font;
	}
	/**
	 * @param font the font to set
	 */
	public void setFont(String font) {
		this.font = font;
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
	/**
	 * @return the isBold
	 */
	public boolean isBold() {
		return isBold;
	}
	/**
	 * @param isBold the isBold to set
	 */
	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}
	/**
	 * @return the isItalic
	 */
	public boolean isItalic() {
		return isItalic;
	}
	/**
	 * @param isItalic the isItalic to set
	 */
	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}
	/**
	 * @return the isUnderlined
	 */
	public boolean isUnderlined() {
		return isUnderlined;
	}
	/**
	 * @param isUnderlined the isUnderlined to set
	 */
	public void setUnderlined(boolean isUnderlined) {
		this.isUnderlined = isUnderlined;
	}
}
