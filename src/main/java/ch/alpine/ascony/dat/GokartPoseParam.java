// code by jph
package ch.alpine.ascony.dat;

import java.util.List;

import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.ascony.ref.SpaceParam;
import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Tensor;

@ReflectionMarker
public class GokartPoseParam extends SpaceParam {
  private final GokartPoseData gokartPoseData;

  public GokartPoseParam( //
      GokartPoseData gokartPoseData, //
      List<ManifoldDisplays> list) {
    super(list);
    this.gokartPoseData = gokartPoseData;
    manifoldDisplays = ManifoldDisplays.Se2;
    string = gokartPoseData.list().getFirst();
  }

  public GokartPoseData gpd() {
    return gokartPoseData;
  }

  @FieldSelectionCallback("gokartPoseData")
  public String string;
  @FieldSelectionArray({ "100", "250", "500", "1000", "2000", "5000" })
  public Integer limit = 1000;

  public final List<String> gokartPoseData() {
    return gokartPoseData.list();
  }

  public final Tensor getPoses() {
    return gokartPoseData.getPose(string, limit);
  }
}
