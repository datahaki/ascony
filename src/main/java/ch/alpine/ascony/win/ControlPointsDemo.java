// code by jph, gjoel
package ch.alpine.ascony.win;

import java.awt.Graphics2D;
import java.util.List;

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
  public final ControlPointsRender controlPointsRender;
  private final AsconaParam asconaParam;
  private ManifoldDisplays manifoldDisplays;

  @SafeVarargs
  protected ControlPointsDemo(Object... objects) {
    super(objects);
    List<ManifoldDisplays> list = getManifoldDisplays();
    manifoldDisplays = getManifoldDisplays().getFirst();
    if (!list.isEmpty() || true) {
      JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
      for (ManifoldDisplays md : list)
        jTabbedPane.addTab(md.toString(), new JPanel());
      jTabbedPane.addChangeListener(_ -> setManifoldDisplay(list.get(jTabbedPane.getSelectedIndex())));
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

  // TODO ASCONA API function should not be here!
  public final void addButtonDubins() {
    JButton jButton = new JButton("dubins");
    jButton.setToolTipText("project control points to dubins path");
    jButton.addActionListener(_ -> controlPointsRender.setControlPointsSe2(DubinsGenerator.project(controlPointsRender.control)));
    timerFrame.jToolBar.add(jButton);
  }

  /** @return */
  public final ManifoldDisplay manifoldDisplay() {
    return manifoldDisplays.manifoldDisplay();
  }

  public final ManifoldDisplays getSelectedMD() {
    return manifoldDisplays;
  }

  public final synchronized void setManifoldDisplay(ManifoldDisplays manifoldDisplays) {
    this.manifoldDisplays = manifoldDisplays;
    fieldsEditor(0).updateJComponents();
  }

  /** @return */
  protected abstract List<ManifoldDisplays> getManifoldDisplays();

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
