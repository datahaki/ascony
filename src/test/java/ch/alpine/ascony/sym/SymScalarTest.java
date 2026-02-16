// code by jph
package ch.alpine.ascony.sym;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.ext.Serialization;

class SymScalarTest {
  @Test
  void testSimple() throws ClassNotFoundException, IOException {
    Serialization.copy(SymScalarLeaf.of(3)).hashCode();
    new SymScalarPart(SymScalarLeaf.of(2), SymScalarLeaf.of(3), Rational.HALF).hashCode();
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> new SymScalarPart(SymScalarLeaf.of(2), RealScalar.of(3), Rational.HALF));
  }
}
