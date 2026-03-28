// code by jph
package ch.alpine.ascony.crv;

import org.junit.jupiter.api.Test;

import ch.alpine.sophis.crv.d2.PolygonArea;
import ch.alpine.tensor.sca.Sign;

class SpikyPolyTest {
  @Test
  void test() {
    Sign.requirePositive(PolygonArea.of(SpikyPoly.POLYGON));
  }
}
