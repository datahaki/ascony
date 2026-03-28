// code by jph
package ch.alpine.ascony.win;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;

import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.img.ColorDataGradients;

class SimpleMdDemo extends ManifoldDisplayDemo {
  @ReflectionMarker
  static class Inner {
    public Integer val = 3;
    public Color color = Color.BLACK;
    public ColorDataGradients cdg = ColorDataGradients.ALPINE;
  }

  public SimpleMdDemo() {
    super(new Inner());
  }

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
  }

  @Override
  protected Collection<ManifoldDisplays> permitted_manifoldDisplays() {
    return ManifoldDisplays.ALL;
  }
}
