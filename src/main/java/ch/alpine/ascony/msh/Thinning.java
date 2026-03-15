// code by jph
package ch.alpine.ascony.msh;

import java.util.stream.IntStream;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;

public enum Thinning {
  ;
  public static Tensor of(Tensor tensor, int delta) {
    Tensor result = Tensors.empty();
    for (int index = 0; index < tensor.length(); index += delta)
      result.append(tensor.get(index));
    return result;
  }

  public static Tensor flatten(Tensor[][] forward, int dx, int dy) {
    // TODO implementation
    return Unprotect.using( //
        IntStream.range(0, forward.length / dx) //
            .boxed() //
            .flatMap(i -> IntStream.range(0, forward[i * dx].length / dy) //
                .mapToObj(j -> forward[i * dx][j * dy]))
            .toList());
  }
}
