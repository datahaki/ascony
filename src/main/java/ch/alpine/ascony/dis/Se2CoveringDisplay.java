// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.sophus.api.GeodesicSpace;
import ch.alpine.sophus.lie.se2.Se2CoveringGroup;
import ch.alpine.tensor.Tensor;

public class Se2CoveringDisplay extends Se2AbstractDisplay {
  public static final ManifoldDisplay INSTANCE = new Se2CoveringDisplay();

  // ---
  private Se2CoveringDisplay() {
    // ---
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    return xya.copy();
  }

  @Override
  public GeodesicSpace geodesicSpace() {
    return Se2CoveringGroup.INSTANCE;
  }

  @Override // from Object
  public String toString() {
    return "SE2C";
  }
}
