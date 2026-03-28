// code by jph
package ch.alpine.ascony.win;

import java.util.IdentityHashMap;
import java.util.Map;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;

public class AbstractDemo extends TimerFrame implements WindowProvider {
  /** helper class to store and restore parameters of instances of {@link AbstractDemo} */
  @ReflectionMarker
  static class ObjectsParam {
    public final Object[] objects;

    public ObjectsParam(Object[] objects) {
      this.objects = objects;
    }
  }

  // ---
  final ObjectsParam objectsParam;
  private final Map<Object, FieldsEditor> map = new IdentityHashMap<>();

  protected AbstractDemo(Object... objects) {
    objectsParam = new ObjectsParam(objects);
    ResourceLocator resourceLocator = ResourceLocator.of(getClass());
    resourceLocator.tryLoad(objectsParam);
    WindowClosed.runs(this, () -> resourceLocator.trySave(objectsParam));
    for (Object object : objects)
      map.put(object, ToolbarFieldsEditor.addToComponent(object, jToolBar()));
    setTitle(FriendlyFormat.defaultTitle(getClass()));
  }

  /** @param object instance supplied to the constructor
   * @return */
  public final FieldsEditor fieldsEditor(Object object) {
    return map.get(object);
  }

  @Override
  public final TimerFrame getWindow() {
    return this;
  }
}
