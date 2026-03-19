// code by jph
package ch.alpine.ascony.ren;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public record TextContour(Graphics2D graphics, FontRenderContext frc, Font font) {
  public static TextContour of(Graphics2D graphics) {
    return new TextContour(graphics, graphics.getFontRenderContext(), graphics.getFont());
  }

  public void draw(ColorPair colorPair, String string, int x, int y) {
    GlyphVector glyphVector = font.createGlyphVector(frc, string);
    Shape shape = glyphVector.getOutline(x, y);
    graphics.setColor(colorPair.fill());
    graphics.fill(shape);
    graphics.setStroke(new BasicStroke(0.50f)); // thickness of outline
    graphics.setColor(colorPair.draw());
    graphics.draw(shape);
  }
}
