package org.kleinb.learning.vavr.collections;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SortListTest {

  @Nested
  class JavaCollection {

    @Test
    void sort_with_comparator() {
      val list = Arrays.asList(2, 3, 1);
      list.sort(comparingInt(i -> i));
      assertThat(list).containsExactly(1, 2, 3);
    }

    @Test
    void sort_with_stream() {
      val list = Stream.of(2, 3, 1)
          .sorted()
          .collect(toList());
      assertThat(list).containsExactly(1, 2, 3);
    }

    @Test
    void sort_with_IntStream() {
      val list = IntStream.of(2, 3, 1)
          .sorted()
          .boxed()
          .collect(toList());
      assertThat(list).containsExactly(1, 2, 3);
    }

    @Test
    void sort_with_Java_11() {
      val list = java.util.List.of(2, 3, 1);
      assertThatThrownBy(() -> list.sort(comparingInt(i -> i)))
          .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void empty_list_breaks_contract_too() {
      val list = Collections.emptyList();
      assertThatThrownBy(() -> list.add("foo"))
          .isInstanceOf(UnsupportedOperationException.class);
    }
  }

  @Nested
  class VavrCollection {

    @Test
    void sort_with_vavr_list() {
      val list = io.vavr.collection.List.of(2, 3, 1).sorted();
      assertThat(list).containsExactly(1, 2, 3);
    }
  }
}
