// code by jph
package ch.alpine.ascony.dis;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ch.alpine.sophus.hs.rpn.HemisphereRandomSample;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.pdf.RandomSample;
import ch.alpine.tensor.pdf.RandomSampleInterface;

@Disabled
class So3DisplayTest {
  @Test
  void testZero() {
    Tensor orth = So3Display.INSTANCE.xya2point(Tensors.vector(0, 0, 0));
    // IO.println(Pretty.of(orth.maps(Round._3)));
    Tolerance.CHOP.requireClose(Det.of(orth), RealScalar.ONE);
    So3Display.INSTANCE.point2xya(orth);
  }

  @Test
  void testXY() {
    Tensor orth = So3Display.INSTANCE.xya2point(Tensors.vector(0.1, 0, 0));
    // IO.println(Pretty.of(orth.maps(Round._3)));
    Tolerance.CHOP.requireClose(Det.of(orth), RealScalar.ONE);
  }

  @Test
  void testXYA() {
    RandomSampleInterface rsi = HemisphereRandomSample.of(2);
    Tensor xya = RandomSample.of(rsi);
    IO.println(xya);
    Tensor orth = So3Display.INSTANCE.xya2point(xya);
    // IO.println(Pretty.of(orth.maps(Round._3)));
    Tolerance.CHOP.requireClose(Det.of(orth), RealScalar.ONE);
    Tensor so3 = So3Display.INSTANCE.point2xya(orth);
    IO.println(so3);
  }
}
