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
  public static MatrixArray from(Tensor tensor) {
    return new MatrixArray(tensor.stream().map(MatrixArray::ofVector).toArray(Tensor[][]::new));
  }

  /** @return */
  public Tensor lift() {
    return Tensor.of(Arrays.stream(arrays).map(Arrays::stream).map(Tensor::of));
  }

  public Tensor[][] maps(TensorUnaryOperator tuo) {
    return Arrays.stream(arrays).parallel() //
        .map(row -> Arrays.stream(row).map(tuo).toArray(Tensor[]::new)) //
        .toArray(Tensor[][]::new);
  }

  /** function is MatrixArray(maps()).lift()
   * 
   * @param tuo
   * @return */
  public Tensor maps_lift(TensorUnaryOperator tuo) {
    return Tensor.of(Arrays.stream(arrays).parallel() //
        .map(row -> Tensor.of(Arrays.stream(row).map(tuo))));
  }

  /** @param i
   * @param j
   * @return reference to array element (i, j) */
  public Tensor get(int i, int j) {
    return arrays[i][j];
  }

  int rows() { // function not used
    return arrays.length;
  }

  int cols() { // function not used
    int length = arrays[0].length;
    if (Arrays.stream(arrays).skip(1).allMatch(entry -> entry.length == length))
      return length;
    throw new RuntimeException("not an array");
  }

  // helper function
  private static Tensor[] ofVector(Tensor vector) {
    return vector.stream().toArray(Tensor[]::new);
  }
}
