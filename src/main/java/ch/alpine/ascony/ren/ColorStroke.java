// code by jph
package ch.alpine.ascony.ren;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.io.Serializable;

import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;

public record ColorStroke(Color color, Stroke stroke) implements Serializable {
  private static final ColorDataIndexed COLOR_DATA_INDEXED = ColorDataLists._097.strict();
  private static final Stroke STROKE_GEODESIC = //
      new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3 }, 0);
  private static final Stroke STROKE_BOLD = //
      new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3 }, 0);
  // ---
  public static final ColorStroke CURVE = new ColorStroke(Color.BLUE, new BasicStroke(1.5f));
  public static final ColorStroke SECONDARY_CURVE = new ColorStroke(Color.RED, new BasicStroke());
  public static final ColorStroke CURVATURE_COMB = new ColorStroke(new Color(0, 192, 0, 128), new BasicStroke());
  public static final ColorStroke TRACE = new ColorStroke(Color.LIGHT_GRAY, STROKE_GEODESIC);
  public static final ColorStroke CONVEX_HULL = new ColorStroke(COLOR_DATA_INDEXED.getColor(1), STROKE_GEODESIC);
  public static final ColorStroke SPHERE_1 = new ColorStroke(new Color(192, 192, 192, 128), new BasicStroke());
  public static final ColorStroke HI_CONTRAST = new ColorStroke(new Color(255, 0, 255), STROKE_BOLD);
}
