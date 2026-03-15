// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophus.lie.LieGroup;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.lie.so.So3Exponential;
import ch.alpine.sophus.lie.so.So3Group;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.nrm.Vector2Norm;

/** orthogonal 3 x 3 matrices */
public class So3Display implements ManifoldDisplay, Serializable {
  private static final Tensor TRIANGLE = CirclePoints.of(3).multiply(RealScalar.of(0.04)).unmodifiable();
  // ---
  public static final ManifoldDisplay INSTANCE = new So3Display();
  // ---

  private So3Display() {
  }

  @Override // from ManifoldDisplay
  public int dimensions() {
    return 3;
  }

  @Override // from ManifoldDisplay
  public Tensor shape() {
    return TRIANGLE;
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
  public Tensor xya2point(Tensor xya) {
    Tensor axis = xya;
    Scalar norm = Vector2Norm.of(axis);
    if (Scalars.lessThan(RealScalar.ONE, norm))
      axis = axis.divide(norm);
    return So3Exponential.vectorExp(axis);
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    return So3Exponential.vector_log(p);
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
  public LieGroup geodesicSpace() {
    return So3Group.INSTANCE;
  }
  // TODO ASCONA ALG line distance should be similar to s^3

  @Override // from ManifoldDisplay
  public RenderInterface background() {
    return S2Background.INSTANCE;
  }

  @Override
  public final String toString() {
    return manifold().toString();
  }
}
