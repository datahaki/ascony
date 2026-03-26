// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.ascony.crv.SpikyPoly;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophus.hs.s.SnRotationMatrix;
import ch.alpine.sophus.lie.LieGroup;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.lie.so.So3Exponential;
import ch.alpine.sophus.lie.so.So3Group;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.TensorUnaryOperator;

/** orthogonal 3 x 3 matrices */
public class So3Display implements ManifoldDisplay {
  private static final Tensor SPIKY = SpikyPoly.normal(RealScalar.of(0.003));
  public static final Tensor REF = UnitVector.of(3, 2).unmodifiable();
  // ---
  public static final ManifoldDisplay INSTANCE = new So3Display();

  private So3Display() {
  }

  @Override // from ManifoldDisplay
  public int dimensions() {
    return 3;
  }

  @Override // from ManifoldDisplay
  public Tensor shape() {
    return SPIKY;
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    // use the point on S^2 as rotation axis
    Tensor xyz = S2Display.INSTANCE.xya2point(xya.extract(0, 2).append(RealScalar.ZERO));
    return SnRotationMatrix.of(REF, xyz).dot(So3Exponential.vectorExp(REF.multiply(xya.Get(2))));
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    Tensor xyz = p.get(Tensor.ALL, 2);
    Tensor rot = SnRotationMatrix.of(xyz, REF).dot(p);
    Tensor vec = So3Exponential.INSTANCE.vectorLog().apply(rot);
    return xyz.extract(0, 2).append(vec.Get(2));
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjectionM2P(Tensor xyz) {
    return null;
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor p) {
    // TODO design more like S2Display
    return Se2Matrix.of(point2xya(p));
  }

  @Override
  public LieGroup geodesicSpace() {
    return So3Group.INSTANCE;
  }

  @Override // from ManifoldDisplay
  public RenderInterface background() {
    return S2Background.INSTANCE;
  }

  @Override
  public final String toString() {
    return manifold().toString();
  }
}
