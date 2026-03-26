// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;

import ch.alpine.ascony.crv.Box2D;
import ch.alpine.ascony.msh.D2Raster;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophus.hs.s.SnRotationMatrix;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.alg.PadRight;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.ext.PackageTestAccess;
import ch.alpine.tensor.lie.rot.AngleVector;
import ch.alpine.tensor.mat.NullSpace;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.nrm.Vector2NormSquared;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.red.CopySign;
import ch.alpine.tensor.red.Times;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.pow.Sqrt;

/** symmetric positive definite 2 x 2 matrices */
public class S2Display extends SnDisplay {
  private static final TensorUnaryOperator PAD_RIGHT = PadRight.zeros(3, 3);
  // ---
  public static final S2Display INSTANCE = new S2Display();
  private static final Tensor REF_PNT = UnitVector.of(3, 2);

  private S2Display() {
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

  @Override
  public Tensor point2xya(Tensor p) {
    return p.copy();
  }

  /** @param xyz vector with Vector2Norm == 1, point on 2-dimensional sphere
   * @return 2 x 3 matrix with rows spanning the space tangent to given xyz */
  Tensor tangentSpaceM2P(Tensor xyz) {
    if (Tolerance.CHOP.isClose(REF_PNT, xyz.negate()))
      return NullSpace.of(Tensors.of(xyz));
    // hs transport
    return SnRotationMatrix.of(xyz, REF_PNT).extract(0, 2);
  }

  @Override // from ManifoldDisplay
  public TensorUnaryOperator tangentProjectionM2P(Tensor xyz) {
    return tangentSpaceM2P(xyz)::dot;
  }

  @PackageTestAccess
  Tensor createTangent(Tensor xya) {
    return createTangent(xya, xya.Get(2));
  }

  public Tensor createTangent(Tensor xya, Scalar angle) {
    Tensor xyz = xya2point(xya);
    return AngleVector.of(angle).dot(tangentSpaceM2P(xyz));
  }

  public static Optional<Tensor> optionalZ(Tensor xya) {
    Tensor xy = xya.extract(0, 2);
    Scalar normsq = Vector2NormSquared.of(xy);
    if (Scalars.lessThan(normsq, RealScalar.ONE)) {
      Scalar z = Sqrt.FUNCTION.apply(RealScalar.ONE.subtract(normsq));
      return Optional.of(xy.append(CopySign.of(z, xya.Get(2))));
    }
    return Optional.empty();
  }

  private static final Clip PERSPECTIVE_Z = Clips.interval(-2.5, 1);

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor xyz) {
    Tensor frame = tangentSpaceM2P(xyz);
    Tensor skew = PAD_RIGHT.apply(Transpose.of(Tensors.of( //
        frame.get(0).extract(0, 2), //
        frame.get(1).extract(0, 2))));
    skew.set(RealScalar.ONE, 2, 2);
    Scalar r = PERSPECTIVE_Z.rescale(xyz.Get(2)); // shrink the frame
    skew = Times.of(Tensors.of(r, r, RealScalar.ONE), skew);
    // IO.println(Pretty.of(skew.maps(Round._3)));
    return Se2Matrix.translation(point2xy(xyz)).dot(skew);
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
