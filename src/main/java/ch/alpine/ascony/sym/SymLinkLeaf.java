// code by jph
package ch.alpine.ascony.sym;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.itp.BinaryAverage;

/** characterized by {@link #isNode()} == true */
public record SymLinkLeaf(Scalar scalar, Tensor position) implements SymLink {
  @Override // from SymLink
  public Tensor position() {
    return Tensors.of(scalar, RealScalar.ZERO);
  }

  @Override // from SymLink
  public Tensor position(BinaryAverage binaryAverage) {
    return position;
  }

  @Override
  public int depth() {
    return 0;
  }
}
