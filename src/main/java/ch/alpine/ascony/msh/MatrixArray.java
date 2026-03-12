// code by jph
package ch.alpine.ascony.msh;

import java.io.Serializable;
import java.util.Arrays;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;

/** for fast access to array elements */
public record MatrixArray(Tensor[][] arrays) implements Serializable {
  /** @param tensor not necessarily with array structure
   * @return
   * @throws Exception if given tensor is not a list of vectors */
  public static MatrixArray wrap(Tensor tensor) {
    return new MatrixArray(tensor.stream().map(MatrixArray::ofVector).toArray(Tensor[][]::new));
  }

  /** @return */
  public Tensor unwrap() {
    return Tensor.of(Arrays.stream(arrays).map(Arrays::stream).map(Tensor::of));
  }
  // public Tensor map(TensorUnaryOperator tuo) {
  // return Tensor.of(Arrays.stream(arrays).map(row -> Tensor.of(Arrays.stream(row).map(tuo))));
  // }

  public Tensor[][] maps(TensorUnaryOperator tuo) {
    return Arrays.stream(arrays) //
        .map(row -> Arrays.stream(row).map(tuo).toArray(Tensor[]::new)) //
        .toArray(Tensor[][]::new);
  }

  /** @param i
   * @param j
   * @return reference to array element (i, j) */
  public Tensor get(int i, int j) {
    return arrays[i][j];
  }

  public int rows() {
    return arrays.length;
  }

  public int cols() {
    return arrays[0].length;
  }

  // helper function
  private static Tensor[] ofVector(Tensor vector) {
    return vector.stream().toArray(Tensor[]::new);
  }
}
