// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.sophus.api.GeodesicSpace;
import ch.alpine.sophus.api.LineDistance;
import ch.alpine.sophus.hs.rpn.HemisphereRandomSample;
import ch.alpine.sophus.hs.s.SnLineDistance;
import ch.alpine.sophus.hs.s.Sphere;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.pdf.RandomSampleInterface;

/** symmetric positive definite 2 x 2 matrices */
public abstract class SnDisplay implements ManifoldDisplay, Serializable {
  private static final Tensor CIRCLE = CirclePoints.of(15).multiply(RealScalar.of(0.05)).unmodifiable();
  // ---
  private final int dimensions;
  private Sphere sphere;

  protected SnDisplay(int dimensions) {
    this.dimensions = dimensions;
    sphere = new Sphere(dimensions);
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
  public final GeodesicSpace geodesicSpace() {
    return sphere;
  }

  @Override // from ManifoldDisplay
  public final LineDistance lineDistance() {
    return SnLineDistance.INSTANCE;
  }

  @Override // from ManifoldDisplay
  public final RandomSampleInterface randomSampleInterface() {
    return HemisphereRandomSample.of(dimensions);
  }

  @Override
  public final String toString() {
    return manifold().toString();
  }
}
