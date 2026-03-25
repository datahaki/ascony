// code by jph, gjoel
package ch.alpine.ascony.win;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.gfx.GeometricComponent;
import ch.alpine.bridge.gfx.GeometricLayer;
import ch.alpine.bridge.gfx.PvmBuilder;
import ch.alpine.bridge.gfx.RenderInterface;
import ch.alpine.bridge.util.CopyOnWriteLinkedSet;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;

/** class is used in other projects outside of owl */
public abstract class ManifoldDisplayDemo extends AbstractDemo implements RenderInterface {
  private final List<ManifoldDisplays> list;
  private final JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
  private final Set<Consumer<ManifoldDisplays>> listeners = new CopyOnWriteLinkedSet<>();
  // ---
  private ManifoldDisplays selected_manifoldDisplays;

  @SafeVarargs
  protected ManifoldDisplayDemo(Object... objects) {
    super(objects);
    list = EnumSet.copyOf(permitted_manifoldDisplays()).stream().toList();
    selected_manifoldDisplays = list.getFirst();
    final GeometricComponent geometricComponent = geometricComponent();
    geometricComponent.addRenderInterfaceBackground(new RenderInterface() {
      @Override
      public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
        manifoldDisplay().background().render(geometricLayer, graphics);
      }
    });
    listeners.add(this::setManifoldDisplay);
    if (0 < list.size()) {
      for (ManifoldDisplays manifoldDisplays : list)
        jTabbedPane.addTab(manifoldDisplays.manifoldDisplay().geodesicSpace().toString(), new JPanel());
      jTabbedPane.addChangeListener(_ -> {
        ManifoldDisplays selected = list.get(jTabbedPane.getSelectedIndex());
        // IO.println("INVOKED");
        listeners.forEach(listener -> listener.accept(selected));
      });
      jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      getWindow().addWest(jTabbedPane);
    }
    geometricComponent.addRenderInterface(this);
    Tensor pvm = PvmBuilder.rhs().setOffset(300, 300).setPerPixel(RealScalar.of(100)).digest();
    geometricComponent.setModel2Pixel(pvm);
    {
      addChangeListener(new Consumer<ManifoldDisplays>() {
        Tensor prev = null;

        @Override
        public void accept(ManifoldDisplays md) {
          if (ManifoldDisplays.S2_RP2.contains(md)) {
            Point point = AwtUtil.center(geometricComponent().getSize());
            int w = Math.min(point.x, point.y);
            if (0 < w) {
              prev = geometricComponent.getModel2Pixel();
              Tensor pvm = PvmBuilder.rhs().setOffset(point.x, point.y).setPerPixel(Rational.of(w * 3, 4)).digest();
              geometricComponent.setModel2Pixel(pvm);
            } else
              IO.println("bypass since window degenerate");
          } else {
            if (Objects.nonNull(prev)) {
              geometricComponent.setModel2Pixel(prev);
              prev = null;
            }
          }
        }
      });
    }
  }

  protected final void addChangeListener(Consumer<ManifoldDisplays> consumer) {
    listeners.add(consumer);
  }

  protected final void addChangeListener(Runnable runnable) {
    listeners.add(_ -> runnable.run());
  }

  /** @return */
  public final ManifoldDisplay manifoldDisplay() {
    return selected_manifoldDisplays.manifoldDisplay();
  }

  public final ManifoldDisplays getSelectedMD() {
    return selected_manifoldDisplays;
  }

  /** invokes listeners
   * 
   * @param manifoldDisplays */
  public final void setManifoldDisplay(ManifoldDisplays manifoldDisplays) {
    jTabbedPane.setSelectedIndex(list.indexOf(manifoldDisplays));
    this.selected_manifoldDisplays = manifoldDisplays;
  }

  /** @return */
  protected abstract Collection<ManifoldDisplays> permitted_manifoldDisplays();
}
