/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style;

/**
 * @author David Gollasch
 *
 */
public enum LineType {
	/**
	 * Per CSS: no stroke-dasharray
	 */
	SOLID,
	
	/**
	 * Per CSS: stroke-dasharray="5,5"
	 */
	DOTTED,
	
	/**
	 * Per CSS: stroke-dasharray="10,10"
	 */
	DASHED,
	
	/**
	 * Per CSS: stroke-dasharray="20,10"
	 */
	LONGDASHED,
	
	/**
	 * Per CSS: stroke-dasharray="10,5,5,5"
	 */
	DASHDOTTED,
	
	/**
	 * Per CSS: stroke-dasharray="20,10,5,5,5,10"
	 */
	DASHDOUBLEDOTTED
}
