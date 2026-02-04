// code by jph
package ch.alpine.ascony.ref;

import java.util.List;

import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class SpaceParam {
  private final List<ManifoldDisplays> list;
  /** currently selected */
  @FieldSelectionCallback("getList")
  public ManifoldDisplays manifoldDisplays;

  public SpaceParam(List<ManifoldDisplays> list) {
    this.list = list;
    manifoldDisplays = list.getFirst();
  }

  public List<ManifoldDisplays> getList() {
    return list;
  }
}
