// code by jph
package ch.alpine.ascony.dis;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import ch.alpine.ascony.msh.D2Raster;
import ch.alpine.sophus.api.Manifold;
import ch.alpine.sophus.api.MetricManifold;

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
  R3(R3Display.INSTANCE),
  S1(S1Display.INSTANCE),
  S2(S2Display.INSTANCE),
  Rp2(Rp2Display.INSTANCE),
  H1(H1Display.INSTANCE),
  H2(H2Display.INSTANCE),
  // Sl2(Sl2Display.INSTANCE),
  So3(So3Display.INSTANCE),
  He1(He1Display.INSTANCE),
  Td1(Td1Display.INSTANCE);

  private final ManifoldDisplay manifoldDisplay;

  ManifoldDisplays(ManifoldDisplay manifoldDisplay) {
    this.manifoldDisplay = manifoldDisplay;
  }

  public ManifoldDisplay manifoldDisplay() {
    return manifoldDisplay;
  }

  public static List<ManifoldDisplays> filter(Predicate<ManifoldDisplay> predicate) {
    return Arrays.stream(values()).filter(md -> predicate.test(md.manifoldDisplay())).toList();
  }

  /** requires biinvariant() */
  public static List<ManifoldDisplays> metricManifolds() {
    return filter(md -> md.geodesicSpace() instanceof MetricManifold);
  }

  /** manifolds */
  public static List<ManifoldDisplays> manifolds() {
    return filter(md -> Objects.nonNull(md.manifold()));
  }

  public static List<ManifoldDisplays> manifolds2DimOrMore() {
    return Arrays.stream(values()) //
        .filter(md -> md.manifoldDisplay().geodesicSpace() instanceof Manifold) //
        .filter(md -> 2 <= md.manifoldDisplay().dimensions()) //
        .toList();
  }

  /** homogeneous spaces (have biinvariant mean) */
  public static List<ManifoldDisplays> homogeneousSpaces() {
    return filter(md -> Objects.nonNull(md.homogeneousSpace()));
  }

  // ---
  /** implement {@link D2Raster} */
  public static List<ManifoldDisplays> d2Rasters() {
    return filter(md -> Objects.nonNull(md.d2Raster()));
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
  public static final Set<ManifoldDisplays> ALL = EnumSet.allOf(ManifoldDisplays.class);
  public static final Set<ManifoldDisplays> S2_RP2 = EnumSet.of(S2, Rp2);
  public static final Set<ManifoldDisplays> S2_TYPES = EnumSet.of(S2, Rp2, So3);
  public static final Set<ManifoldDisplays> R2_ONLY = EnumSet.of(R2);
  public static final Set<ManifoldDisplays> R2_S2 = EnumSet.of(R2, S2);
  public static final Set<ManifoldDisplays> R2_H2_S2_SE2C = EnumSet.of(Se2C, R2, H2, S2);
  public static final Set<ManifoldDisplays> DEFORM_2D = EnumSet.of(Se2C, Se2, R2, H2, S2);
  public static final Set<ManifoldDisplays> SE2C_R2 = EnumSet.of(Se2C, R2);
  // ---
  /** for dubins */
  public static final Set<ManifoldDisplays> SE2_ONLY = EnumSet.of(Se2);
  public static final Set<ManifoldDisplays> SE2_R2_S2 = EnumSet.of(Se2, R2, S2);
  public static final Set<ManifoldDisplays> SE2C_R2_S2 = EnumSet.of(Se2C, R2, S2);
  public static final Set<ManifoldDisplays> SE2C_R2_H2 = EnumSet.of(Se2C, R2, H2);
  public static final Set<ManifoldDisplays> SE2C_R3 = EnumSet.of(Se2C, R3);
  // ---
  /** for gokart data */
  public static final Set<ManifoldDisplays> SE2_R2 = EnumSet.of(Se2, R2);
  // ---
  public static final Set<ManifoldDisplays> SE2C_SE2_R2 = EnumSet.of(Se2C, Se2, R2);
  // ---
  public static final Set<ManifoldDisplays> SE2C_SE2 = EnumSet.of(Se2C, Se2);
  // ---
  public static final Set<ManifoldDisplays> S2_ONLY = EnumSet.of(S2);
  public static final Set<ManifoldDisplays> CLA_ONLY = EnumSet.of(ClA);
  public static final Set<ManifoldDisplays> CLC_ONLY = EnumSet.of(ClC);
}
