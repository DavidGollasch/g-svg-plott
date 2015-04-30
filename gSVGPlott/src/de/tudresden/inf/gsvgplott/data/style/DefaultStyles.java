/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style;

import java.awt.Color;

import de.tudresden.inf.gsvgplott.data.style.palettes.ColorPalette;
import de.tudresden.inf.gsvgplott.data.style.palettes.LineTypePalette;
import de.tudresden.inf.gsvgplott.data.style.palettes.PointTypePalette;

/**
 * @author David Gollasch
 *
 */
public class DefaultStyles {
	
	// GENERAL
	
	public static AreaStyle getDefaultScreenGeneralAreaStyle() {
		AreaStyle x = new AreaStyle(ColorPalette.SNOW);
		return x;
	}
	
	public static AreaStyle getDefaultPrintGeneralAreaStyle() {
		return getDefaultScreenGeneralAreaStyle();
	}
	
	public static TextStyle getDefaultScreenGeneralTextStyle() {
		TextStyle x = new TextStyle("serif", 36, ColorPalette.LOCORICE, false, false);
		return x;
	}
	
	public static TextStyle getDefaultPrintGeneralTextStyle() {
		TextStyle x = new TextStyle("Braille DE Computer", 36, ColorPalette.LOCORICE, false, false);
		return x;
	}
	
	// AXES & HELPLINES
	
	public static LineStyle getDefaultScreenXaxisLineStyle() {
		LineStyle x = new LineStyle(LineTypePalette.SOLID, (float)0.5, new Color(17,17,17));
		return x;
	}
	
	public static LineStyle getDefaultPrintXaxisLineStyle() {
		return getDefaultScreenXaxisLineStyle();
	}
	
	public static LineStyle getDefaultScreenXaxisHelplinesLineStyle() {
		LineStyle x = new LineStyle(LineTypePalette.DASHED, (float)0.5, new Color(17,17,17));
		return x;
	}
	
	public static LineStyle getDefaultPrintXaxisHelplinesLineStyle() {
		return getDefaultScreenXaxisHelplinesLineStyle();
	}
	
	public static LineStyle getDefaultScreenYaxisLineStyle() {
		return getDefaultScreenXaxisLineStyle();
	}
	
	public static LineStyle getDefaultPrintYaxisLineStyle() {
		return getDefaultScreenYaxisLineStyle();
	}
	
	public static LineStyle getDefaultScreenYaxisHelplinesLineStyle() {
		return getDefaultScreenXaxisHelplinesLineStyle();
	}
	
	public static LineStyle getDefaultPrintYaxisHelplinesLineStyle() {
		return getDefaultScreenYaxisHelplinesLineStyle();
	}
	
	// INTEGRAL
	
	public static AreaStyle getDefaultScreenIntegralAreaStyle() {
		AreaStyle x = new AreaStyle(ColorPalette.LOCORICE);
		return x;
	}
	
	public static AreaStyle getDefaultPrintIntegralAreaStyle() {
		return getDefaultScreenIntegralAreaStyle();
	}
	
	// FUNCTIONS & POINT LISTS
	
	public static LineStyle getDefaultScreenFunctionLineStyle() {
		LineStyle x = new LineStyle(LineTypePalette.SOLID, 1, ColorPalette.LOCORICE);
		return x;
	}
	
	public static LineStyle getDefaultPrintFunctionLineStyle() {
		return getDefaultScreenFunctionLineStyle();
	}
	
	public static PointStyle getDefaultScreenMarkedPointsListPointStyle() {
		PointStyle x = new PointStyle(PointTypePalette.CROSS, false, 10, ColorPalette.LOCORICE);
		return x;
	}
	
	public static PointStyle getDefaultPrintMarkedPointsListPointStyle() {
		return getDefaultScreenMarkedPointsListPointStyle();
	}
	
}
