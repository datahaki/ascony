// code by jph
package ch.alpine.ascony.win;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.tensor.ext.HomeDirectory;

public abstract class AbstractDemo implements RenderInterface, WindowProvider {
  public final ResourceLocator RESOURCE_LOCATOR = //
      new ResourceLocator(HomeDirectory._local_share.mk_dirs(getClass().getName().split("\\.")));

  @Override
  public Window getWindow() {
    return timerFrame.jFrame;
  }

  // ---
  public final TimerFrame timerFrame = new TimerFrame();
  private final Object[] objects;
  private final List<FieldsEditor> fieldsEditors = new ArrayList<>();

  /** @param objects may be null */
  protected AbstractDemo(Object... objects) {
    this.objects = objects;
    timerFrame.jFrame.setTitle(getClass().getSimpleName());
    int index = 0;
    for (Object object : objects) {
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(object, timerFrame.jToolBar);
      fieldsEditors.add(fieldsEditor);
      if (++index < objects.length)
        timerFrame.jToolBar.addSeparator();
    }
    timerFrame.geometricComponent.addRenderInterface(this);
  }

  public Object[] objects() {
    return objects;
  }

  public FieldsEditor fieldsEditor(int index) {
    if (index < fieldsEditors.size())
      return fieldsEditors.get(index);
    System.err.println("no can do");
    return null;
  }

  /** @param width
   * @param height */
  public final void setVisible(int width, int height) {
    setVisible(100, 100, width, height);
  }

  public final void setVisible(int x, int y, int width, int height) {
    timerFrame.jFrame.setBounds(x, y, width, height);
    timerFrame.jFrame.setVisible(true);
  }

  public final void dispose() {
    timerFrame.jFrame.setVisible(false);
    timerFrame.jFrame.dispose();
  }
}
