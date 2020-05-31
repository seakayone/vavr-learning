package org.kleinb.learning.vavr.value;

import static io.vavr.API.CharSeq;
import static io.vavr.API.For;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

import io.vavr.collection.Iterator;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

class ForTests {

  @Test
  void test1() {
    assertThat(firstIndexOfAny("aba", new char[]{'z'}))
        .isEmpty();
  }

  @Test
  void test2() {
    assertThat(firstIndexOfAny("zzabyycdxx", new char[]{'z', 'a'}))
        .contains(0);
  }

  @Test
  void test3() {
    assertThat(firstIndexOfAny("zzabyycdxx", new char[]{'c', 'v', '0'}))
        .contains(6);
  }

  static Option<Integer> firstIndexOfAny(String input, char[] searchChars) {
    final Iterator<Option<Integer>> result = For(
        CharSeq(input).zipWithIndex().toStream(),
        Stream.ofAll(searchChars)
    ).yield((pair, character) ->
        pair._1 == character
            ? Option.of(pair._2)
            : Option.none());
    return result.flatMap(Option::iterator).headOption();
  }
}
