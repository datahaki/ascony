// code by jph
package ch.alpine.ascony.ren;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;

/** rendering of scattered set of control points */
public record PointsRender(ColorPair colorPair, TensorUnaryOperator matrixLift, Tensor shape, Tensor points) //
    implements RenderInterface {
  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    int initialCapacity = points.length() * shape.length();
    Path2D path2d = new Path2D.Double(PathIterator.WIND_NON_ZERO, initialCapacity);
    for (Tensor point : points) {
      geometricLayer.pushMatrix(matrixLift.apply(point));
      geometricLayer.toPath2D(path2d, shape);
      path2d.closePath();
      geometricLayer.popMatrix();
    }
    graphics.setStroke(new BasicStroke());
    graphics.setColor(colorPair.fill());
    graphics.fill(path2d);
    graphics.setColor(colorPair.draw());
    graphics.draw(path2d);
  }
}
