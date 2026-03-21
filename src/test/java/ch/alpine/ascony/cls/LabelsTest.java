// code by jph
package ch.alpine.ascony.cls;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class LabelsTest {
  @ParameterizedTest
  @EnumSource
  void testSimple(Labels labels) {
    Distribution distribution = UniformDistribution.unit();
    Tensor vector = Tensors.vector(3, 1, 0, 2, 0, 1, 3);
    Classification classification = labels.apply(vector);
    classification.result(RandomVariate.of(distribution, vector.length()));
    assertThrows(Exception.class, () -> classification.result(RandomVariate.of(distribution, vector.length() - 1)));
  }
}
