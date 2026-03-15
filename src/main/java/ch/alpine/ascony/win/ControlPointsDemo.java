// code by jph, gjoel
package ch.alpine.ascony.win;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JButton;

import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.ascony.ren.ColorPair;
import ch.alpine.ascony.ren.LeversRender;
import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.gfx.GeometricComponent;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.sophis.crv.d2.Extract2D;
import ch.alpine.sophis.crv.dub.DubinsGenerator;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Drop;
import ch.alpine.tensor.alg.Insert;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.sca.Abs;
import ch.alpine.tensor.sca.pow.Sqrt;

/** class is used in other projects outside of owl */
public abstract class ControlPointsDemo extends ManifoldDisplayDemo {
  private class ControlPointsRender implements RenderInterface {
    /** mouse snaps 20 pixel to control points */
    private static final Scalar PIXEL_THRESHOLD = RealScalar.of(20.0);
    /** refined points */
    private static final Stroke STROKE = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3 }, 0);

    private class Midpoints {
      private final ManifoldDisplay manifoldDisplay = manifoldDisplay();
      private final Tensor midpoints;
      private final int index;

      Midpoints() {
        midpoints = ControlInsertions.of(manifoldDisplay.geodesicSpace(), getGeodesicControlPoints());
        Tensor mouse = mouseSe2CState();
        Tensor mouse_dist = Tensor.of(midpoints.stream() //
            .map(manifoldDisplay::point2xy) //
            .map(mouse.extract(0, 2)::subtract) //
            .map(Vector2Norm::of));
        Optional<ArgMinValue> argMinValue = ArgMinValue.of(mouse_dist);
        index = argMinValue.map(ArgMinValue::index).orElseThrow();
      }

      Tensor closestXY() {
        return manifoldDisplay.point2xy(midpoints.get(index));
      }
    }

    private final MouseAdapter mouseAdapter = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent mouseEvent) {
        if (!isPositioningEnabled())
          return;
        Tensor mouse = mouseSe2CState();
        switch (mouseEvent.getButton()) {
        case MouseEvent.BUTTON1:
          if (isPositioningOngoing()) {
            min_index = null; // release
          } else {
            Tensor mouse_dist = Tensor.of(controlPointsSe2.points_se2().stream() //
                .map(mouse::subtract) //
                .map(Extract2D.FUNCTION) //
                .map(Vector2Norm::of));
            Optional<ArgMinValue> argMinValue = ArgMinValue.of(mouse_dist, getPositioningThreshold());
            min_index = argMinValue.map(ArgMinValue::index).orElse(null);
            if (!isPositioningOngoing() && controlPointType().addRemove()) {
              // insert
              if (controlPointsSe2.length() < 2 || !controlPointType().indicateMidpoint()) {
                controlPointsSe2.points_se2().append(mouse);
                min_index = controlPointsSe2.length() - 1;
              } else {
                Midpoints midpoints = new Midpoints();
                controlPointsSe2 = new ControlPosSe2(Insert.of(controlPointsSe2.points_se2(), mouse, midpoints.index));
                min_index = midpoints.index;
              }
            }
          }
          break;
        case MouseEvent.BUTTON3: // remove point
          if (controlPointType().addRemove()) {
            if (!isPositioningOngoing()) {
              Tensor mouse_dist = Tensor.of(controlPointsSe2.points_se2().stream() //
                  .map(mouse::subtract) //
                  .map(Extract2D.FUNCTION) //
                  .map(Vector2Norm::of));
              Optional<ArgMinValue> argMinValue = ArgMinValue.of(mouse_dist, getPositioningThreshold());
              min_index = argMinValue.map(ArgMinValue::index).orElse(null);
            }
            if (isPositioningOngoing()) {
              controlPointsSe2 = new ControlPosSe2(Drop.index(controlPointsSe2.points_se2(), min_index));
              min_index = null;
            }
          }
          break;
        default:
        }
      }
    };
    private ControlPosSe2 controlPointsSe2 = new ControlPosSe2(Tensors.empty());
    /** min_index is non-null while the user drags a control points */
    private Integer min_index = null;
    private boolean mousePositioning = true;

    @Override
    public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
      ManifoldDisplay manifoldDisplay = manifoldDisplay();
      if (!isPositioningEnabled())
        return;
      Tensor mouse = mouseSe2CState();
      if (isPositioningOngoing())
        controlPointsSe2.points_se2().set(mouse, min_index);
      else {
        Tensor mouse_dist = Tensor.of(controlPointsSe2.points_se2().stream() //
            .map(mouse::subtract) //
            .map(Extract2D.FUNCTION) //
            .map(Vector2Norm::of));
        Optional<ArgMinValue> argMinValue = ArgMinValue.of(mouse_dist, getPositioningThreshold());
        Optional<Scalar> value = argMinValue.map(ArgMinValue::value);
        final boolean hold = value.isPresent() && isPositioningEnabled();
        ColorPair colorPair = hold ? ColorPair.MOVEOFFER : ColorPair.INSERTION;
        graphics.setColor(colorPair.fill());
        Tensor posit = mouse;
        if (hold) {
          graphics.setStroke(new BasicStroke(2f));
          Tensor closest = controlPointsSe2.points_se2().get(argMinValue.map(ArgMinValue::index).orElseThrow());
          graphics.draw(geometricLayer.toPath2D(Tensors.of(mouse, closest)));
          posit.set(closest.get(0), 0);
          posit.set(closest.get(1), 1);
        }
        manifoldDisplay.showPoints(colorPair, RealScalar.ONE, Tensors.of(manifoldDisplay.xya2point(posit))) //
            .render(geometricLayer, graphics);
        if (!hold && Tensors.nonEmpty(controlPointsSe2.points_se2()) && controlPointType().indicateMidpoint()) {
          graphics.setColor(Color.RED);
          graphics.setStroke(STROKE);
          // TODO visualization can be enhanced
          graphics.draw(geometricLayer.toLine2D(mouse, new Midpoints().closestXY()));
          graphics.setStroke(new BasicStroke());
        }
      }
      if (controlPointType().draw()) {
        LeversRender leversRender = LeversRender.of( //
            manifoldDisplay, getGeodesicControlPoints(), null, geometricLayer, graphics);
        leversRender.renderSequence();
        if (controlPointType().equals(ControlPointType.CURVYCURV))
          leversRender.renderIndexP();
      }
    }

    /** when positioning is disabled, the mouse position is not indicated graphically
     * 
     * @param enabled */
    void setPositioningEnabled(boolean enabled) {
      if (!enabled)
        min_index = null;
      mousePositioning = enabled;
    }

    /** @return */
    boolean isPositioningEnabled() {
      return mousePositioning;
    }

    /** @return whether user is currently dragging a control point */
    boolean isPositioningOngoing() {
      return Objects.nonNull(min_index);
    }

    Scalar getPositioningThreshold() {
      return PIXEL_THRESHOLD.divide(Sqrt.FUNCTION.apply(Abs.FUNCTION.apply(Det.of(model2Pixel()))));
    }

    /** @param control points as matrix of dimensions N x 3 */
    void setControlPointsSe2(Tensor control) {
      min_index = null;
      this.controlPointsSe2 = new ControlPosSe2(Tensor.of(control.stream() //
          .map(row -> VectorQ.requireLength(row, 3).maps(Tensor::copy))));
    }

    /** @return control points as matrix of dimensions N x 3 */
    Tensor getControlPointsSe2() {
      return controlPointsSe2.points_se2();
    }

    /** @return control points for selected {@link ManifoldDisplay} */
    Tensor getGeodesicControlPoints() {
      return getGeodesicControlPoints(0, Integer.MAX_VALUE);
    }

    /** @param skip
     * @param maxSize
     * @return */
    Tensor getGeodesicControlPoints(int skip, int maxSize) {
      return controlPointsSe2.getGeodesicControlPoints(manifoldDisplay(), skip, maxSize);
    }
  }

  private final ControlPointsRender controlPointsRender = new ControlPointsRender();

  @SafeVarargs
  protected ControlPointsDemo(Object... objects) {
    super(objects);
    final GeometricComponent geometricComponent = geometricComponent();
    geometricComponent.addMouseListener(controlPointsRender.mouseAdapter);
    geometricComponent.addMouseMotionListener(controlPointsRender.mouseAdapter);
    geometricComponent.addRenderInterface(controlPointsRender);
    if (controlPointType().addRemove()) {
      AwtUtil.addSeparator(jToolBar());
      JButton jButton = new JButton("clear");
      jButton.addActionListener(_ -> controlPointsRender.setControlPointsSe2(Tensors.empty()));
      jToolBar().add(jButton);
    }
    {
      boolean hasSe2 = permitted_manifoldDisplays().stream().map(ManifoldDisplays::manifoldDisplay) //
          .filter(ManifoldDisplay::isXY_Angle).findAny().isPresent();
      boolean curvyc = controlPointType().equals(ControlPointType.CURVYCURV);
      if (hasSe2 && curvyc) {
        JButton jButton = new JButton("dubins");
        jButton.setToolTipText("project control points to dubins path");
        jButton.addActionListener(_ -> {
          if (manifoldDisplay().isXY_Angle())
            controlPointsRender.setControlPointsSe2( //
                DubinsGenerator.project(controlPointsRender.getControlPointsSe2()));
        });
        jToolBar().add(jButton);
      }
    }
    geometricComponent.addRenderInterface(controlPointsRender);
  }

  protected abstract ControlPointType controlPointType();

  public final boolean isPositioningOngoing() {
    return controlPointsRender.isPositioningOngoing();
  }

  public final void setPositioningEnabled(boolean enabled) {
    controlPointsRender.setPositioningEnabled(enabled);
  }

  /** @return control points as matrix of dimensions N x 3 */
  public final Tensor getControlPointsSe2() {
    return controlPointsRender.getControlPointsSe2();
  }

  /** @param control points as matrix of dimensions N x 3 */
  public final void setControlPointsSe2(Tensor control) {
    controlPointsRender.setControlPointsSe2(control);
  }

  /** @return control points for selected {@link ManifoldDisplay} */
  public final Tensor getGeodesicControlPoints() {
    return controlPointsRender.getGeodesicControlPoints();
  }

  /** @param points */
  public final void setGeodesicControlPoints(Tensor points) {
    setControlPointsSe2(manifoldDisplay().point2xya().slash(points));
  }

  public final Tensor mouseSe2CState() {
    return geometricComponent().getMouseSe2CState();
  }

  public final Tensor model2Pixel() {
    return geometricComponent().getModel2Pixel();
  }
}
