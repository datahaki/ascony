// code by jph
package ch.alpine.ascony.sym;

import ch.alpine.sophus.lie.rn.RGroup;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.itp.BinaryAverage;
import ch.alpine.tensor.red.Min;

public record SymLinkPart(SymLink lP, SymLink lQ, Scalar lambda) implements SymLink {
  private static final Scalar SHIFT_Y = RealScalar.of(0.5);

  @Override
  public Tensor position() {
    Tensor posP = lP.position();
    Tensor posQ = lQ.position();
    return Tensors.of( //
        RGroup.INSTANCE.split(posP.Get(0), posQ.Get(0), lambda), //
        Min.of(posP.Get(1), posQ.Get(1)).subtract(SHIFT_Y));
  }

  @Override
  public Tensor position(BinaryAverage binaryAverage) {
    return binaryAverage.split( //
        lP.position(binaryAverage), //
        lQ.position(binaryAverage), //
        lambda);
  }

  @Override
  public int depth() {
    return Math.max(lP.depth(), lQ.depth()) + 1;
  }
}
