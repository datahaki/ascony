// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophus.hs.h.HWeierstrassCoordinate;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.api.TensorUnaryOperator;

public class H1Display extends HnDisplay {
  public static final ManifoldDisplay INSTANCE = new H1Display();

  // ---
  private H1Display() {
    super(1);
  }

  @Override
  public final Tensor point2xya(Tensor p) {
    return LIFT.apply(new HWeierstrassCoordinate(p).toPoint());
    // return LIFT.apply(p);
  }

  @Override // from ManifoldDisplay
  public TensorUnaryOperator tangentProjection(Tensor xyz) {
    return v -> Append.of(v, RealScalar.ZERO); // TODO can be better
  }

  @Override
  public RenderInterface background() {
    return H1Background.INSTANCE;
  }
}
