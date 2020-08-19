package org.kleinb.learning.vavr.value.option;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.kleinb.learning.vavr.Util;

class OptionTests {

  private Option<String> findBy(long id) {
    return Option.of(id)
        .filter(it -> it > 0)
        .map(Util::randomBySeed);
  }

  @Test
  void none_is_empty() {
    final var none = Option.none();
    assertThat(none).isEmpty();
  }

  @Test
  void null_is_empty() {
    // will not explode with NPE
    final var none = Option.of(null);
    assertThat(none).isEmpty();
  }

  @Test
  void some_null_is_null() {
    // you cannot do this with Optional
    final var none = Option.some(null);
    assertThat(none.get()).isEqualTo(null);
  }

  @Test
  void foo_is_foo() {
    final var none = Option.of("foo");
    assertThat(none).contains("foo");
  }

  @Test
  void alternative_option_value() {
    final var actual = findBy(-1).orElse(() -> Option.of("fallback"));
    assertThat(actual).contains("fallback");
  }

  @Test
  void list_of_options() {
    final var postalCodes = List.of(-1, 64293, 79618, -42)
        .map(this::findBy)
        .flatMap(Option::toStream)
        .toList();

    assertThat(postalCodes).hasSize(2);
  }

  @Test
  void reacting_on_empty_value() {
    final var none = Option.none();
    none.onEmpty(() -> System.out.println("Is empty value"));
  }

  @Test
  void consuming_none_and_value() {
    final var none = Option.none();
    none
        .peek(it -> {/*do something*/})
        .onEmpty(() -> {/*do something else */});
  }

  @Test
  void as_exception() {
    final var none = Option.none();
    assertThatThrownBy(() ->
        none.getOrElseThrow(IllegalStateException::new)
    ).isInstanceOf(IllegalStateException.class);
  }
}
