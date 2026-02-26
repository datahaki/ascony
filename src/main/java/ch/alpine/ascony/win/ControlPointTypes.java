// code by jph
package ch.alpine.ascony.win;

/** SORTED BY INVERTED BIT REPRESENTATION: true beats false */
public enum ControlPointTypes {
  ;
  public static final ControlPointType CURVYCURV = new ControlPointType(true, true, true);
  public static final ControlPointType SCATTERED = new ControlPointType(true, true, false);
  public static final ControlPointType SHOW_ONLY = new ControlPointType(true, false, false);
  /** only manage position of control points but no removal and draw action */
  public static final ControlPointType HEAD_TAIL = new ControlPointType(false, true, false);
  public static final ControlPointType DELEGATED = new ControlPointType(false, false, false);
}
