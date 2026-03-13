// code by jph
package ch.alpine.ascony.dis;

import java.util.concurrent.ThreadLocalRandom;

import ch.alpine.ascony.msh.D2Raster;
import ch.alpine.ascony.ren.ColorPair;
import ch.alpine.ascony.ren.PointsRender;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.clt.ClothoidBuilder;
import ch.alpine.sophis.ts.TransitionSpace;
import ch.alpine.sophis.ts.UniformTransitionSpace;
import ch.alpine.sophus.api.GeodesicSpace;
import ch.alpine.sophus.api.LineDistance;
import ch.alpine.sophus.api.Manifold;
import ch.alpine.sophus.hs.HomogeneousSpace;
import ch.alpine.sophus.lie.LieGroup;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.RandomSampleInterface;

/** Hint: the interface is intended for use in the demo layer
 * but not in the library functions.
 * 
 * a parameter denoted p is always a pointQ in the manifold */
public interface ManifoldDisplay {
  /** @return dimensions of the manifold, strictly positive */
  int dimensions();

  /** @return polygon to visualize the control point */
  Tensor shape();

  boolean isXYeuclid();

  /** @param xya vector of length 3
   * @return control point */
  Tensor xya2point(Tensor xya);

  /** pseudo-inverse to xya2point function
   * 
   * @param p
   * @return xya */
  Tensor point2xya(Tensor p);

  default TensorUnaryOperator point2xya() {
    return this::point2xya;
  }

  /** function is for drawing paths
   * 
   * @param p control point
   * @return vector of length 2 with grid coordinates {x, y} */
  default Tensor point2xy(Tensor p) {
    return point2xya(p).extract(0, 2);
  }

  default TensorUnaryOperator point2xy() {
    return this::point2xy;
  }

  /** function is for drawing control points with proper orientation
   * 
   * @param p control point
   * @return matrix with dimensions 3 x 3 */
  Tensor matrixLift(Tensor p);

  /** @return never null
   * @see HomogeneousSpace
   * @see LieGroup
   * @see ClothoidBuilder */
  GeodesicSpace geodesicSpace();

  default Manifold manifold() {
    return geodesicSpace() instanceof Manifold manifold //
        ? manifold
        : null;
  }

  default HomogeneousSpace homogeneousSpace() {
    return geodesicSpace() instanceof HomogeneousSpace homogeneousSpace //
        ? homogeneousSpace
        : null;
  }

  default LieGroup lieGroup() {
    return geodesicSpace() instanceof LieGroup lieGroup //
        ? lieGroup
        : null;
  }

  default TransitionSpace transitionSpace() {
    return new UniformTransitionSpace(geodesicSpace());
  }

  /** @param p
   * @return operator that maps arbitrary dimension tangent vectors to 2d for display */
  TensorUnaryOperator tangentProjection(Tensor p);

  default LineDistance lineDistance() {
    return null;
  }

  /** @return points in manifold that have to be {@link #point2xya(Tensor)}ed
   * in order to become control points in the form xya */
  RandomSampleInterface randomSampleInterface();

  /** @return rendering of background, for instance a shaded sphere for S^2 */
  RenderInterface background();

  default RenderInterface showPoints(ColorPair colorPair, Scalar scale, Tensor points) {
    return new PointsRender(colorPair, this::matrixLift, shape().multiply(scale), points);
  }

  default D2Raster d2Raster() {
    return null;
  }

  default CoordinateBoundingBox d2Raster_coordinateBoundingBox() {
    return null;
  }

  default Tensor indetPoint() {
    return randomSampleInterface().randomSample(ThreadLocalRandom.current()) //
        .maps(_ -> DoubleScalar.INDETERMINATE);
  }
}
