// code by jph
package ch.alpine.ascony.sym;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.win.WindowFunctions;

class SymLinkImagesTest {
  @ParameterizedTest
  @EnumSource
  void testSmoothingKernel(WindowFunctions smoothingKernel) {
    for (int radius = 0; radius < 5; ++radius)
      SymLinkImages.ofGC(smoothingKernel.get(), radius);
  }

  @Test
  void testDeBoorRational() {
    Scalar parameter = Rational.of(9, 4);
    SymLinkImage symLinkImage = SymLinkImages.symLinkImageGBSF(4, 20, parameter);
    BufferedImage bufferedImage = symLinkImage.bufferedImage();
    assertTrue(300 < bufferedImage.getWidth());
    assertTrue(200 < bufferedImage.getHeight());
  }

  @Test
  void testDeBoorDecimal() {
    Scalar parameter = RealScalar.of(5.1);
    SymLinkImage symLinkImage = SymLinkImages.symLinkImageGBSF(5, 20, parameter);
    BufferedImage bufferedImage = symLinkImage.bufferedImage();
    assertTrue(300 < bufferedImage.getWidth());
    assertTrue(200 < bufferedImage.getHeight());
  }
}
