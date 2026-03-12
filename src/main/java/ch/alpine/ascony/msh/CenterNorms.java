// code by jph
package ch.alpine.ascony.msh;

import ch.alpine.sophis.crv.d2.ex.Box2D;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.opt.nd.NdCenterInterface;
import ch.alpine.tensor.opt.nd.NdCenters;

public enum CenterNorms {
  _1 {
    @Override
    public NdCenterInterface ndCenterInterface(Tensor center) {
      return NdCenters.VECTOR_1_NORM.apply(center);
    }

    @Override
    public Tensor shape() {
      return CirclePoints.of(4);
    }
  },
  _2 {
    @Override
    public NdCenterInterface ndCenterInterface(Tensor center) {
      return NdCenters.VECTOR_2_NORM.apply(center);
    }

    @Override
    public Tensor shape() {
      return CirclePoints.of(45);
    }
  },
  _INF {
    @Override
    public NdCenterInterface ndCenterInterface(Tensor center) {
      return NdCenters.VECTOR_INFINITY_NORM.apply(center);
    }

    @Override
    public Tensor shape() {
      return Box2D.ABSOLUTE_ONE;
    }
  };

  public abstract NdCenterInterface ndCenterInterface(Tensor center);

  public abstract Tensor shape();
}
