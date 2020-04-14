package vlb.ide.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;


/**
 * IconBuilder builds a color icon to use to designate tabs
 */

public class IconBuilder implements Icon {

	private static final int ICON_SIZE = 8;
	
	public static final Color GREEN = new Color(100, 230, 100);
	
	public static final Color BLUE = new Color(0, 0, 255);
	
	public static final Color RED = new Color(255, 0, 0);
	
	public static final Color YELLOW = new Color(255, 225, 0);
	
	private Color color = null;
	
	public IconBuilder(Color color){
		this.color = color;
	}
	
	public int getIconHeight() {
		return ICON_SIZE;
	}

	public int getIconWidth() {
		return ICON_SIZE;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Color oldColor = g.getColor();

		g.setColor(new Color(70, 70, 70));
		g.fillRect(x, y, ICON_SIZE, ICON_SIZE);

		g.setColor(color);
		g.fillRect(x + 1, y + 1, ICON_SIZE - 2, ICON_SIZE - 2);

		g.setColor(oldColor);
	}
}
