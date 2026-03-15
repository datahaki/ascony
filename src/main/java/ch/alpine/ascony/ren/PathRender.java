// code by jph
package ch.alpine.ascony.ren;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

/** Remark: no further processing of the points is done.
 * this allows to efficiently draw clothoids, curves in SE(2) etc.
 * 
 * @param points has to be a matrix of dimensions n x d where d is at least 2
 * the first 2 values in each row {x,y,...} are part of the path */
public record PathRender(ColorStroke colorStroke, Tensor points, boolean cyclic) implements RenderInterface {
  @Override // from RenderInterface
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    if (Tensors.nonEmpty(points)) {
      graphics.setStroke(colorStroke.stroke());
      graphics.setColor(colorStroke.color());
      Path2D path2d = geometricLayer.toPath2D(points);
      if (cyclic)
        path2d.closePath();
      graphics.draw(path2d);
    }
  }
}
