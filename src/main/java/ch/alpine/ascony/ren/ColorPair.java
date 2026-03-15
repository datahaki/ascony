// code by jph
package ch.alpine.ascony.ren;

import java.awt.Color;
import java.io.Serializable;

import ch.alpine.bridge.awt.AwtUtil;

public record ColorPair(Color fill, Color draw) implements Serializable {
  /** scattered or sequential control points intended for manipulation by user */
  public static final ColorPair CONTROL_POINTS = new ColorPair(new Color(255, 128, 128, 64), new Color(255, 128, 128, 255));
  /** to highlight a single points for instance at a configurable parameter value */
  public static final ColorPair MARKER = new ColorPair(new Color(32, 32, 32, 192), new Color(32, 32, 32, 255));
  /** greenish lever origin == zero reference for computation of coordinates, weightings, etc. */
  public static final ColorPair ORIGIN = new ColorPair(new Color(64, 128, 64, 128), new Color(64, 128, 64, 255));
  /** purple spacial median */
  public static final ColorPair SPACIAL_MEDIAN = new ColorPair(new Color(192, 0, 255, 128), new Color(192, 0, 255, 192));
  /** mean approximation */
  public static final ColorPair APPROXIMATION = new ColorPair(new Color(128, 64, 64, 128), new Color(128, 64, 64, 255));
  /** intermediate control points */
  public static final ColorPair INTERMEDIATE = new ColorPair(new Color(64, 64, 64, 64), new Color(64, 64, 64, 128));
  /** pyramid scheme intermediate control points */
  public static final ColorPair SPLIT_PROCESS = new ColorPair(new Color(0, 0, 255, 128), new Color(0, 0, 192, 192));
  /** for separation into 2 partitions */
  public static final ColorPair GROUP_NEAR = new ColorPair(new Color(64, 64, 192, 64), new Color(64, 64, 192, 255));
  public static final ColorPair GROUP_AFAR = new ColorPair(new Color(192, 64, 64, 64), new Color(192, 64, 64, 255));
  /** insert */
  public static final ColorPair INSERTION = new ColorPair(new Color(0, 255, 0, 192), new Color(0, 192, 0, 255));
  public static final ColorPair MOVEOFFER = new ColorPair(new Color(255, 200, 0, 192), new Color(255, 200, 0, 255));
  /** reference control points */
  public static final ColorPair REFERENCE = new ColorPair(new Color(64, 64, 64, 128), new Color(64, 64, 64, 192));
  /** raster value 230 get's mapped to color {244, 244, 244, 255}
   * when using getRGB because of the color model attached to the
   * image type grayscale */
  public static final ColorPair REGION = new ColorPair(new Color(230, 230, 230, 64), new Color(192, 192, 192, 128));
  // ---
  public static final ColorPair DEC = new ColorPair(new Color(255, 128, 128, 64), new Color(255, 128, 128, 255));
  public static final ColorPair DED = new ColorPair(new Color(160, 160, 160, 160), Color.BLACK);
  public static final ColorPair ASD = new ColorPair(new Color(160, 160, 160, 192), Color.BLACK);
  public static final ColorPair ASC = new ColorPair(new Color(255, 128, 128, 32), new Color(255, 128, 128, 128));
  public static final ColorPair ASN = new ColorPair(new Color(255, 128, 128, 64), new Color(255, 128, 128, 255));

  public ColorPair solid() {
    return new ColorPair( //
        AwtUtil.withAlpha(fill, 255), //
        AwtUtil.withAlpha(draw, 255));
  }
}
