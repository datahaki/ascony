// code by jph
package ch.alpine.ascony.api;

import ch.alpine.sophus.lie.rn.RnLineDistance;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.sca.ply.TripleReduceExtrapolation;

// TODO ASCONY name is not good
public enum RnLineTrim {
  ;
  public static final TripleReduceExtrapolation TRIPLE_REDUCE_EXTRAPOLATION = new TripleReduceExtrapolation() {
    @Override
    protected Scalar reduce(Tensor p, Tensor q, Tensor r) {
      return RnLineDistance.INSTANCE.distanceToLine(p, r).distance(q);
    }

    @Override
    protected Tensor petite(Tensor sequence) {
      return sequence.copy();
    };
  };
}
