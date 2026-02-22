// code by jph
package ch.alpine.ascony.win;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import ch.alpine.ascony.ren.RenderInterface;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;

public abstract class AbstractDemo implements RenderInterface, WindowProvider {
  protected final ResourceLocator resourceLocator = ResourceLocator.of(getClass());
  // ---
  public final TimerFrame timerFrame = new TimerFrame();
  private final Object[] objects;
  private final List<FieldsEditor> fieldsEditors = new ArrayList<>();

  /** @param objects may be null */
  protected AbstractDemo(Object... objects) {
    this.objects = objects;
    int index = 0;
    for (Object object : objects) {
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(object, timerFrame.jToolBar);
      fieldsEditors.add(fieldsEditor);
      if (++index < objects.length)
        timerFrame.jToolBar.addSeparator();
    }
    timerFrame.geometricComponent.addRenderInterface(this);
    timerFrame.jFrame.setTitle(FriendlyFormat.defaultTitle(getClass()));
  }

  @Override
  public Window getWindow() {
    return timerFrame.jFrame;
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
}
