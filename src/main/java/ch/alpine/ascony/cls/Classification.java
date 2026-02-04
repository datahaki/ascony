// code by jph
package ch.alpine.ascony.cls;

import ch.alpine.tensor.Tensor;

@FunctionalInterface
public interface Classification {
  /** @param weights
   * @return */
  ClassificationResult result(Tensor weights);
}
