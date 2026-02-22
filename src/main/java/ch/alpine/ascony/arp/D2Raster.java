// code by jph
package ch.alpine.ascony.arp;

import java.util.List;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.N;

public abstract class D2Raster {
  /** @param d2Raster
   * @param resolution n
   * @param arrayFunction maps points from the manifold to a color
   * @return a tensor with dimensions [n, n, ?] which can be made
   * input to {@link Rescale}, and {@link ImageFormat}.
   * @see ArrayFunction */
  public final <T extends Tensor> Tensor of(int resolution, ArrayFunction<T> arrayFunction) {
    CoordinateBoundingBox coordinateBoundingBox = coordinateBoundingBox();
    Tensor dx = Subdivide.intermediate_increasing(coordinateBoundingBox.clip(0), resolution).maps(N.DOUBLE);
    Tensor dy = Subdivide.intermediate_decreasing(coordinateBoundingBox.clip(1), resolution).maps(N.DOUBLE);
    return Tensor.of(dy.stream().map(Scalar.class::cast).parallel() //
        .map(py -> Tensor.of(dx.stream().map(Scalar.class::cast) //
            .map(px -> Unprotect.using(List.of(px, py))) //
            .map(this::d2lift) //
            .map(arrayFunction))));
  }

  /** in some simple cases the implementation is just
   * Optional.of(project(pxy))
   * 
   * @param pxy vector of the form {px, py}
   * @return point on manifold, or empty */
  public abstract Optional<Tensor> d2lift(Tensor pxy);

  /** @return 2-dimensional bounding box to sample within */
  public abstract CoordinateBoundingBox coordinateBoundingBox();
}
