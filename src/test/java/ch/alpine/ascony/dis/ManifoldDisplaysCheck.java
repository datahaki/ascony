// code by jph
package ch.alpine.ascony.dis;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Objects;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.sophus.api.Manifold;
import ch.alpine.sophus.lie.se.SeNGroup;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.pdf.RandomSample;
import ch.alpine.tensor.pdf.RandomSampleInterface;
import ch.alpine.tensor.sca.Round;

class ManifoldDisplaysCheck {
  private static final SeNGroup SE2_MATRIX_GROUP = new SeNGroup(2);

  @ParameterizedTest
  @EnumSource
  void testRandom(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay md = manifoldDisplays.manifoldDisplay();
    RandomSampleInterface randomSampleInterface = md.randomSampleInterface();
    Objects.requireNonNull(randomSampleInterface);
    Tensor p = RandomSample.of(randomSampleInterface);
    IO.println("---");
    IO.println(md);
    IO.println(p.maps(Round._3));
    Tensor xy = md.point2xy(p);
    Tensor xya = md.point2xya(p);
    VectorQ.requireLength(xy, 2);
    VectorQ.requireLength(xya, 3);
    IO.println(xy.maps(Round._3));
    IO.println(xya.maps(Round._3));
    Tensor matrix = md.matrixLift(p);
    SE2_MATRIX_GROUP.isPointQ().require(matrix);
  }

  @ParameterizedTest
  @EnumSource
  void testSimple(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay md = manifoldDisplays.manifoldDisplay();
    Manifold manifold = md.manifold();
    assumeTrue(Objects.nonNull(manifold));
    RandomSampleInterface randomSampleInterface = md.randomSampleInterface();
    Tensor p = RandomSample.of(randomSampleInterface);
    manifold.isPointQ().require(p);
    IO.println("--- HERE");
    IO.println(manifold);
    IO.println(p.maps(Round._3));
    Tensor xy = md.point2xy(p);
    Tensor xya = md.point2xya(p);
    VectorQ.requireLength(xy, 2);
    VectorQ.requireLength(xya, 3);
    IO.println(xy.maps(Round._3));
    IO.println(xya.maps(Round._3));
    Tensor matrix = md.matrixLift(p);
    SE2_MATRIX_GROUP.isPointQ().require(matrix);
  }
}
