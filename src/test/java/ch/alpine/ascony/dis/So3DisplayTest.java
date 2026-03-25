// code by jph
package ch.alpine.ascony.dis;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import ch.alpine.sophus.hs.rpn.HemisphereRandomSample;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.pdf.RandomSample;
import ch.alpine.tensor.pdf.RandomSampleInterface;
import ch.alpine.tensor.sca.Chop;

class So3DisplayTest {
  @Test
  void testZero() {
    Tensor xya = Tensors.vector(0, 0, 1);
    Tensor orth = So3Display.INSTANCE.xya2point(xya);
    // IO.println(Pretty.of(orth.maps(Round._3)));
    Tolerance.CHOP.requireClose(Det.of(orth), RealScalar.ONE);
    Tensor rec = So3Display.INSTANCE.point2xya(orth);
    Tolerance.CHOP.requireClose(xya, rec);
  }

  @Test
  void testXY() {
    Tensor xya = Tensors.vector(0.1, 0, 0);
    Tensor orth = So3Display.INSTANCE.xya2point(xya);
    // IO.println(Pretty.of(orth.maps(Round._3)));
    Tolerance.CHOP.requireClose(Det.of(orth), RealScalar.ONE);
    Tensor rec = So3Display.INSTANCE.point2xya(orth);
    Tolerance.CHOP.requireClose(xya, rec);
  }

  @RepeatedTest(10)
  void testXYA() {
    RandomSampleInterface rsi = HemisphereRandomSample.of(2);
    Tensor xya = RandomSample.of(rsi);
    // IO.println("INP="+xya);
    Tensor orth = So3Display.INSTANCE.xya2point(xya);
    Tolerance.CHOP.requireClose(Det.of(orth), RealScalar.ONE);
    Tensor rec = So3Display.INSTANCE.point2xya(orth);
    // IO.println("OUT="+rec);
    Chop._08.requireClose(xya, rec);
  }
}
