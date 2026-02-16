// code by jph
package ch.alpine.ascony.dis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.sophus.hs.HomogeneousSpace;
import ch.alpine.sophus.math.api.MetricManifold;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.opt.nd.BoxRandomSample;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.RandomSample;
import ch.alpine.tensor.pdf.RandomSampleInterface;
import ch.alpine.tensor.sca.Clips;

class ManifoldDisplaysTest {
  @Test
  void testSimple() {
    assertTrue(12 <= ManifoldDisplays.values().length);
  }

  @ParameterizedTest
  @EnumSource
  void testDimensions(ManifoldDisplays manifoldDisplays) {
    assertTrue(0 < manifoldDisplays.manifoldDisplay().dimensions());
  }

  @ParameterizedTest
  @EnumSource
  void testShape(ManifoldDisplays manifoldDisplays) {
    Tensor shape = manifoldDisplays.manifoldDisplay().shape();
    List<Integer> list = Dimensions.of(shape);
    assertEquals(list.get(1), 2);
    MatrixQ.require(shape);
  }

  @Disabled
  @ParameterizedTest
  @EnumSource
  void testProject(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    Tensor tensor = manifoldDisplay.xya2point(Array.zeros(3));
    assertNotNull(tensor);
    manifoldDisplay.matrixLift(tensor);
    assertThrows(Exception.class, () -> manifoldDisplay.xya2point(null));
  }

  @Disabled
  @ParameterizedTest
  @EnumSource
  void testToPoint(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    Tensor xya = Tensors.vector(0.1, 0.2, 0.3);
    Tensor p = manifoldDisplay.xya2point(xya);
    VectorQ.requireLength(manifoldDisplay.point2xy(p), 2);
    Tensor matrix = manifoldDisplay.matrixLift(p);
    assertEquals(Dimensions.of(matrix), List.of(3, 3));
    assertThrows(Exception.class, () -> manifoldDisplay.point2xy(null));
  }

  @ParameterizedTest
  @EnumSource
  void testMatrixLiftNull(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    assertThrows(Exception.class, () -> manifoldDisplay.matrixLift(null));
  }

  @ParameterizedTest
  @EnumSource
  void testGeodesicSpace(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    assertNotNull(manifoldDisplay.geodesicSpace());
  }

  @ParameterizedTest
  @EnumSource
  void testBiinvariantMean(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    if (manifoldDisplay.geodesicSpace() instanceof HomogeneousSpace homogeneousSpace) {
      assertNotNull(homogeneousSpace);
    }
  }

  @Test
  void testTensorMetric() {
    for (ManifoldDisplays manifoldDisplays : ManifoldDisplays.metricManifolds())
      assertTrue(manifoldDisplays.manifoldDisplay().geodesicSpace() instanceof MetricManifold);
  }

  @Test
  void testRandomSample() {
    for (ManifoldDisplays manifoldDisplays : ManifoldDisplays.manifolds()) {
      if (Objects.isNull(manifoldDisplays.manifoldDisplay().randomSampleInterface())) {
        System.err.println(manifoldDisplays);
        fail();
      }
    }
  }

  @ParameterizedTest
  @EnumSource
  void testList(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    RandomSampleInterface randomSampleInterface = manifoldDisplay.randomSampleInterface();
    if (Objects.nonNull(randomSampleInterface)) {
      Tensor p = RandomSample.of(randomSampleInterface);
      Tensor xya = manifoldDisplay.point2xya(p);
      Tensor q = manifoldDisplay.xya2point(xya);
      if (!manifoldDisplays.equals(ManifoldDisplays.So3))
        Tolerance.CHOP.requireClose(p, q);
    }
  }

  @ParameterizedTest
  @EnumSource
  void testToPoint2(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    RandomSampleInterface randomSampleInterface = manifoldDisplay.randomSampleInterface();
    if (Objects.nonNull(randomSampleInterface)) {
      Tensor p = RandomSample.of(randomSampleInterface);
      Tensor xya = manifoldDisplay.point2xya(p);
      Tensor xy_ = manifoldDisplay.point2xy(p);
      Tolerance.CHOP.requireClose(xya.extract(0, 2), xy_);
    }
  }

  @ParameterizedTest
  @EnumSource
  void testToPoint3(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    RandomSampleInterface randomSampleInterface = new BoxRandomSample(CoordinateBoundingBox.of(Clips.unit(), Clips.unit(), Clips.unit()));
    if (Objects.nonNull(randomSampleInterface)) {
      Tensor rand = RandomSample.of(randomSampleInterface);
      Tensor p = manifoldDisplay.xya2point(rand);
      Tensor xya = manifoldDisplay.point2xya(p);
      Tensor xy_ = manifoldDisplay.point2xy(p);
      Tolerance.CHOP.requireClose(xya.extract(0, 2), xy_);
    }
  }

  @Test
  void testHs() {
    for (ManifoldDisplays manifoldDisplays : ManifoldDisplays.manifolds())
      assertTrue(manifoldDisplays.manifoldDisplay().geodesicSpace() instanceof HomogeneousSpace);
  }

  @Test
  void testRaster() {
    assertTrue(5 <= ManifoldDisplays.d2Rasters().size());
  }

  @Test
  void testToString() {
    long count = ManifoldDisplays.ALL.stream().map(Object::toString).distinct().count();
    assertEquals(count, ManifoldDisplays.ALL.size());
  }
}
