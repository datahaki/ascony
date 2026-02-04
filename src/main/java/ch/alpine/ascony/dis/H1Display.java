// code by jph
package ch.alpine.ascony.dis;

import ch.alpine.ascony.ren.RenderInterface;

public class H1Display extends HnDisplay {
  public static final ManifoldDisplay INSTANCE = new H1Display();

  // ---
  private H1Display() {
    super(1);
  }

  @Override
  public RenderInterface background() {
    return H1Background.INSTANCE;
  }
}
