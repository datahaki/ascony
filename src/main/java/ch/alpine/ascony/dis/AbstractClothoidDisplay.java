// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.ascony.api.Spearhead;
import ch.alpine.ascony.ren.EmptyRender;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.clt.ClothoidBuilder;
import ch.alpine.sophus.lie.se2.Se2CoveringGroup;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.pdf.RandomSampleInterface;

public abstract class AbstractClothoidDisplay implements ManifoldDisplay, Serializable {
  private static final Tensor SPEARHEAD = //
      // Spearhead.normal(Tensors.vector(-0.5, -0.21, -0.52), RealScalar.of(0.1), RealScalar.of(0.04));
      Spearhead.normal(Tensors.vector(-0.217, -0.183, 4.189), RealScalar.of(0.1), RealScalar.of(0.04));

  @Override
  public abstract ClothoidBuilder geodesicSpace();

  @Override // from ManifoldDisplay
  public final int dimensions() {
    return 3;
  }

  @Override // from ManifoldDisplay
  public final Tensor shape() {
    return SPEARHEAD;
  }

  @Override
  public final boolean isXYeuclid() {
    return true;
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjection(Tensor p) {
    return v -> v.extract(0, 2);
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
  public final RandomSampleInterface randomSampleInterface() {
    return Se2CoveringGroup.INSTANCE;
  }

  @Override // from ManifoldDisplay
  public final RenderInterface background() {
    return EmptyRender.INSTANCE;
  }

  @Override // from Object
  public abstract String toString();
}
