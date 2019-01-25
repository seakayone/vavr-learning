package net.signal7.learning.vavr.kata;


import io.vavr.test.Arbitrary;
import io.vavr.test.Property;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

class FizzBuzRzTest {

  private final Predicate<Integer> divisibleByThree = i -> i % 3 == 0;
  private final Predicate<Integer> divisibleByFive = i -> i % 5 == 0;

  private Arbitrary<Integer> positiveInts = Arbitrary.integer().filter(i -> i > 0);
  private Arbitrary<Integer> multiplesOfThree = positiveInts.filter(divisibleByThree);
  private Arbitrary<Integer> multiplesOfFive = positiveInts.filter(divisibleByFive);

  private static String fizzBuzzElement(Integer i) {
    return FizzBuzz.fizzBuzz().get(i - 1);
  }

  @Test
  void fizz() {
    val multiplesOfThreeNotFive = multiplesOfThree.filter(not(divisibleByFive));
    Property.def("Multiples of three but not five must equal Fizz")
      .forAll(multiplesOfThreeNotFive)
      .suchThat(i -> fizzBuzzElement(i).equals("Fizz"))
      .check()
      .assertIsSatisfied();
  }

  @Test
  void buzz() {
    val multiplesOfFiveNotThree = multiplesOfFive.filter(not(divisibleByThree));
    Property.def("Multiples of five but not three must equal Buzz")
      .forAll(multiplesOfFiveNotThree)
      .suchThat(i -> fizzBuzzElement(i).equals("Buzz"))
      .check()
      .assertIsSatisfied();
  }

  @Test
  void fizzBuzz() {
    val multiplesOfThreeAndFive = positiveInts.filter(divisibleByFive.and(divisibleByThree));
    Property.def("Multiples of three and five must equal FizzBuzz")
      .forAll(multiplesOfThreeAndFive)
      .suchThat(i -> fizzBuzzElement(i).equals("FizzBuzz"))
      .check()
      .assertIsSatisfied();
  }

  @Test
  void number() {
    val noMultiplesOfThreeOrFive = positiveInts.filter(not(divisibleByFive).and(not(divisibleByThree)));
    Property.def("Multiples of neither three nor five must equal the number")
      .forAll(noMultiplesOfThreeOrFive)
      .suchThat(i -> fizzBuzzElement(i).equals(i.toString()))
      .check()
      .assertIsSatisfied();
  }
}
