// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.sophis.crv.d2.ex.Arrowhead;
import ch.alpine.sophis.decim.LineDistance;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.lie.so2.So2;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.pdf.RandomSampleInterface;

public abstract class R2S1AbstractDisplay implements ManifoldDisplay, Serializable {
  private static final Tensor ARROWHEAD = Arrowhead.of(0.2).unmodifiable();

  @Override // from ManifoldDisplay
  public final int dimensions() {
    return 3;
  }

  @Override // from ManifoldDisplay
  public final Tensor shape() {
    return ARROWHEAD;
  }

  @Override // from ManifoldDisplay
  public final Tensor xya2point(Tensor xya) {
    Tensor xym = xya.copy();
    xym.set(So2.MOD, 2);
    return xym;
  }

  @Override
  public final Tensor point2xya(Tensor p) {
    return p.copy();
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjection(Tensor xyz) {
    return null;
  }

  @Override // from ManifoldDisplay
  public final Tensor matrixLift(Tensor p) {
    return Se2Matrix.of(p);
  }

  @Override // from ManifoldDisplay
  public final LineDistance lineDistance() {
    return null;
  }

  @Override // from ManifoldDisplay
  public final RandomSampleInterface randomSampleInterface() {
    return null;
  }
}
