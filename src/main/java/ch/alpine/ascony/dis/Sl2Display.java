// code by jph
package ch.alpine.ascony.dis;

import java.io.Serializable;

import ch.alpine.ascony.api.Spearhead;
import ch.alpine.ascony.ren.FixGridRender;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.d2.PolygonNormalize;
import ch.alpine.sophus.lie.LieGroup;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.sophus.lie.sl.Sl2Iwasawa;
import ch.alpine.sophus.lie.sl.SlNGroup;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.pdf.RandomSampleInterface;

public class Sl2Display implements ManifoldDisplay, Serializable {
  public static final ManifoldDisplay INSTANCE = new Sl2Display();
  private static final Scalar SCALE = RealScalar.of(0.1);
  // ---
  private final SlNGroup slNGroup = new SlNGroup(2);
  private final FixGridRender fixGridRender = new FixGridRender(Subdivide.of(-10, 10, 20), Subdivide.of(-10, 10, 20));

  private Sl2Display() {
  }

  @Override
  public int dimensions() {
    return 3;
  }

  @Override
  public Tensor shape() {
    Tensor polygon = Spearhead.of(Tensors.vector(0.86, 0.06, 1.05), RealScalar.of(0.05));
    return PolygonNormalize.of(polygon, RealScalar.of(0.02));
  }

  @Override
  public Tensor xya2point(Tensor xya) {
    return new Sl2Iwasawa(xya.Get(2), xya.Get(0).multiply(SCALE), xya.Get(1)).matrix();
  }

  @Override
  public Tensor point2xya(Tensor p) {
    Sl2Iwasawa iwasawa = Sl2Iwasawa.from(p);
    return Tensors.of(iwasawa.t().divide(SCALE), iwasawa.s(), iwasawa.theta());
  }

  @Override
  public Tensor matrixLift(Tensor p) {
    return Se2Matrix.of(point2xya(p));
  }

  @Override
  public LieGroup geodesicSpace() {
    return slNGroup;
  }

  @Override
  public TensorUnaryOperator tangentProjection(Tensor p) {
    return v -> v.extract(0, 2); // TODO !?
  }

  @Override
  public RandomSampleInterface randomSampleInterface() {
    return slNGroup;
  }

  @Override
  public RenderInterface background() {
    return fixGridRender;
  }
}
