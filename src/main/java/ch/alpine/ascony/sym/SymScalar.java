// code by jph
package ch.alpine.ascony.sym;

import java.io.Serializable;

import ch.alpine.tensor.MultiplexScalar;
import ch.alpine.tensor.Scalar;

public sealed abstract class SymScalar extends MultiplexScalar implements Serializable //
    permits SymScalarLeaf, SymScalarPart {
  /** @return recursive evaluation of this coordinate */
  protected abstract Scalar evaluate();

  public abstract SymLink build();

  @Override
  public final Scalar multiply(Scalar scalar) {
    throw new UnsupportedOperationException();
  }

  @Override
  public final Scalar negate() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final Scalar reciprocal() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final Scalar zero() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final Scalar one() {
    throw new UnsupportedOperationException();
  }

  @Override
  protected final Scalar plus(Scalar scalar) {
    throw new UnsupportedOperationException();
  }
}
