// code by jph
package ch.alpine.ascony.msh;

import java.util.Objects;

import ch.alpine.tensor.Tensor;

/** Reference:
 * "Weighted Averages on Surfaces"
 * by Daniele Panozzo, Ilya Baran, Olga Diamanti, Olga Sorkine-Hornung */
public abstract class MovingDomain2D {
  protected final Tensor[][] weights;
  /* for visualization only */
  private Tensor _wgs = null;

  /** @param origin reference control points that will be associated to given targets
   * @param sedarim
   * @param domain */
  protected MovingDomain2D(Tensor weights) {
    this.weights = MatrixArray.of(weights);
  }

  /** @param target
   * @return array for imaging and rendering purpose */
  public abstract Tensor[][] forward(Tensor target);

  /** @return array of weights for visualization */
  public final Tensor arrayReshape_weights() {
    if (Objects.isNull(_wgs))
      _wgs = ImageTiling.of(MatrixArray.byRef(weights));
    return _wgs;
  }
}
