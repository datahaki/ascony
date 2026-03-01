// code by jph
package ch.alpine.ascony.ren;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.mat.HilbertMatrix;

class FixGridRenderTest {
  @Test
  void testFailMatrix() {
    assertThrows(Exception.class, () -> new FixGridRender(HilbertMatrix.of(3), HilbertMatrix.of(4)));
  }

  @Test
  void testFailScalar() {
    assertThrows(Exception.class, () -> new FixGridRender(RealScalar.ONE, RealScalar.ZERO));
  }

  @Test
  void testFailColorNull() {
    assertThrows(Exception.class, () -> new FixGridRender(Subdivide.of(1, 2, 3), Subdivide.of(1, 2, 3), null));
  }
}
