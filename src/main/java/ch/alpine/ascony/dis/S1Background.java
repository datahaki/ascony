// code by jph
package ch.alpine.ascony.dis;

import java.awt.Color;
import java.awt.Graphics2D;

import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.lie.rot.CirclePoints;

/* package */ enum S1Background implements RenderInterface {
  INSTANCE;

  private static final Tensor CIRCLE = CirclePoints.of(61);
  private static final Color BORDER = new Color(192, 192, 192, 128);

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    graphics.setColor(BORDER);
    graphics.draw(geometricLayer.toPath2D(CIRCLE, true));
  }
}
