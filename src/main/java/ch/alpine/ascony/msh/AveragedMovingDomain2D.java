// code by jph
package ch.alpine.ascony.msh;

import ch.alpine.sophus.bm.BiinvariantMean;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;

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
    return matrixArray.maps(weights -> biinvariantMean.optional(target, weights).orElse(fallback));
  }

  /** @param target
   * @param tuo for instance color extraction from image
   * @return */
  public Tensor forward(Tensor target, TensorUnaryOperator tuo) {
    return matrixArray.maps_lift(weights -> biinvariantMean.optional(target, weights).map(tuo).orElse(fallback));
  }
}
