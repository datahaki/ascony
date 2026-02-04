// code by jph
package ch.alpine.ascony.api;

import ch.alpine.sophis.crv.d2.TripleReduceExtrapolation;
import ch.alpine.sophis.decim.RnLineDistance;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;

// TODO ASCONA name is not good
public enum RnLineTrim {
  ;
  public static final TripleReduceExtrapolation TRIPLE_REDUCE_EXTRAPOLATION = new TripleReduceExtrapolation() {
    @Override
    protected Scalar reduce(Tensor p, Tensor q, Tensor r) {
      return RnLineDistance.INSTANCE.tensorNorm(p, r).norm(q);
    }
  };
}
