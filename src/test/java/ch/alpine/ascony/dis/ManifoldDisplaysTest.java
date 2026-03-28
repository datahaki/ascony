// code by jph
package ch.alpine.ascony.dis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.ascony.ren.ColorPairs;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.PvmBuilder;
import ch.alpine.sophus.api.Manifold;
import ch.alpine.sophus.api.MetricManifold;
import ch.alpine.sophus.hs.HomogeneousSpace;
import ch.alpine.sophus.lie.se.SeNGroup;
import ch.alpine.tensor.RealScalar;
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
  private static final SeNGroup SE2_MATRIX_GROUP = new SeNGroup(2);

  @ParameterizedTest
  @EnumSource
  void testRandom(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay md = manifoldDisplays.manifoldDisplay();
    md.indetPoint();
    RandomSampleInterface randomSampleInterface = md.randomSampleInterface();
    Tensor p = RandomSample.of(randomSampleInterface);
    md.showPoints(ColorPairs.APPROXIMATION, RealScalar.ONE, Tensors.of(p));
    Tensor xy = md.point2xy(p);
    Tensor xya = md.point2xya(p);
    VectorQ.requireLength(xy, 2);
    VectorQ.requireLength(xya, 3);
    Tensor matrix = md.matrixLift(p);
    SE2_MATRIX_GROUP.isPointQ().require(matrix);
  }

  @ParameterizedTest
  @EnumSource
  void testBackground(ManifoldDisplays manifoldDisplays) {
    GeometricLayer geometricLayer = new GeometricLayer(PvmBuilder.rhs().digest());
    BufferedImage bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    manifoldDisplays.manifoldDisplay().background().render(geometricLayer, graphics);
    graphics.dispose();
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
    Tensor xy = md.point2xy(p);
    Tensor xya = md.point2xya(p);
    VectorQ.requireLength(xy, 2);
    VectorQ.requireLength(xya, 3);
    Tensor matrix = md.matrixLift(p);
    SE2_MATRIX_GROUP.isPointQ().require(matrix);
  }

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

  @ParameterizedTest
  @EnumSource
  void testList(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    RandomSampleInterface randomSampleInterface = manifoldDisplay.randomSampleInterface();
    Tensor p = RandomSample.of(randomSampleInterface);
    Tensor xya = manifoldDisplay.point2xya(p);
    Tensor q = manifoldDisplay.xya2point(xya);
    if (!manifoldDisplays.equals(ManifoldDisplays.So3))
      Tolerance.CHOP.requireClose(p, q);
  }

  @ParameterizedTest
  @EnumSource
  void testToPoint2(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    RandomSampleInterface randomSampleInterface = manifoldDisplay.randomSampleInterface();
    Tensor p = RandomSample.of(randomSampleInterface);
    Tensor xya = manifoldDisplay.point2xya(p);
    Tensor xy_ = manifoldDisplay.point2xy(p);
    Tolerance.CHOP.requireClose(xya.extract(0, 2), xy_);
  }

  @ParameterizedTest
  @EnumSource
  void testToPoint3(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    RandomSampleInterface randomSampleInterface = new BoxRandomSample(CoordinateBoundingBox.of(Clips.unit(), Clips.unit(), Clips.unit()));
    Tensor rand = RandomSample.of(randomSampleInterface);
    Tensor p = manifoldDisplay.xya2point(rand);
    Tensor xya = manifoldDisplay.point2xya(p);
    Tensor xy_ = manifoldDisplay.point2xy(p);
    Tolerance.CHOP.requireClose(xya.extract(0, 2), xy_);
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
