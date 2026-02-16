// code by jph
package ch.alpine.ascony.ren;

import java.io.Serializable;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.ArgMin;
import ch.alpine.tensor.ext.PackageTestAccess;

public record ArgMinValue(int index, Scalar value) implements Serializable {
  /** @param tensor
   * @return */
  public static Optional<ArgMinValue> of(Tensor tensor) {
    int index = ArgMin.of(tensor);
    return 0 <= index //
        ? Optional.of(new ArgMinValue(index, tensor.Get(index)))
        : Optional.empty();
  }

  public static Optional<ArgMinValue> of(Tensor tensor, Scalar threshold) {
    int index = ArgMin.of(tensor);
    return 0 <= index //
        ? new ArgMinValue(index, tensor.Get(index)).filter(threshold)
        : Optional.empty();
  }

  @PackageTestAccess
  Optional<ArgMinValue> filter(Scalar threshold) {
    return Scalars.lessEquals(value, threshold) //
        ? Optional.of(this)
        : Optional.empty();
  }
}
