// code by jph
package ch.alpine.ascony.api;

import ch.alpine.sophis.api.Genesis;
import ch.alpine.sophis.dv.AffineCoordinate;
import ch.alpine.sophis.gbc.d2.IterativeCoordinateLevel;
import ch.alpine.sophis.gbc.d2.ThreePointScalings;
import ch.alpine.sophis.gbc.d2.ThreePointWeighting;
import ch.alpine.sophus.api.Manifold;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorScalarFunction;
import ch.alpine.tensor.sca.Chop;

public enum IterativeGenesis {
  MEAN_VALUE(new ThreePointWeighting(ThreePointScalings.MEAN_VALUE)),
  INVERSE_DISTANCE(AffineCoordinate.INSTANCE);

  private final Genesis genesis;

  IterativeGenesis(Genesis genesis) {
    this.genesis = genesis;
  }

  public TensorScalarFunction with(int max) {
    return new IterativeCoordinateLevel(genesis, Chop._08, max);
  }

  public TensorScalarFunction counts(Manifold manifold, Tensor sequence, int max) {
    TensorScalarFunction tsf = with(max);
    return point -> tsf.apply(manifold.tangentSpace(point).log().slash(sequence));
  }
}
