// code by jph
package ch.alpine.ascony.ren;

import java.awt.Graphics2D;
import java.awt.Shape;

import ch.alpine.ascony.dat.GlyphMesh;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.BezierCurve;
import ch.alpine.sophis.crv.clt.Clothoid;
import ch.alpine.sophis.crv.clt.ClothoidBuilders;
import ch.alpine.sophis.crv.d2.PolygonArea;
import ch.alpine.sophis.hull.d2.ConvexHull2D;
import ch.alpine.sophis.srf.SurfaceMesh;
import ch.alpine.sophus.bm.LinearBiinvariantMean;
import ch.alpine.sophus.lie.so2.ArcTan2D;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.sca.Chop;

public record ClothoidGlyphRender(Shape shape, Tensor domain) implements RenderInterface {
  private static final Chop CHOP = Chop._02;

  public static ScalarTensorFunction clothoid(Tensor p0, Tensor p1, Tensor p2, Tensor p3) {
    Tensor hull = ConvexHull2D.of(Tensors.of(p0, p1, p2, p3));
    Scalar scalar = PolygonArea.of(hull);
    if (CHOP.isZero(scalar)) {
      return BezierCurve.of(LinearBiinvariantMean.INSTANCE, Tensors.of(p0, p3));
      // System.err.println("nono");
    }
    if (CHOP.isClose(p0, p1))
      return clothoid(p1, p2, p3);
    if (CHOP.isClose(p2, p3))
      return clothoid(p0, p1, p2);
    return ClothoidBuilders.SE2_ANALYTIC.clothoidBuilder().curve( //
        Append.of(p0, ArcTan2D.of(p1.subtract(p0))), //
        Append.of(p3, ArcTan2D.of(p3.subtract(p2))));
  }

  public static Clothoid clothoid(Tensor p0, Tensor p1, Tensor p2) {
    return ClothoidBuilders.SE2_ANALYTIC.clothoidBuilder().curve( //
        Append.of(p0, ArcTan2D.of(p1.subtract(p0))), //
        Append.of(p2, ArcTan2D.of(p2.subtract(p1))));
  }

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    SurfaceMesh surfaceMesh = GlyphMesh.of(shape);
    for (int[] face : surfaceMesh.faces()) {
      switch (face.length) {
      case 2: {
        graphics.draw(geometricLayer.toLine2D( //
            surfaceMesh.vrt.get(face[0]), //
            surfaceMesh.vrt.get(face[1])));
        break;
      }
      case 3: {
        ScalarTensorFunction stf = clothoid( //
            surfaceMesh.vrt.get(face[0]), //
            surfaceMesh.vrt.get(face[1]), //
            surfaceMesh.vrt.get(face[2]));
        graphics.draw(geometricLayer.toPath2D(domain.maps(stf)));
        break;
      }
      case 4: {
        ScalarTensorFunction stf = clothoid( //
            surfaceMesh.vrt.get(face[0]), //
            surfaceMesh.vrt.get(face[1]), //
            surfaceMesh.vrt.get(face[2]), //
            surfaceMesh.vrt.get(face[3]));
        graphics.draw(geometricLayer.toPath2D(domain.maps(stf)));
        break;
      }
      default:
        throw new IllegalArgumentException("Unexpected value: " + face.length);
      }
    }
  }
}
