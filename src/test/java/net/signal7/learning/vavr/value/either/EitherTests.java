package net.signal7.learning.vavr.value.either;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.filter;

import io.vavr.control.Either;
import java.util.function.Function;
import lombok.Value;
import lombok.val;
import org.junit.jupiter.api.Test;

class EitherTests {

  @Value(staticConstructor = "of")
  static class Failed {

    String reason;
  }

  @Test
  void failed_with_fatal_reason_should_throw() {
    val failure = Either.<Failed, String>left(Failed.of("fatal"));
    assertThatThrownBy(() -> getSuccessOrThrow(failure))
        .isInstanceOf(IllegalArgumentException.class);
  }

  private String getSuccessOrThrow(Either<Failed, String> failure) {
    return failure.swap().flatMap(this::successOnNonFatal).swap()
        .mapLeft(Failed::getReason)
        .getOrElseThrow((Function<String, IllegalArgumentException>) IllegalArgumentException::new);
  }

  private Either<String, Failed> successOnNonFatal(Failed failed) {
    final String reason = failed.getReason();
    if ("fatal".equals(reason)) {
      return Either.right(failed);
    }
    return Either.left(failed.getReason());
  }

  @Test
  void failed_with_nonfatal_reason_should_throw() {
    Either<Failed, String> failure = Either.left(Failed.of("nonfatal"));

    assertThat(getSuccessOrThrow(failure)).isEqualTo("nonfatal");
  }


  @Test
  void foo() {
    Either<Failed, String> failure = Either.left(Failed.of("nonfatal"));


  }
}
