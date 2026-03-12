// code by jph
package ch.alpine.ascony.msh;

import java.util.Arrays;

import ch.alpine.tensor.Tensor;

public enum MatrixArray {
  ;
  /** @param tensor not necessarily with array structure
   * @return
   * @throws Exception if given tensor is not a list of vectors */
  public static Tensor[][] of(Tensor tensor) {
    return tensor.stream().map(MatrixArray::ofVector).toArray(Tensor[][]::new);
  }

  /** @param tensors
   * @return */
  public static Tensor byRef(Tensor[][] tensors) {
    return Tensor.of(Arrays.stream(tensors).map(row -> Tensor.of(Arrays.stream(row))));
  }

  // helper function
  private static Tensor[] ofVector(Tensor vector) {
    return vector.stream().toArray(Tensor[]::new);
  }
}
