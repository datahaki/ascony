// code by jph
package ch.alpine.ascony.ren;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.BezierCurve;
import ch.alpine.sophus.bm.LinearBiinvariantMean;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarTensorFunction;

public record BezierGlyphRender(Shape shape, Tensor domain) implements RenderInterface {
  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    Tensor prev = null;
    Tensor next = null;
    Tensor stol = null;
    Tensor last = null;
    PathIterator pathIterator = shape.getPathIterator(new AffineTransform(1, 0, 0, -1, 0, 0));
    float[] coords = new float[6];
    while (!pathIterator.isDone()) {
      switch (pathIterator.currentSegment(coords)) {
      case PathIterator.SEG_MOVETO:
        prev = Tensors.vector(coords[0], coords[1]);
        break;
      case PathIterator.SEG_LINETO: {
        next = Tensors.vector(coords[0], coords[1]);
        graphics.draw(geometricLayer.toLine2D(prev, next));
        prev = next;
        break;
      }
      case PathIterator.SEG_QUADTO: {
        next = Tensors.vector(coords[0], coords[1]);
        last = Tensors.vector(coords[2], coords[3]);
        ScalarTensorFunction stf = BezierCurve.of(LinearBiinvariantMean.INSTANCE, Tensors.of(prev, next, last));
        graphics.draw(geometricLayer.toPath2D(domain.maps(stf)));
        prev = last;
        break;
      }
      case PathIterator.SEG_CUBICTO: {
        next = Tensors.vector(coords[0], coords[1]);
        stol = Tensors.vector(coords[2], coords[3]);
        last = Tensors.vector(coords[4], coords[5]);
        ScalarTensorFunction bez = BezierCurve.of(LinearBiinvariantMean.INSTANCE, Tensors.of(prev, next, stol, last));
        graphics.draw(geometricLayer.toPath2D(domain.maps(bez)));
        prev = last;
        break;
      }
      case PathIterator.SEG_CLOSE:
        break;
      }
      pathIterator.next();
    }
  }
}
