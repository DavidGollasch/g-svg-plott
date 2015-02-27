/**
 * 
 */
package de.tudresden.inf.gsvgplott.data.style.palettes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author David Gollasch
 *
 */
public final class PointTypePalette {
	//TODO: Fill the palette
	public static final String ELEMENT = new String("");
	
	/**
	 * Get all instances of String declared as static fields in this class
	 * @return Map with field names as key and its related object reference as value
	 */
	public static Map<String, String> getPalette() {
		Map<String, String> palette = new HashMap<String, String>();
		
		// identify all fields of the class and add String instances to the map
		// using the name as key and the object reference as value
		List<Field> fields = new ArrayList<Field>(
				Arrays.asList(LineTypePalette.class.getDeclaredFields()));
		for (Field field : fields) {
			Object fieldobj = null;
			try {
				fieldobj = field.get(null);
			} catch (Exception e) {
				continue;	//leave that iteration
			}
			
			if(fieldobj instanceof String) {
				palette.put(field.getName(), (String)fieldobj);
			}
		}
		
		return palette;
	}
}
