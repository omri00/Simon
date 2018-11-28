package grafics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class handle the images of the game.
 * 
 * @author omri
 *
 */
public class ImagesHandler {

	public static final int NON_ACTIVE = -1;
	public static final int ALL_ACTIVE = -2;

	private Image background = new ImageIcon("imgs/bg.png").getImage();
	private BufferedImage[][] colors = new BufferedImage[4][2];
	private int activeImage = NON_ACTIVE;
	private boolean inverted;
	public final int size;

	/**
	 * Makes a new ImagesHandler.
	 * 
	 * @param inverted
	 *            if the game is in inverte mode.
	 */
	public ImagesHandler(boolean inverted) {
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[i].length; j++) {
				try {
					colors[i][j] = ImageIO.read(new File("imgs/color" + i + j + ".png"));
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
		size = colors[0][0].getHeight();
		this.inverted = inverted;
	}

	/**
	 * Active a color
	 * 
	 * @param color
	 *            the wanted color.
	 */
	public void activeColor(int color) {
		activeImage = color;
	}

	/**
	 * 
	 * @param x
	 *            the x value of the click.
	 * @param y
	 *            the y value of the click.
	 * @return the color that was clicked on or NON_ACTIVE if no colored was clicked
	 *         on.
	 */
	public int checkColor(int x, int y) {
		if (!new Rectangle(0, 0, background.getWidth(null), background.getHeight(null)).contains(x, y)) {
			return NON_ACTIVE;
		}
		int pressedColor = x / size + (y / size) * 2;
		x = x % size;
		y = y % size;
		if ((colors[pressedColor][0].getRGB(x, y) & 0xFF000000) == 0) {
			return NON_ACTIVE;
		}
		return pressedColor;
	}

	/**
	 * @return The size of an image of a color
	 */
	public int getSize() {
		return size;
	}

	/**
	 * active all colors.
	 */
	public void activeAll() {
		activeImage = ALL_ACTIVE;
	}

	/**
	 * deactivate all colors.
	 */
	public void deactiveAll() {
		activeImage = NON_ACTIVE;
	}

	/**
	 * Draw on the screen the images.
	 * 
	 * @param g
	 *            graphics.
	 */
	public void draw(Graphics g) {
		if (activeImage == NON_ACTIVE) {
			g.drawImage(colors[0][0], 0, 0, null);
			g.drawImage(colors[1][0], size, 0, null);
			g.drawImage(colors[2][0], 0, size, null);
			g.drawImage(colors[3][0], size, size, null);
		} else if (activeImage == ALL_ACTIVE) {
			g.drawImage(colors[0][1], 0, 0, null);
			g.drawImage(colors[1][1], size, 0, null);
			g.drawImage(colors[2][1], 0, size, null);
			g.drawImage(colors[3][1], size, size, null);
		} else {
			g.drawImage(colors[0][activeImage == 0 ^ inverted ? 1 : 0], 0, 0, null);
			g.drawImage(colors[1][activeImage == 1 ^ inverted ? 1 : 0], size, 0, null);
			g.drawImage(colors[2][activeImage == 2 ^ inverted ? 1 : 0], 0, size, null);
			g.drawImage(colors[3][activeImage == 3 ^ inverted ? 1 : 0], size, size, null);
		}
		g.drawImage(background, 0, 0, null);
	}

}
