// code by jph
package ch.alpine.ascony.msh;

import java.util.stream.IntStream;

import ch.alpine.sophus.bm.BiinvariantMean;
import ch.alpine.tensor.Tensor;

/** Reference:
 * "Weighted Averages on Surfaces"
 * by Daniele Panozzo, Ilya Baran, Olga Diamanti, Olga Sorkine-Hornung */
public class AveragedMovingDomain2D extends MovingDomain2D {
  private final Tensor fallback;

  /** @param origin
   * @param sedarim
   * @param domain
   * @param ConstantArray.of(DoubleScalar.INDETERMINATE, 3) */
  public AveragedMovingDomain2D(Tensor weights, Tensor fallback) {
    super(weights);
    this.fallback = fallback;
  }

  @Override // from MovingDomain2D
  public Tensor[][] forward(Tensor target, BiinvariantMean biinvariantMean) {
    int rows = weights.length;
    int cols = weights[0].length;
    Tensor[][] array = new Tensor[rows][cols];
    IntStream.range(0, rows).parallel() //
        .forEach(cx -> IntStream.range(0, rows).forEach(cy -> {
          try {
            array[cx][cy] = biinvariantMean.mean(target, weights[cx][cy]);
          } catch (Exception e) {
            array[cx][cy] = fallback;
          }
        }));
    return array;
  }
}
