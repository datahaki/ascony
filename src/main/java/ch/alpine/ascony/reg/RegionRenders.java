// code by jph
package ch.alpine.ascony.reg;

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

  /** default color for obstacle region */
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
