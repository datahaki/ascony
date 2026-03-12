// code by jph
package ch.alpine.ascony.msh;

import java.util.stream.IntStream;

import ch.alpine.sophus.bm.BiinvariantMean;
import ch.alpine.tensor.Tensor;

/** Reference:
 * "Weighted Averages on Surfaces"
 * by Daniele Panozzo, Ilya Baran, Olga Diamanti, Olga Sorkine-Hornung */
public class AveragedMovingDomain2D extends MovingDomain2D {
  private final BiinvariantMean biinvariantMean;
  private final Tensor fallback;

  /** @param origin
   * @param sedarim
   * @param domain
   * @param ConstantArray.of(DoubleScalar.INDETERMINATE, 3) */
  public AveragedMovingDomain2D(Tensor weights, BiinvariantMean biinvariantMean, Tensor fallback) {
    super(weights);
    this.biinvariantMean = biinvariantMean;
    this.fallback = fallback;
  }

  @Override // from MovingDomain2D
  public Tensor[][] forward(Tensor target) {
    int rows = weights.rows();
    int cols = weights.cols();
    Tensor[][] array = new Tensor[rows][cols];
    IntStream.range(0, rows).parallel().forEach(cx -> IntStream.range(0, rows) //
        .forEach(cy -> array[cx][cy] = biinvariantMean.optional(target, weights.get(cx, cy)).orElse(fallback)));
    return array;
  }
}
