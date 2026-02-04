// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;

import ch.alpine.ascony.api.Box2D;
import ch.alpine.ascony.arp.D2Raster;
import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class H2Display extends HnDisplay implements D2Raster {
  public static final ManifoldDisplay INSTANCE = new H2Display();

  private H2Display() {
    super(2);
  }

  @Override // from HsArrayPlot
  public Optional<Tensor> d2lift(Tensor pxy) {
//    return Optional.of(HnWeierstrassCoordinate.toPoint(pxy));
    return Optional.of(pxy);
  }

  @Override
  public CoordinateBoundingBox coordinateBoundingBox() {
    return Box2D.xy(CLIP);
  }

  @Override
  public RenderInterface background() {
    return H2Background.INSTANCE;
  }
}
