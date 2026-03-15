// code by jph
package ch.alpine.ascony.ren;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.io.Serializable;

import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorDataLists;

public record ColorStrokeIndexed(ColorDataIndexed cdi, Stroke stroke) implements Serializable {
  public static final ColorStrokeIndexed _097 = //
      new ColorStrokeIndexed(ColorDataLists._097.cyclic(), new BasicStroke(1.5f));

  public ColorStroke getColorStroke(int index) {
    return new ColorStroke(cdi.getColor(index), stroke);
  }
}
