// code by jph, gjoel
package ch.alpine.ascony.win;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.alpine.ascony.dis.ManifoldDisplay;
import ch.alpine.ascony.dis.ManifoldDisplays;
import ch.alpine.bridge.util.CopyOnWriteLinkedSet;
import ch.alpine.tensor.RealScalar;

/** class is used in other projects outside of owl */
// TODO ASCONA possibly provide option for cyclic midpoint indication (see R2Bary..Coord..Demo)
public abstract class ManifoldDisplayDemo extends AbstractDemo {
  private final List<ManifoldDisplays> list;
  private final JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
  private final Set<Consumer<ManifoldDisplays>> listeners = new CopyOnWriteLinkedSet<>();
  // ---
  private ManifoldDisplays selected_manifoldDisplays;

  @SafeVarargs
  protected ManifoldDisplayDemo(Object... objects) {
    super(objects);
    list = permitted_manifoldDisplays();
    selected_manifoldDisplays = list.getFirst();
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
      timerFrame.addWest(jTabbedPane);
    }
    timerFrame.geometricComponent.setPerPixel(RealScalar.of(100));
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
  protected abstract List<ManifoldDisplays> permitted_manifoldDisplays();
}
