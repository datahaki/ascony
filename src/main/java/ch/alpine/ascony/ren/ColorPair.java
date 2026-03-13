// code by jph
package ch.alpine.ascony.ren;

import java.awt.Color;

import ch.alpine.bridge.awt.AwtUtil;

public record ColorPair(Color fill, Color draw) {
  /** purple spacial median */
  public static final ColorPair SPACIAL_MEDIAN = new ColorPair(new Color(192, 0, 255, 128), new Color(192, 0, 255, 192));
  /** intermediate control points */
  public static final ColorPair INTERMEDIATE = new ColorPair(new Color(64, 64, 64, 64), new Color(64, 64, 64, 128));
  /** to highlight a single points for instance at a configurable parameter value */
  public static final ColorPair MARKER = new ColorPair(new Color(32, 32, 32, 192), new Color(32, 32, 32, 255));
  /** extrapolation */
  public static final ColorPair EXTRAPOLATION = new ColorPair(new Color(32, 192, 32, 64), new Color(32, 192, 32, 128));
  public static final ColorPair APPROXIMATION = new ColorPair(new Color(128, 64, 64, 128), new Color(128, 64, 64, 255));
  public static final ColorPair SUPPORT = new ColorPair(new Color(0, 0, 255, 128), new Color(0, 0, 192, 192));
  // ---
  public static final ColorPair DEC = new ColorPair(new Color(255, 128, 128, 64), new Color(255, 128, 128, 255));
  public static final ColorPair DED = new ColorPair(new Color(160, 160, 160, 160), Color.BLACK);
  public static final ColorPair ABE = new ColorPair(new Color(128, 128, 128, 64), new Color(128, 128, 128, 255));
  public static final ColorPair PCD = new ColorPair(Color.LIGHT_GRAY, Color.GRAY);
  public static final ColorPair R2P = new ColorPair(new Color(0, 128, 128, 64), new Color(0, 128, 128, 255));
  public static final ColorPair RMF = new ColorPair(new Color(128, 128, 255, 64), new Color(128, 128, 255, 255));
  public static final ColorPair RMD = new ColorPair(new Color(128, 128, 128, 64), new Color(128, 128, 128, 255));
  public static final ColorPair RIG = new ColorPair(new Color(64, 255, 64, 64), new Color(64, 255, 64, 255));
  public static final ColorPair EXP = new ColorPair(new Color(0, 128, 128, 64), new Color(0, 128, 128, 96));
  public static final ColorPair S1I = new ColorPair(new Color(64, 128, 64, 64), new Color(64, 128, 64, 255));
  public static final ColorPair SPA = new ColorPair(new Color(128, 128, 128, 64), new Color(128, 128, 128, 128));
  public static final ColorPair ADE = new ColorPair(new Color(64, 128, 64, 64), new Color(64, 128, 64, 255));
  public static final ColorPair R2B = new ColorPair(new Color(255, 128, 128, 160), new Color(255, 128, 128, 192));
  public static final ColorPair HOL = new ColorPair(new Color(255, 200, 0, 192), Color.GRAY);
  public static final ColorPair REL = new ColorPair(new Color(0, 255, 0, 192), Color.GRAY);
  public static final ColorPair ASD = new ColorPair(new Color(160, 160, 160, 192), Color.BLACK);
  public static final ColorPair ASC = new ColorPair(new Color(255, 128, 128, 32), new Color(255, 128, 128, 128));
  public static final ColorPair ASN = new ColorPair(new Color(255, 128, 128, 64), new Color(255, 128, 128, 255));
  public static final ColorPair KNE = new ColorPair(new Color(64, 192, 64, 64), new Color(64, 192, 64, 255));
  public static final ColorPair KFA = new ColorPair(new Color(192, 64, 64, 64), new Color(192, 64, 64, 255));
  public static final ColorPair LEV = new ColorPair(new Color(64, 128, 64, 128), new Color(64, 128, 64, 255));
  /** raster value 230 get's mapped to color {244, 244, 244, 255}
   * when using getRGB because of the color model attached to the
   * image type grayscale */
  public static final ColorPair REG = new ColorPair(new Color(230, 230, 230, 64), new Color(192, 192, 192, 128));
  public static final ColorPair FIT = new ColorPair(new Color(128, 128, 255, 64), new Color(128, 128, 255, 255));

  public ColorPair solid() {
    return new ColorPair( //
        AwtUtil.withAlpha(fill, 255), //
        AwtUtil.withAlpha(draw, 255));
  }
}
