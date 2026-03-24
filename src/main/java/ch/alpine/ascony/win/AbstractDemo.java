// code by jph
package ch.alpine.ascony.win;

import java.util.IdentityHashMap;
import java.util.Map;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;

public class AbstractDemo extends TimerFrame implements WindowProvider {
  private final Map<Object, FieldsEditor> map = new IdentityHashMap<>();
  private final ObjectsParam objectsParam;

  /** @param objects may be null */
  protected AbstractDemo(Object... objects) {
    objectsParam = new ObjectsParam(objects);
    ResourceLocator resourceLocator = ResourceLocator.of(getClass());
    resourceLocator.tryLoad(objectsParam);
    WindowClosed.runs(this, () -> resourceLocator.trySave(objectsParam));
    for (Object object : objects)
      map.put(object, ToolbarFieldsEditor.addToComponent(object, jToolBar()));
    setTitle(FriendlyFormat.defaultTitle(getClass()));
  }

  @Override
  public final TimerFrame getWindow() {
    return this;
  }

  public final FieldsEditor fieldsEditor(Object object) {
    return map.get(object);
  }
}
