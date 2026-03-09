// code by jph
package ch.alpine.ascony.sym;

import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;

public record SymLinkBuilder(Tensor control) {
  /** @param control
   * @param symScalar
   * @return */
  public static SymLink of(Tensor control, SymScalar symScalar) {
    return new SymLinkBuilder(control).build(symScalar);
  }

  /** function for recursive building
   * 
   * @param symScalar
   * @return */
  private SymLink build(SymScalar symScalar) {
    if (symScalar instanceof SymScalarPart symScalarPart)
      return new SymLinkPart( //
          build(symScalarPart.getP()), //
          build(symScalarPart.getQ()), //
          symScalarPart.ratio());
    int index = Scalars.intValueExact(symScalar.evaluate());
    return new SymLinkLeaf(symScalar, control.get(index));
  }
}
