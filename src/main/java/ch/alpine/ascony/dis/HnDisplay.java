// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.sophis.crv.d2.StarPoints;
import ch.alpine.sophis.decim.LineDistance;
import ch.alpine.sophus.hs.GeodesicSpace;
import ch.alpine.sophus.hs.h.Hyperboloid;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.PadRight;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomSampleInterface;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** symmetric positive definite 2 x 2 matrices */
public abstract class HnDisplay implements ManifoldDisplay, Serializable {
  private static final Tensor STAR_POINTS = StarPoints.of(6, 0.12, 0.04).unmodifiable();
  protected static final Clip CLIP = Clips.absolute(2.5);
  private static final TensorUnaryOperator LIFT = PadRight.zeros(3);
  // ---
  private final int dimensions;

  protected HnDisplay(int dimensions) {
    this.dimensions = dimensions;
  }

  @Override
  public final int dimensions() {
    return dimensions;
  }

  @Override // from ManifoldDisplay
  public final Tensor xya2point(Tensor xya) {
//    return HnWeierstrassCoordinate.toPoint(xya.extract(0, dimensions));
    return xya.extract(0, dimensions);
  }

  @Override
  public final Tensor point2xya(Tensor p) {
    return LIFT.apply(p.extract(0, dimensions));
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjection(Tensor xyz) {
    return v->v;
  }

  @Override // from ManifoldDisplay
  public final Tensor matrixLift(Tensor p) {
    return Se2Matrix.translation(p);
  }

  @Override // from ManifoldDisplay
  public final Tensor shape() {
    return STAR_POINTS;
  }

  @Override
  public final GeodesicSpace geodesicSpace() {
    return Hyperboloid.INSTANCE;
  }

  @Override // from ManifoldDisplay
  public final LineDistance lineDistance() {
    return null;
  }

  @Override // from ManifoldDisplay
  public final RandomSampleInterface randomSampleInterface() {
    Distribution distribution = UniformDistribution.of(CLIP);
    return randomGenerator -> {
      // return VectorQ.requireLength(RandomVariate.of(distribution, random, 2).append(RealScalar.ZERO), 3);
//      return HnWeierstrassCoordinate.toPoint(RandomVariate.of(distribution, randomGenerator, dimensions));
      return RandomVariate.of(distribution, randomGenerator, dimensions);
    };
  }
}
