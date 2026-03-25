// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.ascony.crv.SpikyPoly;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophus.lie.LieGroup;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.lie.so.So3Group;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Fold;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.lie.rot.AngleVector;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.mat.pd.Orthogonalize;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.nrm.VectorAngle;
import ch.alpine.tensor.sca.Sign;

/** orthogonal 3 x 3 matrices */
public class So3Display implements ManifoldDisplay {
  private static final Tensor SPIKY = SpikyPoly.normal(RealScalar.of(0.003));
  // ---
  public static final ManifoldDisplay INSTANCE = new So3Display();

  private So3Display() {
  }

  @Override // from ManifoldDisplay
  public int dimensions() {
    return 3;
  }

  @Override // from ManifoldDisplay
  public Tensor shape() {
    return SPIKY;
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
    Tensor nrm = S2Display.INSTANCE.xya2point(xya.extract(0, 2).append(RealScalar.ZERO));
    Tensor ang = AngleVector.of(xya.Get(2)).append(RealScalar.ZERO);
    Tensor matrix = Fold.of(Tensor::append, Tensors.of(nrm, ang), IdentityMatrix.of(3));
    Tensor mat = Tensor.of(Orthogonalize.of(matrix).stream().limit(3));
    if (Sign.isNegative(Det.of(mat)))
      mat.set(Tensor::negate, 2);
    return mat;
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    Tensor xy = p.get(0).extract(0, 2);
    Scalar a = VectorAngle.of(p.get(1), UnitVector.of(3, 0)).orElse(RealScalar.ZERO);
    return xy.append(a);
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
