// code by jph
package ch.alpine.ascony.ref;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class AsconaParam {
  public final boolean addRemoveControlPoints;
  public boolean drawControlPoints = true;

  public AsconaParam(boolean addRemoveControlPoints) {
    this.addRemoveControlPoints = addRemoveControlPoints;
  }
}
