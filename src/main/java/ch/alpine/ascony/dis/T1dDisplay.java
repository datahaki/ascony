// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;

import ch.alpine.ascony.api.Box2D;
import ch.alpine.ascony.arp.D2Raster;
import ch.alpine.ascony.ren.AxesRender;
import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.sophis.decim.LineDistance;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.lie.td.TdGroup;
import ch.alpine.sophus.lie.td.TdRandomSample;
import ch.alpine.sophus.math.api.GeodesicSpace;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.RandomSampleInterface;
import ch.alpine.tensor.pdf.c.ExponentialDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.exp.Exp;
import ch.alpine.tensor.sca.exp.Log;

public enum T1dDisplay implements ManifoldDisplay {
  INSTANCE;

  private static final Tensor PENTAGON = CirclePoints.of(5).multiply(RealScalar.of(0.1)).unmodifiable();

  @Override // from ManifoldDisplay
  public int dimensions() {
    return 2;
  }

  @Override // from ManifoldDisplay
  public TensorUnaryOperator tangentProjection(Tensor p) {
    return null;
  }

  @Override // from ManifoldDisplay
  public Tensor shape() {
    return PENTAGON;
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    Tensor point = xya.extract(0, 2);
    point.set(Exp.FUNCTION, 1);
    return point;
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    return Append.of(point2xy(p), RealScalar.ZERO);
  }

  @Override // from ManifoldDisplay
  public Tensor point2xy(Tensor p) {
    Tensor q = VectorQ.requireLength(p, 2).copy();
    q.set(Log.FUNCTION, 1);
    return q;
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor p) {
    // Scalar f = Exp.FUNCTION.apply(p.Get(1).subtract(RealScalar.of(10)));
    // Tensor diag = DiagonalMatrix.of(f, f, RealScalar.ONE);
    return Se2Matrix.translation(point2xy(p)); // .dot(diag);
  }

  @Override // from ManifoldDisplay
  public GeodesicSpace geodesicSpace() {
    return TdGroup.INSTANCE;
  }

  @Override // from ManifoldDisplay
  public LineDistance lineDistance() {
    return null;
  }

  @Override // from ManifoldDisplay
  public RandomSampleInterface randomSampleInterface() {
    return new TdRandomSample(UniformDistribution.of(-3, 3), 1, ExponentialDistribution.standard());
  }

  @Override // from ManifoldDisplay
  public RenderInterface background() {
    return AxesRender.INSTANCE;
  }

  @Override
  public D2Raster d2Raster() {
    return new D2Raster() {
      @Override // D2Raster
      public Optional<Tensor> d2lift(Tensor pxy) {
        return Optional.of(xya2point(pxy));
      }

      @Override // D2Raster
      public CoordinateBoundingBox coordinateBoundingBox() {
        return Box2D.xy(Clips.absolute(3));
      }
    };
  }
}
