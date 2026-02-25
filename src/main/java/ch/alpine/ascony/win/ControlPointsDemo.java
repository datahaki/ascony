// code by jph, gjoel
package ch.alpine.ascony.win;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.ascony.ref.AsconaParam;
import ch.alpine.ascony.ren.ControlPointsRender;
import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.sophis.crv.dub.DubinsGenerator;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

/** class is used in other projects outside of owl */
// TODO ASCONA possibly provide option for cyclic midpoint indication (see R2Bary..Coord..Demo)
public abstract class ControlPointsDemo extends AbstractDemo {
  private final List<ManifoldDisplays> list;
  private final JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
  public final ControlPointsRender controlPointsRender;
  private final AsconaParam asconaParam;
  private final List<Consumer<ManifoldDisplays>> listeners = new LinkedList<>();
  private ManifoldDisplays selected_manifoldDisplays;

  @SafeVarargs
  protected ControlPointsDemo(Object... objects) {
    super(objects);
    list = permitted_manifoldDisplays();
    selected_manifoldDisplays = list.getFirst();
    listeners.add(this::setManifoldDisplay);
    if (0 < list.size()) {
      for (ManifoldDisplays manifoldDisplays : list)
        jTabbedPane.addTab(manifoldDisplays.manifoldDisplay().geodesicSpace().toString(), new JPanel());
      jTabbedPane.addChangeListener(_ -> {
        ManifoldDisplays selected = list.get(jTabbedPane.getSelectedIndex());
        listeners.forEach(listener -> listener.accept(selected));
      });
      jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      timerFrame.addWest(jTabbedPane);
    }
    this.asconaParam = (AsconaParam) objects[0];
    controlPointsRender = ControlPointsRender.create( //
        asconaParam, this::manifoldDisplay, timerFrame.geometricComponent);
    timerFrame.jToolBar.addSeparator();
    if (asconaParam.addRemoveControlPoints) {
      JButton jButton = new JButton("clear");
      jButton.addActionListener(_ -> controlPointsRender.setControlPointsSe2(Tensors.empty()));
      timerFrame.jToolBar.add(jButton);
    }
    timerFrame.geometricComponent.addRenderInterfaceBackground(new RenderInterface() {
      @Override
      public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
        manifoldDisplay().background().render(geometricLayer, graphics);
      }
    });
    timerFrame.geometricComponent.addRenderInterface(controlPointsRender);
  }

  public AsconaParam asconaParam() {
    return asconaParam;
  }

  protected void addChangeListener(Consumer<ManifoldDisplays> consumer) {
    listeners.add(consumer);
  }

  // TODO ASCONA API function should not be here!
  public final void addButtonDubins() {
    JButton jButton = new JButton("dubins");
    jButton.setToolTipText("project control points to dubins path");
    jButton.addActionListener(_ -> controlPointsRender.setControlPointsSe2(DubinsGenerator.project(controlPointsRender.control)));
    timerFrame.jToolBar.add(jButton);
  }

  /** @return */
  public final ManifoldDisplay manifoldDisplay() {
    return selected_manifoldDisplays.manifoldDisplay();
  }

  public final ManifoldDisplays getSelectedMD() {
    return selected_manifoldDisplays;
  }

  public final void setManifoldDisplay(ManifoldDisplays manifoldDisplays) {
    this.selected_manifoldDisplays = manifoldDisplays;
  }

  /** @return */
  protected abstract List<ManifoldDisplays> permitted_manifoldDisplays();

  /** @param control points as matrix of dimensions N x 3 */
  public final void setControlPointsSe2(Tensor control) {
    controlPointsRender.setControlPointsSe2(control);
  }

  /** @return control points as matrix of dimensions N x 3 */
  public final Tensor getControlPointsSe2() {
    return controlPointsRender.getControlPointsSe2();
  }

  /** @return control points for selected {@link ManifoldDisplay} */
  public final Tensor getGeodesicControlPoints() {
    return controlPointsRender.getGeodesicControlPoints();
  }
}
