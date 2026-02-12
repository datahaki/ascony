// code by jph
package ch.alpine.ascony.sym;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.itp.BinaryAverage;

/** SymNode extends from here */
public interface SymLink {
  /** @return */
  Tensor position();

  Tensor position(BinaryAverage binaryAverage);

  /** @return depth from leaft */
  int depth();
}
