// code by jph
package ch.alpine.ascony.ren;

import ch.alpine.tensor.img.ColorDataIndexed;

public class ColorPairIndexed {
  private final ColorDataIndexed cdiF;
  private final ColorDataIndexed cdiD;

  public ColorPairIndexed(ColorDataIndexed cdi, int aF, int aD) {
    this.cdiF = cdi.deriveWithAlpha(aF);
    this.cdiD = cdi.deriveWithAlpha(aD);
  }

  public ColorPair getColorPair(int index) {
    return new ColorPair(cdiF.getColor(index), cdiD.getColor(index));
  }
}
