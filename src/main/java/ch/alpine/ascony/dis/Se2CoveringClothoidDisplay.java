// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.sophis.crv.clt.ClothoidBuilder;
import ch.alpine.sophis.crv.clt.ClothoidBuilders;
import ch.alpine.sophis.crv.clt.ClothoidTransitionSpace;
import ch.alpine.sophis.ts.TransitionSpace;
import ch.alpine.tensor.Tensor;

public final class Se2CoveringClothoidDisplay extends AbstractClothoidDisplay {
  public static final ManifoldDisplay INSTANCE = new Se2CoveringClothoidDisplay();

  // ---
  private Se2CoveringClothoidDisplay() {
    // ---
  }

  @Override // from AbstractClothoidDisplay
  public ClothoidBuilder geodesicSpace() {
    return ClothoidBuilders.SE2_COVERING.clothoidBuilder();
  }

  @Override
  public TransitionSpace transitionSpace() {
    return ClothoidTransitionSpace.COVERING;
  }

  @Override // from ManifoldDisplay
  public final Tensor xya2point(Tensor xya) {
    return xya.copy();
  }

  @Override // from Object
  public String toString() {
    return "ClC";
  }
}
