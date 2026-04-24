// code by jph
package ch.alpine.ascony.res;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.ResourceData;
import ch.alpine.tensor.io.Import;

public class ResourceMapper {
  /** @param index for instance "ch/alpine/ascona/resource_index.vector"
   * @return */
  public static ResourceMapper of(String string) {
    return new ResourceMapper(string);
  }

  // ---
  /** parent terminates with '/' */
  private String parent;
  private List<String> lines;

  private ResourceMapper(String string) {
    parent = string.substring(0, string.lastIndexOf('/') + 1);
    lines = Collections.unmodifiableList(ResourceData.lines(string));
  }

  public List<String> list() {
    return lines;
  }

  public Tensor importTensor(String key) {
    return Import.of(parent + key);
  }

  public BufferedImage importImage(String key) {
    return ResourceData.bufferedImage(parent + key);
  }
}
