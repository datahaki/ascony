// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.ascony.ren.EmptyRender;
import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.sophis.decim.LineDistance;
import ch.alpine.sophus.hs.GeodesicSpace;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.lie.so.Rodrigues;
import ch.alpine.sophus.lie.so.So3Group;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.pdf.RandomSampleInterface;

/** orthogonal 3 x 3 matrices */
public class So3Display implements ManifoldDisplay, Serializable {
  private static final Tensor TRIANGLE = CirclePoints.of(3).multiply(RealScalar.of(0.4)).unmodifiable();
  // TODO ASCONA radius == 1
  private static final Scalar RADIUS = RealScalar.of(7);
  // ---
  public static final ManifoldDisplay INSTANCE = new So3Display(RADIUS);
  // ---
  private final Scalar radius;

  public So3Display(Scalar radius) {
    this.radius = radius;
  }

  @Override // from ManifoldDisplay
  public int dimensions() {
    return 3;
  }

  @Override // from ManifoldDisplay
  public Tensor shape() {
    return TRIANGLE;
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    Tensor axis = xya.divide(radius);
    Scalar norm = Vector2Norm.of(axis);
    if (Scalars.lessThan(RealScalar.ONE, norm))
      axis = axis.divide(norm);
    return Rodrigues.vectorExp(axis);
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    return Rodrigues.vector_log(p).multiply(radius);
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjection(Tensor xyz) {
    return null;
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor xyz) {
    return Se2Matrix.translation(point2xy(xyz));
  }

  @Override
  public GeodesicSpace geodesicSpace() {
    return So3Group.INSTANCE;
  }

  @Override
  public final LineDistance lineDistance() {
    return null; // TODO ASCONA ALG line distance should be similar to s^3
  }

  @Override
  public RandomSampleInterface randomSampleInterface() {
    return So3Group.INSTANCE;
  }

  @Override // from ManifoldDisplay
  public RenderInterface background() {
    return EmptyRender.INSTANCE;
  }
}
