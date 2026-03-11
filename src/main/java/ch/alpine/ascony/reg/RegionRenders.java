// code by jph
package ch.alpine.ascony.reg;

import java.awt.Color;

import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.d2.Extract2D;
import ch.alpine.sophis.crv.d2.ex.Box2D;
import ch.alpine.sophis.crv.d2.ex.EllipsePoints;
import ch.alpine.sophis.reg.BallRegion;
import ch.alpine.sophis.reg.EllipsoidRegion;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public enum RegionRenders {
  ;
  /** raster value 230 get's mapped to color {244, 244, 244, 255}
   * when using getRGB because of the color model attached to the
   * image type grayscale */
  public static final int RGB = 230;
  /** default color for obstacle region */
  public static final Color COLOR = new Color(RGB, RGB, RGB, 64);
  public static final Color BOUNDARY = new Color(192, 192, 192, 128);

  public static RenderInterface of(CoordinateBoundingBox cbb) {
    return new PolygonRegionRender(Box2D.polygon(cbb));
  }

  /** @param ellipsoidRegion
   * @return */
  public static RenderInterface of(EllipsoidRegion ellipsoidRegion) {
    Tensor radius = ellipsoidRegion.radius();
    return of( //
        Extract2D.FUNCTION.apply(ellipsoidRegion.center()), //
        radius.Get(0), radius.Get(1));
  }

  /** @param ballRegion
   * @return */
  public static RenderInterface of(BallRegion ballRegion) {
    return of( //
        Extract2D.FUNCTION.apply(ballRegion.center()), //
        ballRegion.radius(), ballRegion.radius());
  }

  private static final int RESOLUTION = 22;

  /** @param center vector of length 2
   * @param radiusX
   * @param radiusY */
  private static RenderInterface of(Tensor center, Scalar radiusX, Scalar radiusY) {
    return new PolygonRegionRender(Tensor.of(EllipsePoints.of(RESOLUTION, radiusX, radiusY).stream().map(center::add)));
  }
}
