// code by jph
package ch.alpine.ascony.dis;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;

class T1dDisplayTest {
  @Test
  void test() {
    Tensor xya = Tensors.vector(-1, -2, -3);
    Tensor q = Td1Display.INSTANCE.xya2point(xya);
    Tensor xy0 = Td1Display.INSTANCE.point2xya(q);
    Tolerance.CHOP.requireClose(xya.extract(0, 2), xy0.extract(0, 2));
  }
}
