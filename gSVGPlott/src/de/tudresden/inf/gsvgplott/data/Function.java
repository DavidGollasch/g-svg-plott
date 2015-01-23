/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

import java.awt.Color;

/**
 * @author David Gollasch
 *
 */
public class Function {
	
	private String function;
	
	private LineType linetype_screen;
	private int linewidth_screen;
	private Color color_screen;
	
	private LineType linetype_print;
	private int linewidth_print;
	private Color color_print;
	
	public Function() {
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public LineType getScreenLinetype() {
		return linetype_screen;
	}

	public void setScreenLinetype(LineType linetype_screen) {
		this.linetype_screen = linetype_screen;
	}

	public int getScreenLinewidth() {
		return linewidth_screen;
	}

	public void setScreenLinewidth(int linewidth_screen) {
		this.linewidth_screen = linewidth_screen;
	}

	public Color getScreenColor() {
		return color_screen;
	}

	public void setScreenColor(Color color_screen) {
		this.color_screen = color_screen;
	}

	public LineType getPrintLinetype() {
		return linetype_print;
	}

	public void setPrintLinetype(LineType linetype_print) {
		this.linetype_print = linetype_print;
	}

	public int getPrintLinewidth() {
		return linewidth_print;
	}

	public void setPrintLinewidth(int linewidth_print) {
		this.linewidth_print = linewidth_print;
	}

	public Color getPrintColor() {
		return color_print;
	}

	public void setPrintColor(Color color_print) {
		this.color_print = color_print;
	}
	
	
}
