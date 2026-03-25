// code by jph
package ch.alpine.ascony.crv;

import ch.alpine.sophis.crv.d2.PolygonNormalize;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

public enum SpikyPoly {
  ;
  private static final Tensor POLYGON = Tensors.fromString("{{0,0},{1,0},{0.3,0.2},{0.2,0.3},{0,1}}");

  public static Tensor normal(Scalar area) {
    return PolygonNormalize.of(POLYGON, area);
  }
}
