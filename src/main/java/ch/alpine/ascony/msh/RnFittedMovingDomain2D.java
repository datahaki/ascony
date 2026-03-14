// code by jph
package ch.alpine.ascony.msh;

import java.util.stream.IntStream;

import ch.alpine.sophis.fit.RigidMotionFit;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;

/** Reference:
 * "Weighted Averages on Surfaces"
 * by Daniele Panozzo, Ilya Baran, Olga Diamanti, Olga Sorkine-Hornung */
public class RnFittedMovingDomain2D extends MovingDomain2D {
  private final Tensor origin;
  final Tensor domain;

  /** @param origin
   * @param weights
   * @param domain */
  public RnFittedMovingDomain2D(Tensor origin, Tensor weights, Tensor domain) {
    super(weights);
    this.origin = origin;
    this.domain = domain;
  }

  @Override // from MovingDomain2D
  public Tensor[][] forward(Tensor target) {
    int rows = domain.length();
    int cols = Unprotect.dimension1(domain);
    Tensor[][] array = new Tensor[rows][cols];
    Tensor origin = origin();
    IntStream.range(0, rows).parallel().forEach(i -> IntStream.range(0, rows) //
        .forEach(j -> array[i][j] = RigidMotionFit.of(origin, target, matrixArray.get(i, j)).apply(domain.get(i, j))));
    return array;
  }

  public final Tensor origin() {
    return origin;
  }
}
