// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;

import ch.alpine.ascony.msh.D2Raster;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class R2Display extends RnDisplay {
  public static final ManifoldDisplay INSTANCE = new R2Display();

  private R2Display() {
    super(2);
  }

  @Override
  public Tensor uvw2log(Tensor xya) {
    return xya.extract(0, 2);
  }

  @Override
  public D2Raster d2Raster() {
    return new D2Raster() {
      @Override
      public Optional<Tensor> d2lift(Tensor pxy) {
        return Optional.of(pxy);
      }
    };
  }

  @Override
  public CoordinateBoundingBox d2Raster_coordinateBoundingBox() {
    return CoordinateBoundingBox.of(CLIP, CLIP);
  }
}
