// code by jph
package ch.alpine.ascony.win;

import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.BufferedImage;

import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.bridge.gfx.GeometricComponent;
import ch.alpine.bridge.io.GitHubCI;
import ch.alpine.bridge.pro.SanityCheckRunProvider;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.ref.util.RandomFieldsAssignment;
import ch.alpine.tensor.Throw;

/** DO NOT USE IN THE APPLICATION LAYER */
public class SanityCheckAscony extends SanityCheckRunProvider {
  private static final int SIZE = 800;
  private final int limit;

  public SanityCheckAscony(int limit) {
    this.limit = limit;
  }

  @Override
  protected void check(WindowProvider windowProvider) {
    if (windowProvider instanceof AbstractDemo abstractDemo) {
      subcheck(windowProvider);
      RandomFieldsAssignment.of(abstractDemo.objectsParam).randomize(limit) //
          .forEach(obj -> {
            GitHubCI.println(" PARAM=" + ObjectProperties.list(obj));
            subcheck(windowProvider);
          });
    } else
      subcheck(windowProvider);
  }

  private void subcheck(WindowProvider windowProvider) {
    if (windowProvider instanceof ManifoldDisplayDemo manifoldDisplayDemo)
      checkDemo(manifoldDisplayDemo);
    else
      super.check(windowProvider);
  }

  private void checkDemo(ManifoldDisplayDemo manifoldDisplayDemo) {
    Window window = manifoldDisplayDemo.getWindow();
    window.setSize(SIZE, SIZE);
    GeometricComponent geometricComponent = manifoldDisplayDemo.geometricComponent();
    geometricComponent.setSize(SIZE, SIZE);
    BufferedImage bufferedImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    manifoldDisplayDemo.getWindow().setSize(SIZE, SIZE);
    boolean status = true;
    for (ManifoldDisplays manifoldDisplays : manifoldDisplayDemo.permitted_manifoldDisplays())
      try {
        manifoldDisplayDemo.setManifoldDisplay(manifoldDisplays);
        geometricComponent.printAll(graphics);
      } catch (Exception exception) {
        GitHubCI.println("Exception in: " + manifoldDisplayDemo.getClass().getName() + " MD=" + manifoldDisplays);
        exception.printStackTrace();
        status = false;
      }
    graphics.dispose();
    Throw.unless(status);
  }
}
