package org.kleinb.learning.vavr.pbt;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Tuple2;

import io.vavr.Tuple;
import io.vavr.collection.Stream;

class FizzBuzz {

  static Stream<String> fizzBuzz() {
    return Stream.from(0).map(FizzBuzz::numberToString);
  }

  private static String numberToString(Integer i) {
    return Match(Tuple.of(i % 3, i % 5)).of(
        Case($Tuple2($(0), $(0)), "FizzBuzz"),
        Case($Tuple2($(0), $()), "Fizz"),
        Case($Tuple2($(), $(0)), "Buzz"),
        Case($(), i.toString())
    );
  }
}
