// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.sophus.api.GeodesicSpace;
import ch.alpine.sophus.api.LineDistance;
import ch.alpine.sophus.hs.rpn.RpnManifold;
import ch.alpine.sophus.hs.s.SnLineDistance;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.lie.rot.CirclePoints;

/** symmetric positive definite 2 x 2 matrices */
public abstract class RpnDisplay implements ManifoldDisplay, Serializable {
  private static final Tensor CIRCLE = CirclePoints.of(15).multiply(RealScalar.of(0.05)).unmodifiable();
  // ---
  private final int dimensions;
  private final RpnManifold rpnManifold;

  protected RpnDisplay(int dimensions) {
    this.dimensions = dimensions;
    rpnManifold = new RpnManifold(dimensions);
  }

  @Override // from ManifoldDisplay
  public final int dimensions() {
    return dimensions;
  }

  @Override // from ManifoldDisplay
  public final Tensor shape() {
    return CIRCLE;
  }

  @Override
  public final boolean isXYeuclid() {
    return false;
  }

  @Override
  public final boolean isXY_Angle() {
    return false;
  }

  @Override // from ManifoldDisplay
  public final GeodesicSpace geodesicSpace() {
    return rpnManifold;
  }

  @Override // from ManifoldDisplay
  public final LineDistance lineDistance() {
    return SnLineDistance.INSTANCE;
  }

  @Override
  public final String toString() {
    return manifold().toString();
  }
}
