// code by jph
package ch.alpine.ascony.win;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.sophus.lie.rn.RGroup;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

class ControlMidpointsTest {
  @Test
  void testSimple() {
    Tensor tensor = ControlInsertions.of(RGroup.INSTANCE, Tensors.vector(1, 2, 3));
    assertEquals(tensor.toString(), "{1, 3/2, 5/2, 3}");
  }
}
