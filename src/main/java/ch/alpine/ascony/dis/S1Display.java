// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.nrm.Vector2Norm;

/** symmetric positive definite 2 x 2 matrices */
public class S1Display extends SnDisplay {
  public static final ManifoldDisplay INSTANCE = new S1Display();

  // ---
  private S1Display() {
    super(1);
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    Tensor xy = xya.extract(0, 2);
    Scalar norm = Vector2Norm.of(xy);
    return Scalars.isZero(norm) //
        ? UnitVector.of(2, 0)
        : xy.divide(norm);
  }

  @Override
  public Tensor point2xya(Tensor p) {
    return p.copy().append(RealScalar.ZERO);
  }

  @Override // from ManifoldDisplay
  public TensorUnaryOperator tangentProjection(Tensor p) {
    return null;
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor xy) {
    return Se2Matrix.translation(point2xy(xy));
  }

  @Override
  public RenderInterface background() {
    return S1Background.INSTANCE;
  }
}
