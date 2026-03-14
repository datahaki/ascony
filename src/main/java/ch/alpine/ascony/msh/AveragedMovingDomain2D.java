// code by jph
package ch.alpine.ascony.msh;

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
    return matrixArray.maps(w -> biinvariantMean.optional(target, w).orElse(fallback));
  }
}
