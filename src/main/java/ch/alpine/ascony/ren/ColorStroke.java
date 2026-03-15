// code by jph
package ch.alpine.ascony.ren;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.io.Serializable;

public record ColorStroke(Color color, Stroke stroke) implements Serializable {
  public static final ColorStroke CURVE = new ColorStroke(Color.BLUE, new BasicStroke(1.25f));
  public static final ColorStroke SECONDARY_CURVE = new ColorStroke(Color.RED, new BasicStroke());
  public static final ColorStroke CURVATURE_COMB = new ColorStroke(new Color(0, 192, 0, 128), new BasicStroke());
  private static final Stroke STROKE_GEODESIC = //
      new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3 }, 0);
  public static final ColorStroke AREA_SELECTION = new ColorStroke(Color.LIGHT_GRAY, STROKE_GEODESIC);
}
