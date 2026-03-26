// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.ascony.crv.Arrowhead;
import ch.alpine.ascony.ren.EmptyRender;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;

public abstract class Se2AbstractDisplay implements ManifoldDisplay {
  private static final Tensor ARROWHEAD = Arrowhead.of(0.3).unmodifiable();

  @Override // from ManifoldDisplay
  public final int dimensions() {
    return 3;
  }

  @Override
  public Tensor uvw2log(Tensor uvw) {
    return uvw.copy();
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjectionM2P(Tensor p) {
    return v -> v.extract(0, 2);
  }

  @Override // from ManifoldDisplay
  public final Tensor shape() {
    return ARROWHEAD;
  }

  @Override
  public final boolean isDubins() {
    return true;
  }

  @Override
  public final Tensor point2xya(Tensor p) {
    return p.copy();
  }

  @Override // from ManifoldDisplay
  public final Tensor matrixLift(Tensor p) {
    return Se2Matrix.of(p);
  }

  @Override // from ManifoldDisplay
  public final RenderInterface background() {
    return EmptyRender.INSTANCE;
  }

  @Override // from Object
  public abstract String toString();
}
