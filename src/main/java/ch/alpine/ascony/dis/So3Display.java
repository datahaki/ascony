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
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.TensorUnaryOperator;

/** orthogonal 3 x 3 matrices */
public class So3Display implements ManifoldDisplay {
  private static final Tensor SPIKY = SpikyPoly.normal(RealScalar.of(0.003));
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

  @Override
  public final boolean isXYeuclid() {
    return false;
  }

  @Override
  public final boolean isXY_Angle() {
    return false;
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    Tensor p = UnitVector.of(3, 2);
    Tensor q = S2Display.INSTANCE.xya2point(xya.extract(0, 2).append(RealScalar.ZERO));
    Tensor ang = So3Exponential.vectorExp(p.multiply(xya.Get(2).negate()));
    // Tolerance.CHOP.requireClose(p, ang.dot(p));
    Tensor rpq = SnRotationMatrix.of(q, p);
    // Tolerance.CHOP.requireClose(p, rpq.dot(q));
    Tensor res = Dot.of(ang, rpq);
    // Tolerance.CHOP.requireClose(p, res.dot(q));
    return Transpose.of(res);
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    Tensor u = UnitVector.of(3, 2);
    Tensor v = p.get(Tensor.ALL, 2);
    // IO.println("q=" + q);
    Tensor rpq = SnRotationMatrix.of(v, u);
    Tensor ang = rpq.dot(p);
    // IO.println("ANG=" + Pretty.of(ang));
    Tensor vec = So3Exponential.INSTANCE.vectorLog().apply(ang);
    // IO.println("vec=" + vec);
    // Tensor q = UnitVector.of(3, 2);
    // Tensor q = S2Display.INSTANCE.xya2point(xya.extract(0, 2).append(RealScalar.ZERO));
    // Tensor ang = So3Exponential.vectorExp(p.multiply(xya.Get(2)));
    // Tensor rpq = SnRotationMatrix.of(p, q);
    // return Dot.of(ang, rpq);
    return v.extract(0, 2).append(vec.Get(2));
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjection(Tensor xyz) {
    return null;
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor p) {
    return Se2Matrix.of(point2xya(p));
  }

  @Override
  public LieGroup geodesicSpace() {
    return So3Group.INSTANCE;
  }
  // TODO ASCONA ALG line distance should be similar to s^3

  @Override // from ManifoldDisplay
  public RenderInterface background() {
    return S2Background.INSTANCE;
  }

  @Override
  public final String toString() {
    return manifold().toString();
  }
}
