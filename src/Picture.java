/* Arnav Jaiswal
 * give images to the block class
 */

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Picture {
	private Map<String, Image> images;

	public Picture() {
		images = new HashMap<>();

		images.put("dirt", new ImageIcon("src/Dirt.png").getImage());
		images.put("steel", new ImageIcon("src/Steel.png").getImage());
		images.put("stone", new ImageIcon("src/Stone.png").getImage());
		images.put("crack1", new ImageIcon("src/Crack-1.png").getImage());
		images.put("crack2", new ImageIcon("src/Crack-2.png").getImage());
		images.put("crack3", new ImageIcon("src/Crack-3.png").getImage());
		images.put("crack4", new ImageIcon("src/Crack-4.png").getImage());
		// air, grass, and shop are intended to be null
	}

	public Image getImage(String name) {
		return images.get(name);
	}
}