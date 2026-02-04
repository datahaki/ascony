// code by jph
package ch.alpine.ascony.api;

import ch.alpine.sophis.dv.Biinvariant;
import ch.alpine.sophis.dv.Sedarim;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.api.TensorScalarFunction;

public interface LogWeighting {
  /** @param biinvariant
   * @param variogram
   * @param sequence
   * @return */
  Sedarim sedarim(Biinvariant biinvariant, ScalarUnaryOperator variogram, Tensor sequence);

  /** @param biinvariant
   * @param variogram
   * @param sequence
   * @param values
   * @return */
  TensorScalarFunction function( //
      Biinvariant biinvariant, ScalarUnaryOperator variogram, Tensor sequence, Tensor values);
}
