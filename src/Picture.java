/* Arnav Jaiswal
 * Mar 31
 * return images to the block class
 */

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Picture {
	private Map<String, Image> images;

	public Picture() {
		images = new HashMap<>();

		// Load and store each image in the map
		images.put("dirt", new ImageIcon("src/Dirt.png").getImage());
		images.put("steel", new ImageIcon("src/Steel.png").getImage());
		images.put("stone", new ImageIcon("src/Stone.png").getImage());
		images.put("crack1", new ImageIcon("src/Crack-1.png").getImage());
		images.put("crack2", new ImageIcon("src/Crack-2.png").getImage());
		images.put("crack3", new ImageIcon("src/Crack-3.png").getImage());
		images.put("crack4", new ImageIcon("src/Crack-4.png").getImage());
		images.put("grass", new ImageIcon("src/Grass.png").getImage());
	}

	public Image getImage(String name) {
		return images.get(name); // returns null if not found, or you can use getOrDefault
	}
}