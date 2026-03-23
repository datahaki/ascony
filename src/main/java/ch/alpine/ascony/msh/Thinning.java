// code by jph
package ch.alpine.ascony.msh;

import java.util.stream.IntStream;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;

public enum Thinning {
  ;
  /** @param tensor
   * @param delta
   * @return */
  public static Tensor of(Tensor tensor, int delta) {
    return Tensor.of(IntStream.iterate(0, i -> i + delta) //
        .limit(tensor.length() / delta) //
        .mapToObj(tensor::get));
  }

  /** @param forward
   * @param dx
   * @param dy
   * @return list of references, i.e. NOT copies to input array */
  public static Tensor flatten(Tensor[][] forward, int dx, int dy) {
    return Unprotect.using(IntStream.iterate(0, i -> i + dx) //
        .limit(forward.length / dx) //
        .boxed() //
        .flatMap(i -> IntStream.iterate(0, j -> j + dy) //
            .limit(forward[i].length / dy) //
            .mapToObj(j -> forward[i][j]))
        .toList());
  }
}
