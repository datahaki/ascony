// code by jph
package ch.alpine.ascony.api;

import java.util.Objects;

import ch.alpine.sophis.dv.Biinvariant;
import ch.alpine.sophis.dv.HsCoordinates;
import ch.alpine.sophis.dv.Sedarim;
import ch.alpine.sophis.gbc.d2.InsideConvexHullCoordinate;
import ch.alpine.sophus.math.Genesis;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.api.TensorScalarFunction;

public record InsideConvexHullLogWeighting(Genesis genesis) implements LogWeighting {
  @Override // from LogWeighting
  public Sedarim sedarim( //
      Biinvariant biinvariant, // only used for hsDesign
      ScalarUnaryOperator variogram, // <- ignored
      Tensor sequence) {
    return HsCoordinates.wrap( //
        biinvariant.manifold(), //
        new InsideConvexHullCoordinate(genesis), //
        sequence);
  }

  @Override // from LogWeighting
  public TensorScalarFunction function( //
      Biinvariant biinvariant, // only used for hsDesign
      ScalarUnaryOperator variogram, // <- ignored
      Tensor sequence, Tensor values) {
    Sedarim sedarim = sedarim(biinvariant, variogram, sequence);
    Objects.requireNonNull(values);
    return point -> (Scalar) sedarim.sunder(point).dot(values);
  }
}
