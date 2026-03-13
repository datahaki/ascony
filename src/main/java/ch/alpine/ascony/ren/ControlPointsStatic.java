// code by jph
package ch.alpine.ascony.ren;

import java.awt.Color;

import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;

public enum ControlPointsStatic {
  ;
  private static final ColorPair GRAY = //
      new ColorPair(new Color(160, 160, 160, 128), Color.BLACK);

  /** @param manifoldDisplay
   * @param points */
  public static RenderInterface gray(ManifoldDisplay manifoldDisplay, Tensor points) {
    return manifoldDisplay.showPoints(GRAY, RealScalar.ONE, points);
  }
}
