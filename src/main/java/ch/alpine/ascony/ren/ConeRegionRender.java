// code by jph
package ch.alpine.ascony.ren;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.sophis.reg.ConeRegion;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.lie.rot.AngleVector;

public record ConeRegionRender(ConeRegion coneRegion) implements RenderInterface {
  private static final Color NEAR = new Color(255, 0, 0, 64);
  private static final Color FAR = new Color(255, 255, 0, 64);
  // ---
  private static final Scalar pixels = RealScalar.of(20);

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    geometricLayer.pushMatrix(Se2Matrix.of(coneRegion.apex()));
    Scalar scalar = geometricLayer.pixel2modelFactor(pixels);
    geometricLayer.pushMatrix(Se2Matrix.pixel2model(scalar));
    graphics.setPaint(new GradientPaint( //
        geometricLayer.toPoint2D(Array.zeros(2)), NEAR, //
        geometricLayer.toPoint2D(UnitVector.of(2, 0)), FAR));
    Path2D path2d = geometricLayer.toPath2D(Tensors.of( //
        AngleVector.of(coneRegion.semi()), //
        Array.zeros(2), //
        AngleVector.of(coneRegion.semi().negate()) //
    ));
    graphics.fill(path2d);
    geometricLayer.popMatrix();
    geometricLayer.popMatrix();
  }
}
