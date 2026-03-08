// code by jph
package ch.alpine.ascony.ren;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Optional;
import java.util.function.Supplier;

import ch.alpine.bridge.fig.GridDrawer;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public record GridRender(Supplier<Dimension> supplier) implements RenderInterface {
  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    if (geometricLayer.isAxisAligned()) {
      Dimension dimension = supplier.get();
      Optional<Rectangle> optional = Show.optionalDefaultInsets(dimension, graphics.getFont().getSize());
      if (optional.isPresent()) {
        Rectangle rectangle = optional.orElseThrow();
        CoordinateBoundingBox cbb = geometricLayer.fromRectangle(rectangle).orElseThrow();
        ShowableConfig showableConfig = new ShowableConfig(rectangle, cbb);
        new GridDrawer().render(showableConfig, graphics);
      }
    } else {
      graphics.setColor(Color.RED);
      graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
      graphics.drawString("no grid because axes not aligned", 0, 30);
    }
  }
}
