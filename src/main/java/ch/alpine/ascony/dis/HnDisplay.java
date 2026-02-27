// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.sophis.crv.d2.ex.StarPoints;
import ch.alpine.sophus.api.GeodesicSpace;
import ch.alpine.sophus.api.LineDistance;
import ch.alpine.sophus.hs.h.HLineDistance;
import ch.alpine.sophus.hs.h.HWeierstrassCoordinate;
import ch.alpine.sophus.hs.h.Hyperboloid;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.PadRight;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.pdf.RandomSampleInterface;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** symmetric positive definite 2 x 2 matrices */
public abstract class HnDisplay implements ManifoldDisplay, Serializable {
  private static final Tensor STAR_POINTS = StarPoints.of(6, 0.12, 0.04).unmodifiable();
  protected static final Clip CLIP = Clips.absolute(2.5);
  private static final TensorUnaryOperator LIFT = PadRight.zeros(3);
  // ---
  private final int dimensions;
  private final Hyperboloid hyperboloid;

  protected HnDisplay(int dimensions) {
    this.dimensions = dimensions;
    hyperboloid = new Hyperboloid(dimensions);
  }

  @Override
  public final int dimensions() {
    return hyperboloid.dimensions();
  }

  @Override // from ManifoldDisplay
  public final Tensor xya2point(Tensor xya) {
    return xya.extract(0, dimensions);
  }

  @Override
  public final Tensor point2xya(Tensor p) {
    return LIFT.apply(new HWeierstrassCoordinate(p).toPoint());
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjection(Tensor xyz) {
    return v -> v;
  }

  @Override // from ManifoldDisplay
  public final Tensor matrixLift(Tensor p) {
    return Se2Matrix.of(point2xya(p));
  }

  @Override // from ManifoldDisplay
  public final Tensor shape() {
    return STAR_POINTS;
  }

  @Override
  public final GeodesicSpace geodesicSpace() {
    return hyperboloid;
  }

  @Override // from ManifoldDisplay
  public final LineDistance lineDistance() {
    return HLineDistance.INSTANCE;
  }

  @Override // from ManifoldDisplay
  public final RandomSampleInterface randomSampleInterface() {
    return hyperboloid;
  }

  @Override
  public final String toString() {
    return manifold().toString();
  }
}
