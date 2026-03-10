// code by jph
package ch.alpine.ascony.win;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

/** helper class to store and restore parameters of instances of {@link AbstractDemo} */
@ReflectionMarker
class ObjectsParam {
  public final Object[] objects;

  public ObjectsParam(Object[] objects) {
    this.objects = objects;
  }
}
