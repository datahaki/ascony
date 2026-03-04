// code by jph
package ch.alpine.ascony.ref;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Optional;

import ch.alpine.ascony.api.BufferedImageSupplier;
import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Tensor;

/** class is used in other projects outside of owl */
@ReflectionMarker
public class BaseGraphParam {
  public Boolean graph = true;

  // TODO this is not really used
  public final RenderInterface spawn(ManifoldDisplay manifoldDisplay, Tensor refined, Rectangle rectangle) {
    return new RenderInterface() {
      @Override
      public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
        if (graph && //
            this instanceof BufferedImageSupplier bufferedImageSupplier) {
          Optional<BufferedImage> optional = Optional.ofNullable(bufferedImageSupplier.bufferedImage());
          if (optional.isPresent())
            graphics.drawImage(optional.orElseThrow(), 0, 0, null);
        }
      }
    };
  }
}
