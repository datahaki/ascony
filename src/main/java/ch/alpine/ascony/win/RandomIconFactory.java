// adapted from chatgpt
package ch.alpine.ascony.win;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import ch.alpine.bridge.awt.RenderQuality;

public class RandomIconFactory {
  public static Icon create(int seed, int size) {
    BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    RenderQuality.setQuality(graphics);
    Random random = new Random(seed);
    // --- background color ---
    Color bg = new Color(100 + random.nextInt(156), 100 + random.nextInt(156), 100 + random.nextInt(156));
    graphics.setColor(bg);
    graphics.fillRoundRect(0, 0, size, size, size / 4, size / 4);
    // --- foreground color ---
    Color fg = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    graphics.setColor(fg);
    int shape = random.nextInt(4);
    switch (shape) {
    case 0 -> graphics.fillOval(size / 4, size / 4, size / 2, size / 2);
    case 1 -> graphics.fillRect(size / 4, size / 4, size / 2, size / 2);
    case 2 -> graphics.fillPolygon(new int[] { size / 2, size * 3 / 4, size / 4 }, new int[] { size / 4, size * 3 / 4, size * 3 / 4 }, 3);
    case 3 -> {
      graphics.setStroke(new BasicStroke(size / 8f));
      graphics.drawLine(size / 4, size / 4, size * 3 / 4, size * 3 / 4);
      graphics.drawLine(size * 3 / 4, size / 4, size / 4, size * 3 / 4);
    }
    }
    graphics.dispose();
    return new ImageIcon(bufferedImage);
  }
}
