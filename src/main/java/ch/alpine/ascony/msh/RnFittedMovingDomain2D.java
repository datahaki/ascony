// code by jph
package ch.alpine.ascony.msh;

import java.util.stream.IntStream;

import ch.alpine.sophis.fit.RigidMotionFit;
import ch.alpine.sophus.bm.BiinvariantMean;
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
  public Tensor[][] forward(Tensor target, BiinvariantMean biinvariantMean) {
    int rows = domain.length();
    int cols = Unprotect.dimension1(domain);
    Tensor[][] array = new Tensor[rows][cols];
    Tensor origin = origin();
    IntStream.range(0, rows).parallel().forEach(cx -> IntStream.range(0, rows) //
        .forEach(cy -> array[cx][cy] = RigidMotionFit.of(origin, target, weights[cx][cy]).apply(domain.get(cx, cy))));
    return array;
  }

  public final Tensor origin() {
    return origin;
  }
}
