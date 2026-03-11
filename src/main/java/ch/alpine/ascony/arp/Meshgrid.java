// code by jph
package ch.alpine.ascony.arp;

import java.util.List;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.N;

/** @param cbb
 * @param res */
public record Meshgrid(CoordinateBoundingBox cbb, int resolution) {
  /** @param tuo that maps a vector {x, y} to a tensor
   * @return */
  public Tensor image(TensorUnaryOperator tuo) {
    Tensor dx = Subdivide.intermediate_increasing(cbb.clip(0), resolution).maps(N.DOUBLE);
    Tensor dy = Subdivide.intermediate_decreasing(cbb.clip(1), resolution).maps(N.DOUBLE);
    return Tensor.of(dy.stream().map(Scalar.class::cast) //
        .map(py -> Tensor.of(dx.stream().map(Scalar.class::cast) //
            .map(px -> tuo.apply(Unprotect.using(List.of(px, py)))) //
        )));
  }

  /** @return tensor with dimensions */
  public Tensor image() {
    return image(t -> t);
  }
}
