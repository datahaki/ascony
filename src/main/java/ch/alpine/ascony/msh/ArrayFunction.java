// code by jph
package ch.alpine.ascony.msh;

import java.util.Optional;
import java.util.function.Function;

import ch.alpine.tensor.Tensor;

/** allows to discard points on the meshgrid domain that do not correspond to points on manifold
 * for instance in case meshgrid is the interval [-1, 1] x [-1, 1], the points in the corners
 * are not mapped on the sphere S^2 */
public record ArrayFunction<T extends Tensor>(Function<Tensor, T> function, T fallback) //
    implements Function<Optional<Tensor>, T> {
  /** @param optional
   * @return */
  @Override
  public T apply(Optional<Tensor> optional) {
    return optional.map(function).orElse(fallback);
  }
}
