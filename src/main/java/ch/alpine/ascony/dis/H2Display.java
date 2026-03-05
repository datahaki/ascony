// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;

import ch.alpine.ascony.api.Box2D;
import ch.alpine.ascony.arp.D2Raster;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class H2Display extends HnDisplay {
  public static final ManifoldDisplay INSTANCE = new H2Display();

  private H2Display() {
    super(2);
  }

  @Override
  public Tensor point2xya(Tensor p) {
    // return LIFT.apply(new HWeierstrassCoordinate(p).toPoint());
    return LIFT.apply(p);
  }
  
  @Override // from ManifoldDisplay
  public  TensorUnaryOperator tangentProjection(Tensor xyz) {
    return v -> v;
  }


  @Override
  public D2Raster d2Raster() {
    return new D2Raster() {
      @Override // from HsArrayPlot
      public Optional<Tensor> d2lift(Tensor pxy) {
        // return Optional.of(HnWeierstrassCoordinate.toPoint(pxy));
        return Optional.of(pxy);
      }
    };
  }

  @Override
  public CoordinateBoundingBox d2Raster_coordinateBoundingBox() {
    return Box2D.xy(CLIP);
  }

  @Override
  public RenderInterface background() {
    return H2Background.INSTANCE;
  }
}
