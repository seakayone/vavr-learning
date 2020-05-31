package org.kleinb.learning.vavr.value.option;

import static java.util.stream.Collectors.toList;
import static org.kleinb.learning.vavr.Util.noop;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.val;
import org.kleinb.learning.vavr.Util;
import org.junit.jupiter.api.Test;

class OptionalTests {

  private Optional<String> findBy(long id) {
    return Optional.of(id)
        .filter(it -> it > 0)
        .map(Util::randomBySeed);
  }

  @Test
  void none_is_empty() {
    val none = Optional.empty();
    assertThat(none).isEmpty();
  }

  @Test
  void null_ofNullable_is_empty() {
    val none = Optional.ofNullable(null);
    assertThat(none).isEmpty();
  }

  @Test
  void null_of_throws_NPE() {
    assertThatThrownBy(() ->
        Optional.of(null)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void foo_is_foo() {
    val none = Optional.of("foo");
    assertThat(none).contains("foo");
  }

  @Test
  void alternative_optional_value_Java8() {
    val fallback = findBy(-1).or(() -> Optional.of("fallback"));
    assertThat(fallback).contains("fallback");
  }

  @Test
  void alternative_optional_value_Java9() {
    val fallback = Optional.of(findBy(-1).orElse("fallback"));
    assertThat(fallback).contains("fallback");
  }

  @Test
  void list_of_optionals() {
    val postalCodes = java.util.List.of(-1, 64293, 79618, -42)
        .stream()
        .map(this::findBy)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());

    assertThat(postalCodes).hasSize(2);
  }

  @Test
  void reacting_on_empty_value_Java8() {
    val none = Optional.empty();
    if (!none.isPresent()) {
      System.out.println("Is empty value");
    }
  }

  @Test
  void reacting_on_empty_value_Java9() {
    val none = Optional.empty();
    none.ifPresentOrElse(
        noop(),
        () -> System.out.println("Is empty value")
    );
  }

  @Test
  void reacting_on_empty_value_Java11() {
    val none = Optional.empty();
    if (none.isEmpty()) {
      System.out.println("Is empty value");
    }
  }

  @Test
  void consuming_empty_and_value_ifPresent() {
    val maybe = Optional.empty();
    maybe.ifPresent(v -> {/*do something*/});
    if (!maybe.isPresent()) {
      // do something else
    }
  }

  @Test
  void consuming_empty_and_value_isPresent() {
    val maybe = Optional.empty();
    if (maybe.isPresent()) {
      // do something
    } else {
      // do something else
    }
  }

  @Test
  void as_exception_Java8() {
    val none = Optional.empty();
    assertThatThrownBy(() ->
        none.orElseThrow(IllegalStateException::new)
    ).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void as_exception_Java10() {
    val none = Optional.empty();
    assertThatThrownBy(() ->
        none.orElseThrow()
    ).isInstanceOf(NoSuchElementException.class);
  }
}
