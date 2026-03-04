// code by jph
package ch.alpine.ascony.reg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.reg.BallRegion;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.lie.rot.CirclePoints;

public record BallRegionRender(BallRegion ballRegion) implements RenderInterface {
  private static final Tensor CIRCLE_POINTS = CirclePoints.of(16).unmodifiable();
  private static final float[] RATIOS = { 0.0f, 1.0f };
  private static final Color[] COLORS = { new Color(255, 0, 0, 64), new Color(255, 255, 0, 64) };

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    Tensor polygon = CIRCLE_POINTS.multiply(ballRegion.radius());
    geometricLayer.pushMatrix(Se2Matrix.translation(ballRegion.center()));
    Point2D center = geometricLayer.toPoint2D(ballRegion.center().maps(Scalar::zero));
    Point2D extent = geometricLayer.toPoint2D(polygon.get(0));
    graphics.setPaint(new RadialGradientPaint( //
        center, (float) extent.distance(center), //
        RATIOS, COLORS));
    graphics.fill(geometricLayer.toPath2D(polygon));
    geometricLayer.popMatrix();
  }
}
