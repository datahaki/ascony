// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.sophis.decim.LineDistance;
import ch.alpine.sophus.hs.GeodesicSpace;
import ch.alpine.sophus.hs.rpn.RpnManifold;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.pdf.RandomSampleInterface;

/** symmetric positive definite 2 x 2 matrices */
public abstract class RpnDisplay implements ManifoldDisplay, Serializable {
  private static final Tensor CIRCLE = CirclePoints.of(15).multiply(RealScalar.of(0.05)).unmodifiable();
  // ---
  private final int dimensions;

  protected RpnDisplay(int dimensions) {
    this.dimensions = dimensions;
  }

  @Override // from ManifoldDisplay
  public final int dimensions() {
    return dimensions;
  }

  @Override // from ManifoldDisplay
  public final Tensor shape() {
    return CIRCLE;
  }

  @Override // from ManifoldDisplay
  public final GeodesicSpace geodesicSpace() {
    return new RpnManifold(dimensions);
  }

  @Override // from ManifoldDisplay
  public final LineDistance lineDistance() {
    return null;
  }

  @Override // from ManifoldDisplay
  public final RandomSampleInterface randomSampleInterface() {
    return new RpnManifold(dimensions);
  }
}
