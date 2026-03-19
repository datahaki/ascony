// code by jph
package ch.alpine.ascony.dat;

import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

/** [N x 2 x 3] */
public record ControlPosVelSe2(Tensor points) {
  /** @return control points for selected {@link ManifoldDisplay} */
  public Tensor getGeodesicControlPoints(ManifoldDisplay manifoldDisplay) {
    return getGeodesicControlPoints(manifoldDisplay, 0, Integer.MAX_VALUE);
  }

  /** @param skip
   * @param maxSize
   * @return */
  public Tensor getGeodesicControlPoints(ManifoldDisplay manifoldDisplay, int skip, int maxSize) {
    return Tensor.of(points.stream() //
        .skip(skip) //
        .limit(maxSize) //
        .map(pv -> Tensors.of( //
            manifoldDisplay.xya2point(pv.get(0)), //
            manifoldDisplay.uvw2log(pv.get(1)))));
  }

  public int length() {
    return points.length();
  }
}
