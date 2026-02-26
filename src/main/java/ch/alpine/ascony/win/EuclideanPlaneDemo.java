// code by jph
package ch.alpine.ascony.win;

import java.util.List;

import ch.alpine.ascony.dis.ManifoldDisplays;

public abstract class EuclideanPlaneDemo extends ControlPointsDemo {
  @SafeVarargs
  protected EuclideanPlaneDemo(Object... objects) {
    super(objects);
  }

  @Override
  protected final List<ManifoldDisplays> permitted_manifoldDisplays() {
    return ManifoldDisplays.R2_ONLY;
  }
}
