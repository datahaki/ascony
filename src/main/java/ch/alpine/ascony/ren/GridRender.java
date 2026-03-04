// code by jph
package ch.alpine.ascony.ren;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Supplier;

import ch.alpine.bridge.fig.GridDrawer;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.re.LinearSolve;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

public record GridRender(Supplier<Dimension> supplier) implements RenderInterface {
  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    if (geometricLayer.isAxisAligned()) {
      Dimension dimension = supplier.get(); // jComponent.getSize();
      Rectangle rectangle = Show.defaultInsets(dimension, graphics.getFont().getSize());
      CoordinateBoundingBox cbb = fromRectangle(geometricLayer, rectangle);
      ShowableConfig showableConfig = new ShowableConfig(rectangle, cbb);
      new GridDrawer().render(showableConfig, graphics);
    }
  }

  // ---
  /** transforms point in pixel space to coordinates of model space
   * 
   * @param point
   * @return tensor of length 2 */
  private Tensor toModel(Tensor model2pixel, Point point) {
    return LinearSolve.of(model2pixel, Tensors.vector(point.x, point.y, 1)).extract(0, 2);
  }

  private CoordinateBoundingBox fromRectangle(GeometricLayer geometricLayer, Rectangle rectangle) {
    // if (isRotatable)
    // System.err.println("warning: rotatable");
    Tensor matrix = geometricLayer.getMatrix();
    Tensor p1 = toModel(matrix, rectangle.getLocation());
    Tensor p2 = toModel(matrix, new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
    return CoordinateBounds.of(Tensors.of(p1, p2));
  }
}
