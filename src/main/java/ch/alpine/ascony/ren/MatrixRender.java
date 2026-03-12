// code by jph
package ch.alpine.ascony.ren;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.function.Function;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Flatten;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradient;
import ch.alpine.tensor.img.ColorDataIndexed;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.ArcTan;

public record MatrixRender( //
    Graphics2D graphics, ColorDataIndexed colorDataIndexed, Function<Scalar, Color> function, ScalarUnaryOperator round) {
  public static MatrixRender absoluteOne( //
      Graphics2D graphics, ColorDataIndexed colorDataIndexed, ColorDataGradient colorDataGradient, ScalarUnaryOperator round) {
    return new MatrixRender( //
        graphics, colorDataIndexed, //
        value -> ColorFormat.toColor(colorDataGradient.apply(Clips.absoluteOne().rescale(value))), //
        round);
  }

  public static MatrixRender arcTan( //
      Graphics2D graphics, ColorDataIndexed colorDataIndexed, ColorDataGradient colorDataGradient, ScalarUnaryOperator round) {
    Clip clip = Clips.absolute(Pi.HALF);
    return new MatrixRender(graphics, colorDataIndexed, //
        value -> ColorFormat.toColor(colorDataGradient.apply(clip.rescale(ArcTan.FUNCTION.apply(value)))), //
        round);
  }

  public static MatrixRender of( //
      Graphics2D graphics, ColorDataIndexed colorDataIndexed, Color color, ScalarUnaryOperator round) {
    return new MatrixRender(graphics, colorDataIndexed, _ -> color, round);
  }

  public void renderMatrix(Tensor matrix, int pix, int piy) {
    Tensor rounded = matrix.maps(round);
    FontMetrics fontMetrics = graphics.getFontMetrics();
    int fheight = fontMetrics.getAscent();
    int max = Flatten.stream(rounded, -1) //
        .map(Object::toString) //
        .mapToInt(fontMetrics::stringWidth) //
        .max() //
        .orElseThrow();
    int width = max + 3;
    for (int row = 0; row < rounded.length(); ++row) {
      Tensor vector = matrix.get(row);
      for (int col = 0; col < vector.length(); ++col) {
        graphics.setColor(function.apply(vector.Get(col)));
        int tpx = pix + width * col;
        int tpy = piy + fheight * row;
        graphics.fillRect(tpx, tpy, width, fheight);
        String string = rounded.Get(row, col).toString();
        int sw = fontMetrics.stringWidth(string);
        String show = string;
        graphics.setColor(new Color(255, 255, 255, 128));
        graphics.drawString(show, tpx + width - sw - 1, tpy + fheight - 2);
        graphics.setColor(colorDataIndexed.getColor(row));
        graphics.drawString(show, tpx + width - sw, tpy + fheight - 1);
      }
    }
  }
}
