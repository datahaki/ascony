// code by jph
package ch.alpine.ascony.dis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.sophus.lie.rn.RGroup;
import ch.alpine.sophus.lie.rn.RnGroup;

class R2DisplayTest {
  @Test
  void testSimple() {
    assertTrue(R2Display.INSTANCE.geodesicSpace() instanceof RGroup);
    assertTrue(R2Display.INSTANCE.geodesicSpace() instanceof RnGroup);
  }
}
