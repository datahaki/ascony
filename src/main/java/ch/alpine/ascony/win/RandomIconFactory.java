// adapted from chatgpt
package ch.alpine.ascony.win;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class RandomIconFactory {
  public static Icon create(int seed, int size) {
    Random random = new Random(seed);
    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    enableQuality(g);
    // --- background color ---
    Color bg = new Color(100 + random.nextInt(156), 100 + random.nextInt(156), 100 + random.nextInt(156));
    g.setColor(bg);
    g.fillRoundRect(0, 0, size, size, size / 4, size / 4);
    // --- foreground color ---
    Color fg = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    g.setColor(fg);
    int shape = random.nextInt(4);
    switch (shape) {
    case 0 -> g.fillOval(size / 4, size / 4, size / 2, size / 2);
    case 1 -> g.fillRect(size / 4, size / 4, size / 2, size / 2);
    case 2 -> g.fillPolygon(new int[] { size / 2, size * 3 / 4, size / 4 }, new int[] { size / 4, size * 3 / 4, size * 3 / 4 }, 3);
    case 3 -> {
      g.setStroke(new BasicStroke(size / 8f));
      g.drawLine(size / 4, size / 4, size * 3 / 4, size * 3 / 4);
      g.drawLine(size * 3 / 4, size / 4, size / 4, size * 3 / 4);
    }
    }
    g.dispose();
    return new ImageIcon(image);
  }

  private static void enableQuality(Graphics2D g) {
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
  }
}
