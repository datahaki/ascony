// code by jph
package ch.alpine.ascony.dis;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.alpine.ascony.arp.D2Raster;
import ch.alpine.sophus.api.Manifold;
import ch.alpine.sophus.api.MetricManifold;
import ch.alpine.sophus.hs.HomogeneousSpace;

public enum ManifoldDisplays {
  ClC(Se2CoveringClothoidDisplay.INSTANCE),
  ClA(Se2ClothoidDisplay.ANALYTIC),
  ClL(Se2ClothoidDisplay.LEGENDRE),
  R2S1A(R2S1ADisplay.INSTANCE),
  R2S1B(R2S1BDisplay.INSTANCE),
  Se2C(Se2CoveringDisplay.INSTANCE),
  Se2(Se2Display.INSTANCE),
  Spd2(Spd2Display.INSTANCE),
  R1(R1Display.INSTANCE),
  R2(R2Display.INSTANCE),
  S1(S1Display.INSTANCE),
  S2(S2Display.INSTANCE),
  Rp2(Rp2Display.INSTANCE),
  H1(H1Display.INSTANCE),
  H2(H2Display.INSTANCE),
  Sl2(Sl2Display.INSTANCE),
  So3(So3Display.INSTANCE),
  He1(He1Display.INSTANCE),
  Td1(Td1Display.INSTANCE);

  public boolean isXY_Angle() {
    return ordinal() <= Se2.ordinal();
  }

  private final ManifoldDisplay manifoldDisplay;

  ManifoldDisplays(ManifoldDisplay manifoldDisplay) {
    this.manifoldDisplay = manifoldDisplay;
  }

  public ManifoldDisplay manifoldDisplay() {
    return manifoldDisplay;
  }

  public static final List<ManifoldDisplays> ALL = List.of(values());

  /** requires biinvariant() */
  public static List<ManifoldDisplays> metricManifolds() {
    return Arrays.stream(values()) //
        .filter(md -> md.manifoldDisplay().geodesicSpace() instanceof MetricManifold) //
        .toList();
  }

  /** manifolds */
  public static List<ManifoldDisplays> manifolds() {
    return Arrays.stream(values()) //
        .filter(md -> md.manifoldDisplay().geodesicSpace() instanceof Manifold) //
        .toList();
  }

  public static List<ManifoldDisplays> manifolds2DimOrMore() {
    return Arrays.stream(values()) //
        .filter(md -> md.manifoldDisplay().geodesicSpace() instanceof Manifold) //
        .filter(md -> 2 <= md.manifoldDisplay().dimensions()) //
        .toList();
  }

  /** homogeneous spaces (have biinvariant mean) */
  public static List<ManifoldDisplays> homogeneousSpaces() {
    return Arrays.stream(values()) //
        .filter(md -> md.manifoldDisplay().geodesicSpace() instanceof HomogeneousSpace) //
        .toList();
  }

  // ---
  /** implement {@link D2Raster} */
  public static List<ManifoldDisplays> d2Rasters() {
    return Arrays.stream(values()) //
        .filter(md -> Objects.nonNull(md.manifoldDisplay().d2Raster())) //
        .toList();
  }

  /** implement {@link D2Raster} */
  public static List<ManifoldDisplays> manifoldD2Rasters() {
    return Arrays.stream(values()) //
        .filter(md -> Objects.nonNull(md.manifoldDisplay().d2Raster())) //
        .filter(md -> md.manifoldDisplay().geodesicSpace() instanceof Manifold) //
        .toList();
  }

  /** implement {@link D2Raster} */
  public static List<ManifoldDisplays> metricD2Rasters() {
    return Arrays.stream(values()) //
        .filter(md -> Objects.nonNull(md.manifoldDisplay().d2Raster())) //
        .filter(md -> md.manifoldDisplay().geodesicSpace() instanceof MetricManifold) //
        .toList();
  }

  public static List<ManifoldDisplays> lineDistances() {
    return Arrays.stream(values()) //
        .filter(md -> md.manifoldDisplay().dimensions() == 2) //
        .filter(md -> Objects.nonNull(md.manifoldDisplay().lineDistance())) //
        .toList();
  }

  // ---
  public static final List<ManifoldDisplays> R2_ONLY = List.of(R2);
  public static final List<ManifoldDisplays> R2_S2 = List.of(R2, S2);
  public static final List<ManifoldDisplays> R2_S2_H2 = List.of(R2, S2, H2);
  public static final List<ManifoldDisplays> R2_H2_S2_SE2C = List.of(R2, H2, S2, Se2C);
  public static final List<ManifoldDisplays> SE2C_R2 = List.of(Se2C, R2);
  // ---
  public static final List<ManifoldDisplays> SE2_ONLY = List.of(Se2);
  public static final List<ManifoldDisplays> SE2C_ONLY = List.of(Se2C);
  // ---
  public static final List<ManifoldDisplays> SE2_R2 = List.of( //
      Se2, //
      R2);
  // ---
  public static final List<ManifoldDisplays> SE2C_SE2_R2 = List.of( //
      Se2C, //
      Se2, //
      R2);
  // ---
  public static final List<ManifoldDisplays> SE2C_SE2 = List.of( //
      Se2C, //
      Se2);
  // ---
  public static final List<ManifoldDisplays> S2_ONLY = List.of(S2);
  public static final List<ManifoldDisplays> H2_ONLY = List.of(H2);
  public static final List<ManifoldDisplays> CL_ONLY = List.of(ClA);
  public static final List<ManifoldDisplays> CLC_ONLY = List.of(ClC);
}
