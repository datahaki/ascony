// code by jph
package ch.alpine.ascony.ren;

import java.awt.Graphics2D;
import java.awt.Shape;

import ch.alpine.ascony.dat.GlyphMesh;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.BezierCurve;
import ch.alpine.sophis.srf.SurfaceMesh;
import ch.alpine.sophus.bm.LinearBiinvariantMean;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarTensorFunction;

public record BezierGlyphRender(Shape shape, Tensor domain) implements RenderInterface {
  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    SurfaceMesh surfaceMesh = GlyphMesh.of(shape);
    for (int[] face : surfaceMesh.faces()) {
      if (face.length == 2)
        graphics.draw(geometricLayer.toLine2D( //
            surfaceMesh.vrt.get(face[0]), //
            surfaceMesh.vrt.get(face[1])));
      else {
        ScalarTensorFunction stf = //
            BezierCurve.of(LinearBiinvariantMean.INSTANCE, surfaceMesh.polygon_face(face));
        graphics.draw(geometricLayer.toPath2D(domain.maps(stf)));
      }
    }
  }
}
