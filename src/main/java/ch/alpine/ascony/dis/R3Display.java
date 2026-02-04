// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.Tensor;

public class R3Display extends RnDisplay {
  public static final ManifoldDisplay INSTANCE = new R3Display();

  private R3Display() {
    super(3);
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor p) {
    return Se2Matrix.translation(p);
  }
}
