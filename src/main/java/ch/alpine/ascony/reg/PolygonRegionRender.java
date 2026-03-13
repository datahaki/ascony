// code by jph
package ch.alpine.ascony.reg;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import ch.alpine.ascony.ren.ColorPair;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.d2.alg.PolygonRegion;
import ch.alpine.tensor.Tensor;

public record PolygonRegionRender(Tensor polygon) implements RenderInterface {
  public static PolygonRegionRender of(PolygonRegion polygonRegion) {
    return new PolygonRegionRender(polygonRegion.polygon());
  }

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    Path2D path2D = geometricLayer.toPath2D(polygon);
    path2D.closePath();
    graphics.setColor(ColorPair.REG.fill());
    graphics.fill(path2D);
    graphics.setColor(ColorPair.REG.draw());
    graphics.draw(path2D);
  }
}
