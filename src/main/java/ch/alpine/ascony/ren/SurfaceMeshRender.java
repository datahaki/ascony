// code by jph
package ch.alpine.ascony.ren;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Optional;
import java.util.stream.IntStream;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.d2.SignedCurvature2D;
import ch.alpine.sophis.srf.SurfaceMesh;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradient;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.lie.rot.Cross;
import ch.alpine.tensor.nrm.NormalizeUnlessZero;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;

/** for meshes where the vertices are vectors of length 3 */
public record SurfaceMeshRender(SurfaceMesh surfaceMesh, ColorDataGradient colorDataGradient) implements RenderInterface {
  private static final TensorUnaryOperator NORMALIZE_UNLESS_ZERO = NormalizeUnlessZero.with(Vector2Norm::of);
  private static final Tensor REF = NORMALIZE_UNLESS_ZERO.apply(Tensors.vector(-1, 1, 2));
  private static final Color COLOR_EDGE = new Color(128, 128, 128, 32);

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    graphics.setStroke(new BasicStroke());
    for (int[] face : surfaceMesh.faces()) {
      Tensor polygon = Tensor.of(IntStream.of(face).mapToObj(surfaceMesh.vrt::get));
      Optional<Scalar> optional = SignedCurvature2D.of( //
          polygon.get(0).extract(0, 2), //
          polygon.get(1).extract(0, 2), //
          polygon.get(2).extract(0, 2));
      boolean ccw = optional.isPresent() && Sign.isPositive(optional.orElseThrow());
      if (ccw) {
        Path2D path2d = geometricLayer.toPath2D(polygon);
        path2d.closePath();
        Tensor x = polygon.get(0);
        Tensor y = polygon.get(1);
        Tensor z = polygon.get(2);
        Tensor nrm = Cross.of(x.subtract(z), y.subtract(z));
        nrm = NORMALIZE_UNLESS_ZERO.apply(nrm);
        Scalar s = Clips.unit().apply((Scalar) REF.dot(nrm));
        Tensor rgba = colorDataGradient.apply(s);
        Color color = ColorFormat.toColor(rgba);
        graphics.setColor(color);
        graphics.fill(path2d);
        graphics.setColor(COLOR_EDGE);
        graphics.draw(path2d);
      }
    }
  }
}
