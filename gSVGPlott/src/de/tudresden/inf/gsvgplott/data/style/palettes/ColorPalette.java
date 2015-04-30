/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style.palettes;

import java.awt.Color;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author David Gollasch
 *
 */
public final class ColorPalette {
	public static final Color NETTEDMELON = new Color(255, 204, 102);
	public static final Color MUSKMELON = new Color(204, 255, 102);
	public static final Color SEAGREEN = new Color(102, 255, 204);
	public static final Color SKY = new Color(102, 204, 255);
	public static final Color LAVENDER = new Color(204, 102, 255);
	
	public static final Color CARNATION = new Color(255, 111, 207);
	public static final Color LOCORICE = new Color(0, 0, 0);
	public static final Color SNOW = new Color(255, 255, 255);
	public static final Color SALMON = new Color(255, 102, 102);
	public static final Color BANANA = new Color(255, 255, 102);
	
	public static final Color PLANT = new Color(102, 255, 102);
	public static final Color ICE = new Color(102, 255, 255);
	public static final Color ORCHID = new Color(102, 102, 255);
	public static final Color BUBBLEGUM = new Color(255, 102, 255);
	public static final Color PLUMB = new Color(25, 25, 25);
	
	public static final Color QUICKSILVER = new Color(230, 230, 230);
	public static final Color MANDARIN = new Color(255, 128, 0);
	public static final Color LEMON = new Color(128, 255, 0);
	public static final Color SPRAY = new Color(0, 255, 128);
	public static final Color WATER = new Color(0, 128, 255);
	
	public static final Color GRAPE = new Color(128, 0, 255);
	public static final Color STRAWBERRY = new Color(255, 0, 128);
	public static final Color BASALT = new Color(51, 51, 51);
	public static final Color SILVER = new Color(204, 204, 204);
	public static final Color APPLE = new Color(255, 0, 0);
	
	public static final Color CITRUS = new Color(255, 255, 0);
	public static final Color SPRING = new Color(0, 255, 0);
	public static final Color TURQUOISE = new Color(0, 255, 255);
	public static final Color BLUEBERRY = new Color(0, 0, 255);
	public static final Color MAGENTA = new Color(255, 0, 255);
	
	public static final Color IRON = new Color(76, 76, 76);
	public static final Color MAGNESIUM = new Color(179, 179, 179);
	public static final Color MOCHA = new Color(128, 64, 0);
	public static final Color FERN = new Color(64, 128, 0);
	public static final Color MOSS = new Color(0, 128, 64);
	
	public static final Color OCEAN = new Color(0, 64, 128);
	public static final Color AUBERGINE = new Color(64, 0, 128);
	public static final Color DARKRED = new Color(128, 0, 64);
	public static final Color STEEL = new Color(102, 102, 102);
	public static final Color ALUMINIUM = new Color(153, 153, 153);
	
	public static final Color CAYENNE = new Color(128, 0, 0);
	public static final Color ASPARAGUS = new Color(128, 128, 0);
	public static final Color CLOVER = new Color(0, 128, 0);
	public static final Color SEAGRASS = new Color(0, 128, 128);
	public static final Color MIDNIGHT = new Color(0, 0, 128);
	
	public static final Color PLUM = new Color(128, 0, 128);
	public static final Color TIN = new Color(127, 127, 127);
	public static final Color NICKEL = new Color(128, 128, 128);
	
	/**
	 * Get all instances of Color declared as static fields in this class
	 * @return Map with field names as key and its related object reference as value
	 */
	public static Map<String, Color> getPalette() {
		Map<String, Color> palette = new HashMap<String, Color>();
		
		// identify all fields of the class and add Color instances to the map
		// using the name as key and the object reference as value
		List<Field> fields = new ArrayList<Field>(
				Arrays.asList(ColorPalette.class.getDeclaredFields()));
		for (Field field : fields) {
			Object fieldobj = null;
			try {
				fieldobj = field.get(null);
			} catch (Exception e) {
				continue;	//leave that iteration
			}
			
			if(fieldobj instanceof Color) {
				palette.put(field.getName(), (Color)fieldobj);
			}
		}
		
		return palette;
	}
	
	/**
	 * Get all instances' names of Color declared as static fields in this class
	 * @return List with field names as ordered per class definition
	 */
	public static List<String> getOrderedPaletteKeys() {
		List<String> palettekeys = new ArrayList<String>();

		// identify all fields of the class and add name to the list
		List<Field> fields = new ArrayList<Field>(
				Arrays.asList(ColorPalette.class.getDeclaredFields()));
		for (Field field : fields) {
			Object fieldobj = null;
			try {
				fieldobj = field.get(null);
			} catch (Exception e) {
				continue;	//leave that iteration
			}

			if(fieldobj instanceof Color) {
				palettekeys.add(field.getName());
			}
		}

		return palettekeys;
	}
}
