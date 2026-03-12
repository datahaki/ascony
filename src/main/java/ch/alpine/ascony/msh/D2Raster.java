// code by jph
package ch.alpine.ascony.msh;

import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public abstract class D2Raster {
  /** @param d2Raster
   * @param resolution n
   * @param arrayFunction maps points from the manifold to a color
   * @return a tensor with dimensions [n, n, ?] which can be made
   * input to {@link Rescale}, and {@link ImageFormat}.
   * @see ArrayFunction */
  public final <T extends Tensor> Tensor of(ArrayFunction<T> arrayFunction, CoordinateBoundingBox cbb, int resolution) {
    return new Meshgrid(cbb, resolution).image(xy -> arrayFunction.apply(d2lift(xy)));
  }

  /** in some simple cases the implementation is just
   * Optional.of(project(pxy))
   * 
   * @param pxy vector of the form {px, py}
   * @return point on manifold, or empty */
  public abstract Optional<Tensor> d2lift(Tensor pxy);
}
