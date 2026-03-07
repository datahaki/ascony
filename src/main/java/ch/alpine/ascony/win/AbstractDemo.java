// code by jph
package ch.alpine.ascony.win;

import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.gfx.GeometricComponent;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.tensor.ext.PackageTestAccess;

public class AbstractDemo implements WindowProvider {
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
        AwtUtil.addSeparator(timerFrame.jToolBar);
    }
    timerFrame.jFrame.setTitle(FriendlyFormat.defaultTitle(getClass()));
  }

  protected final GeometricComponent geometricComponent() {
    return timerFrame.geometricComponent;
  }

  protected final Dimension getSize() {
    return timerFrame.geometricComponent.getSize();
  }

  @Override
  public final Window getWindow() {
    return timerFrame.jFrame;
  }

  @PackageTestAccess
  final Object[] objects() {
    return objects;
  }

  public final FieldsEditor fieldsEditor(int index) {
    if (index < fieldsEditors.size())
      return fieldsEditors.get(index);
    System.err.println("no can do: " + index + " vs. " + fieldsEditors.size());
    return null;
  }
}
