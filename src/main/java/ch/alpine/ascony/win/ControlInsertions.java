// code by jph
package ch.alpine.ascony.win;

import java.util.Iterator;

import ch.alpine.sophus.api.GeodesicSpace;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Last;

/** creates sequence of end points and midpoints
 * 
 * {1, 2, 3} -> {1, 3/2, 5/2, 3} */
enum ControlInsertions {
  ;
  /** @param geodesicSpace
   * @param tensor
   * @return */
  public static Tensor of(GeodesicSpace geodesicSpace, Tensor tensor) {
    if (Tensors.isEmpty(tensor))
      return Tensors.empty();
    Tensor result = Tensors.reserve(tensor.length() + 1);
    Iterator<Tensor> iterator = tensor.iterator();
    Tensor prev = iterator.next();
    result.append(prev);
    while (iterator.hasNext())
      result.append(geodesicSpace.midpoint(prev, prev = iterator.next()));
    result.append(Last.of(tensor));
    return result;
  }
}
