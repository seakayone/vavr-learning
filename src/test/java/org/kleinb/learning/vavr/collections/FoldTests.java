package org.kleinb.learning.vavr.collections;

import static io.vavr.collection.List.empty;
import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

class FoldTests {

  @Test
  void reverse_list_with_foldLeft() {
    final var list = List.of(1, 2, 3, 4, 5, 6, 7);
    final var reversed = list.foldLeft(empty(), List::prepend);
    assertThat(reversed).containsExactly(7, 6, 5, 4, 3, 2, 1);
  }

}
