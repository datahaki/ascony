// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;

import ch.alpine.ascony.crv.Box2D;
import ch.alpine.ascony.msh.D2Raster;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.nrm.Vector2NormSquared;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.pow.Sqrt;

/** symmetric positive definite 2 x 2 matrices */
public class Rp2Display extends RpnDisplay {
  public static final ManifoldDisplay INSTANCE = new Rp2Display();

  private Rp2Display() {
    super(2);
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    Tensor xyz = xya.copy();
    Optional<Tensor> optional = optionalZ(xyz);
    if (optional.isPresent())
      return optional.get();
    xyz.set(RealScalar.ZERO, 2);
    // intersection of front and back hemisphere
    return Vector2Norm.NORMALIZE.apply(xyz);
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    return p.copy();
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjectionM2P(Tensor xyz) {
    return null;
  }

  public static Optional<Tensor> optionalZ(Tensor xya) {
    Tensor xy = xya.extract(0, 2);
    Scalar normsq = Vector2NormSquared.of(xy);
    if (Scalars.lessThan(normsq, RealScalar.ONE)) {
      Scalar z = Sqrt.FUNCTION.apply(RealScalar.ONE.subtract(normsq));
      return Optional.of(xy.append(z));
    }
    return Optional.empty();
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor xyz) {
    return S2Display.INSTANCE.matrixLift(xyz);
  }

  @Override
  public D2Raster d2Raster() {
    return new D2Raster() {
      @Override // from GeodesicArrayPlot
      public Optional<Tensor> d2lift(Tensor point) {
        Scalar z2 = RealScalar.ONE.subtract(Vector2NormSquared.of(point));
        return Optional.ofNullable(Sign.isPositive(z2) ? Append.of(point, Sqrt.FUNCTION.apply(z2)) : null);
      }
    };
  }

  @Override
  public CoordinateBoundingBox d2Raster_coordinateBoundingBox() {
    return Box2D.xy(Clips.absolute(1));
  }

  @Override
  public RenderInterface background() {
    return S2Background.INSTANCE;
  }
}
