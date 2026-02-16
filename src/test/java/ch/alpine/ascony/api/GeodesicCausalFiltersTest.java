// code by jph
package ch.alpine.ascony.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.sophus.hs.GeodesicSpace;
import ch.alpine.sophus.hs.HomogeneousSpace;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.sca.win.WindowFunctions;

class GeodesicCausalFiltersTest {
  @ParameterizedTest
  @EnumSource
  void testSimple(ManifoldDisplays manifoldDisplays) {
    ManifoldDisplay manifoldDisplay = manifoldDisplays.manifoldDisplay();
    GeodesicSpace geodesicSpace = manifoldDisplay.geodesicSpace();
    if (geodesicSpace instanceof HomogeneousSpace)
      for (WindowFunctions smoothingKernel : WindowFunctions.values())
        for (int radius = 0; radius < 3; ++radius)
          for (GeodesicCausalFilters geodesicCausalFilters : GeodesicCausalFilters.values()) {
            TensorUnaryOperator tensorUnaryOperator = geodesicCausalFilters.supply(manifoldDisplay, smoothingKernel.get(), radius, Rational.HALF);
            assertNotNull(tensorUnaryOperator);
          }
  }
}
