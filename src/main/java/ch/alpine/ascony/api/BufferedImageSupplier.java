// code by jph
package ch.alpine.ascony.api;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface BufferedImageSupplier {
  /** @return bufferedImage may be null */
  BufferedImage bufferedImage();
}
