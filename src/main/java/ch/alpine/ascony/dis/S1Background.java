// code by jph
package ch.alpine.ascony.dis;

import java.awt.Graphics2D;

import ch.alpine.ascony.ren.ColorStroke;
import ch.alpine.ascony.ren.PathRender;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.lie.rot.CirclePoints;

enum S1Background implements RenderInterface {
  INSTANCE;

  private static final Tensor CIRCLE = CirclePoints.of(61);

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    new PathRender(ColorStroke.SPHERE_1, CIRCLE, true).render(geometricLayer, graphics);
  }
}
