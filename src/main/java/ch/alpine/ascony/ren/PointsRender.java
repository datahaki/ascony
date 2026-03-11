// code by jph
package ch.alpine.ascony.ren;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;

public record PointsRender(Color color_fill, Color color_draw) {
  public RenderInterface show(TensorUnaryOperator matrixLift, Tensor shape, Tensor points) {
    return new Show(matrixLift, shape, points);
  }

  private class Show implements RenderInterface {
    private final TensorUnaryOperator matrixLift;
    private final Tensor shape;
    private final Tensor points;

    public Show(TensorUnaryOperator matrixLift, Tensor shape, Tensor points) {
      this.matrixLift = matrixLift;
      this.shape = shape;
      this.points = points;
    }

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
      graphics.setColor(color_fill);
      graphics.fill(path2d);
      graphics.setColor(color_draw);
      graphics.draw(path2d);
    }
  }
}
