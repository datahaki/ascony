// code by jph
package ch.alpine.ascony.ref;

import java.util.List;

import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.sophis.dv.Biinvariants;

@ReflectionMarker
public class BiinvariantsParam {
  public static BiinvariantsParam fast() {
    return new BiinvariantsParam(Biinvariants.FAST);
  }

  public static BiinvariantsParam okay() {
    return new BiinvariantsParam(Biinvariants.OKAY);
  }

  // ---
  private final List<Biinvariants> list;

  public BiinvariantsParam(List<Biinvariants> list) {
    this.list = list;
  }

  @FieldSelectionCallback("biinvariants")
  public Biinvariants biinvariants = Biinvariants.METRIC;

  @ReflectionMarker
  public List<Biinvariants> biinvariants() {
    return list;
  }
}
