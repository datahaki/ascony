// code by jph
package ch.alpine.ascony.api;

import java.util.List;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.ArrayReshape;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.Transpose;

public enum ImageTiling {
  ;
  /** @param tensor slices of images as tensor of rank at least 3
   * @return tensor with rank of given tensor - 1 */
  public static Tensor of(Tensor tensor) {
    List<Integer> list = Dimensions.of(tensor);
    return ArrayReshape.of(Transpose.of(tensor, 0, 2, 1), list.get(0), list.get(1) * list.get(2));
  }
}
