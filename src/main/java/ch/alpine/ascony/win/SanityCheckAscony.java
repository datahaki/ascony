// code by jph
package ch.alpine.ascony.win;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.bridge.pro.SanityCheckRunProvider;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.tensor.Throw;

/** DO NOT USE IN THE APPLICATION LAYER */
public class SanityCheckAscony extends SanityCheckRunProvider {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 800;

  @Override
  protected void check(WindowProvider windowProvider) {
    if (windowProvider instanceof ManifoldDisplayDemo manifoldDisplayDemo)
      check(manifoldDisplayDemo);
    else
      super.check(windowProvider);
  }

  private void check(ManifoldDisplayDemo manifoldDisplayDemo) {
    IO.println("CORRECT ENTER");
    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    manifoldDisplayDemo.timerFrame.jFrame.setSize(WIDTH, HEIGHT);
    boolean status = true;
    try {
      for (ManifoldDisplays manifoldDisplays : manifoldDisplayDemo.permitted_manifoldDisplays()) {
        manifoldDisplayDemo.setManifoldDisplay(manifoldDisplays);
        manifoldDisplayDemo.geometricComponent().jComponent.printAll(graphics);
      }
    } catch (Exception e) {
      status = false;
    }
    graphics.dispose();
    Throw.unless(status);
  }
}
