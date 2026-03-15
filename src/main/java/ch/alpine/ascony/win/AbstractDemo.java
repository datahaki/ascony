// code by jph
package ch.alpine.ascony.win;

import java.awt.Dimension;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.JToolBar;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.gfx.GeometricComponent;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;

public class AbstractDemo implements WindowProvider {
  private final TimerFrame timerFrame = new TimerFrame();
  private final Map<Object, FieldsEditor> map = new IdentityHashMap<>();
  private final ObjectsParam objectsParam;

  /** @param objects may be null */
  protected AbstractDemo(Object... objects) {
    objectsParam = new ObjectsParam(objects);
    {
      ResourceLocator resourceLocator = ResourceLocator.of(getClass());
      resourceLocator.tryLoad(objectsParam);
      WindowClosed.runs(timerFrame, () -> resourceLocator.trySave(objectsParam));
    }
    {
      // int index = 0;
      for (Object object : objects) {
        FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(object, timerFrame.jToolBar);
        map.put(object, fieldsEditor);
        // if (++index < objects.length)
        // AwtUtil.addSeparator(timerFrame.jToolBar);
      }
    }
    timerFrame.setTitle(FriendlyFormat.defaultTitle(getClass()));
  }

  protected final JToolBar jToolBar() {
    return timerFrame.jToolBar;
  }

  protected final GeometricComponent geometricComponent() {
    return timerFrame.geometricComponent;
  }

  protected final Dimension getSize() {
    return timerFrame.geometricComponent.getSize();
  }

  @Override
  public final TimerFrame getWindow() {
    return timerFrame;
  }

  public final FieldsEditor fieldsEditor(Object object) {
    return map.get(object);
  }
}
