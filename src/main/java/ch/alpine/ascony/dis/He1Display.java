// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.ascony.ren.EmptyRender;
import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.sophis.decim.LineDistance;
import ch.alpine.sophus.lie.LieGroup;
import ch.alpine.sophus.lie.he.HeNGroup;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.pdf.RandomSampleInterface;

public enum He1Display implements ManifoldDisplay {
  INSTANCE;

  private static final Tensor SQUARE = CirclePoints.of(4).multiply(RealScalar.of(0.2)).unmodifiable();

  @Override // from ManifoldDisplay
  public int dimensions() {
    return 3;
  }

  @Override // from ManifoldDisplay
  public Tensor shape() {
    return SQUARE;
  }

  @Override // from ManifoldDisplay
  public Tensor xya2point(Tensor xya) {
    return xya.copy();
  }

  @Override // from ManifoldDisplay
  public Tensor point2xya(Tensor p) {
    return p.copy();
  }

  @Override // from ManifoldDisplay
  public final TensorUnaryOperator tangentProjection(Tensor xyz) {
    return null;
  }

  @Override // from ManifoldDisplay
  public Tensor matrixLift(Tensor p) {
    return Se2Matrix.of(p);
  }

  @Override
  public LieGroup geodesicSpace() {
    return new HeNGroup(1);
  }

  @Override // from ManifoldDisplay
  public LineDistance lineDistance() {
    return null;
  }

  @Override // from ManifoldDisplay
  public RandomSampleInterface randomSampleInterface() {
    return new HeNGroup(1);
  }

  @Override // from ManifoldDisplay
  public RenderInterface background() {
    return EmptyRender.INSTANCE;
  }

  @Override // from Object
  public String toString() {
    return "He1";
  }
}
