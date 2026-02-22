// code by jph
package ch.alpine.ascony.dis;

import java.util.Optional;
import java.util.stream.Stream;

import ch.alpine.ascony.arp.D2Raster;
import ch.alpine.ascony.ren.AxesRender;
import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.sophis.decim.LineDistance;
import ch.alpine.sophus.lie.cn.Complex1LieGroup;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.math.api.GeodesicSpace;
import ch.alpine.tensor.ComplexScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.num.ReIm;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomSampleInterface;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum C1Display implements ManifoldDisplay {
  INSTANCE;

  private static final Tensor SHAPE = CirclePoints.of(9).multiply(RealScalar.of(0.04));
  private static final Clip CLIP = Clips.absolute(2.0);

  @Override
  public int dimensions() {
    return 1;
  }

  @Override
  public Tensor shape() {
    return SHAPE;
  }

  @Override
  public Tensor xya2point(Tensor xya) {
    return ComplexScalar.of(xya.Get(0), xya.Get(1));
  }

  @Override
  public Tensor point2xya(Tensor p) {
    return point2xy(p).append(RealScalar.ZERO);
  }

  @Override
  public Tensor point2xy(Tensor p) {
    return ReIm.of((Scalar) p).vector();
  }

  @Override
  public Tensor matrixLift(Tensor p) {
    return Se2Matrix.translation(point2xy(p)); //
  }

  @Override
  public GeodesicSpace geodesicSpace() {
    return Complex1LieGroup.INSTANCE;
  }

  @Override
  public TensorUnaryOperator tangentProjection(Tensor p) {
    return null;
  }

  @Override
  public LineDistance lineDistance() {
    return null;
  }

  @Override
  public RandomSampleInterface randomSampleInterface() {
    // Distribution distribution = NormalDistribution.standard();
    Distribution distribution = UniformDistribution.of(CLIP);
    return randomGenerator -> ComplexScalar.of( //
        RandomVariate.of(distribution, randomGenerator), //
        RandomVariate.of(distribution, randomGenerator));
  }

  @Override
  public RenderInterface background() {
    return AxesRender.INSTANCE; // EmptyRender.INSTANCE;
  }

  @Override
  public D2Raster d2Raster() {
    return new D2Raster() {
      @Override // from D2Raster
      public Optional<Tensor> d2lift(Tensor pxy) {
        return Optional.of(ComplexScalar.of(pxy.Get(0), pxy.Get(1)));
      }

      @Override // from D2Raster
      public CoordinateBoundingBox coordinateBoundingBox() {
        return CoordinateBoundingBox.of(Stream.generate(() -> CLIP).limit(2));
      }
    };
  }
}
