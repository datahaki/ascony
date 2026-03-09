// code by jph
package ch.alpine.ascony.res;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.io.StringScalar;

public class ResourceIndexBuilder {
  public static final String FILENAME = "resource_index.vector";

  public static void of(Path root) {
    new ResourceIndexBuilder(root);
  }

  // ---
  private final Path root;
  private final int length;
  private final List<String> list = new LinkedList<>();

  private ResourceIndexBuilder(Path root) {
    this.root = root;
    length = root.toString().length() + 1;
    check(root);
    Collections.sort(list);
    Tensor tensor = Tensor.of(list.stream().map(StringScalar::of));
    Unprotect.Export(root.resolve(FILENAME), tensor);
  }

  void check(Path folder) {
    try {
      for (final Path file : Files.list(folder).toList()) {
        if (Files.isDirectory(file))
          check(file);
        else {
          if (file.getFileName().toString().equals(FILENAME)) {
            // System.err.println("skip " + file);
          } else {
            String suffix = file.toString().substring(length);
            Path path = root.resolve(suffix);
            if (!Files.isRegularFile(path))
              System.err.println(path);
            list.add(suffix);
          }
        }
      }
    } catch (IOException ioException) {
      throw new UncheckedIOException(ioException);
    }
  }

  static void main() {
    Path path = HomeDirectory.Projects.resolve("ascona/src/main/resources/ch/alpine/ascona/gokart");
    ResourceIndexBuilder.of(path);
  }
}
