// code by jph
package ch.alpine.ascony.ren;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.sophis.ref.d1.CurveSubdivision;
import ch.alpine.sophus.lie.rn.RGroup;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

class ControlMidpointsTest {
  @Test
  void testSimple() {
    CurveSubdivision curveSubdivision = new ControlMidpoints(RGroup.INSTANCE);
    Tensor tensor = curveSubdivision.string(Tensors.vector(1, 2, 3));
    assertEquals(tensor.toString(), "{1, 3/2, 5/2, 3}");
  }
}
