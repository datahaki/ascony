// code by jph
package ch.alpine.ascony.ren;

import java.awt.Graphics2D;

import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;

public enum EmptyRender implements RenderInterface {
  INSTANCE;

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    // ---
  }
}
