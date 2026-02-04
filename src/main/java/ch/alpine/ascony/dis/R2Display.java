// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;

import ch.alpine.ascony.arp.D2Raster;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.Tensor;

public class R2Display extends RnDisplay implements D2Raster {
  public static final ManifoldDisplay INSTANCE = new R2Display();

  private R2Display() {
    super(2);
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor p) {
    return Se2Matrix.translation(p);
  }

  @Override // from GeodesicArrayPlot
  public Optional<Tensor> d2lift(Tensor pxy) {
    return Optional.of(pxy);
  }
}
