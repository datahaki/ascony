// code by jph
package ch.alpine.ascony.ren;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ch.alpine.bridge.fig.Meshgrid;
import ch.alpine.bridge.gfx.AffineTransforms;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.PvmBuilder;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.d2.ex.Box2D;
import ch.alpine.sophus.lie.se2.Se2Matrix;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.red.Times;
import ch.alpine.tensor.sca.Clip;

/** coordinate bounding box is area of image in model space */
public class ImageRender implements RenderInterface {
  /** @param coordinateBoundingBox
   * @param w
   * @param h
   * @return */
  public static Tensor pixel2model(Meshgrid meshgrid) {
    Clip clipX = meshgrid.cbb().clip(0);
    Clip clipY = meshgrid.cbb().clip(1);
    Tensor range = Tensors.of(clipX.width(), clipY.width());
    Tensor scale = Times.of(Tensors.vector(meshgrid.width(), meshgrid.height()), range.maps(Scalar::reciprocal));
    Tensor mat = Se2Matrix.translation(Tensors.of(clipX.min(), clipY.min()));
    Tensor res = mat.dot(Times.of(Append.of(scale.maps(Scalar::reciprocal), RealScalar.ONE), Se2Matrix.flipY(meshgrid.height())));
    // TODO use PVM
    Tensor dig = PvmBuilder.rhs().setOffset(0, meshgrid.height()).digest();
    // IO.println("====");
    // IO.println(Pretty.of(res.maps(Round._3)));
    // IO.println(Pretty.of(dig.maps(Round._3)));
    return res;
  }

  public static boolean DRAW_BOX = false;
  private final BufferedImage bufferedImage;
  private final CoordinateBoundingBox cbb;
  private final Tensor pixel2model;

  public ImageRender(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    this.bufferedImage = bufferedImage;
    this.cbb = cbb;
    Meshgrid meshgrid = new Meshgrid(cbb, bufferedImage.getWidth(), bufferedImage.getHeight());
    pixel2model = pixel2model(meshgrid);
  }

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    if (DRAW_BOX) {
      graphics.setColor(Color.LIGHT_GRAY);
      graphics.draw(geometricLayer.toPath2D(Box2D.polygon(cbb), true));
    }
    geometricLayer.pushMatrix(pixel2model);
    graphics.drawImage(bufferedImage, AffineTransforms.of(geometricLayer.getMatrix()), null);
    geometricLayer.popMatrix();
  }
}
