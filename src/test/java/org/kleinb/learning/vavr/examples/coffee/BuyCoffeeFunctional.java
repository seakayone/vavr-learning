package org.kleinb.learning.vavr.examples.coffee;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuyCoffeeFunctional {

  class Cafe {

    Tuple2<Coffee, Charge> buyCoffee(CreditCard cc) {
      final var cup = new Coffee();
      final var charge = new Charge(cc, cup.getPrice());
      return Tuple.of(cup, charge);
    }

    Tuple2<List<Coffee>, Charge> buyCoffees(CreditCard cc, int n) {
      var purchases = List.fill(n, () -> buyCoffee(cc));
      return purchases.unzip(e -> Tuple.of(e._1, e._2))
          .map2(charges -> charges.reduce(Charge::combine));
    }
  }

  @Value
  class Coffee {

    long price = 250;
  }

  @Value
  class CreditCard {

    @NonNull
    String number;
  }

  @Value
  class Charge {

    @NonNull
    CreditCard cc;
    long amount;

    Charge combine(Charge other) {
      if (this.cc == other.cc) {
        return new Charge(cc, this.amount + other.amount);
      } else {
        throw new IllegalArgumentException("Cannot combine charges from different credit cards");
      }
    }
  }

  @BeforeEach
  void setup() {
    this.cafe = new Cafe();
    this.cc = new CreditCard("nr");
  }

  private Cafe cafe;
  private CreditCard cc;

  @Test
  void buy_a_coffees() {
    final var bought = cafe.buyCoffee(cc);

    assertThat(bought._1).isNotNull();
    assertThat(bought._2.getAmount()).isEqualTo(250L);
    assertThat(bought._2.getCc()).isEqualTo(cc);
  }

  @Test
  void buy_2_coffees() {
    final var bought = cafe.buyCoffees(cc, 2);

    assertThat(bought._1).hasSize(2);
    assertThat(bought._2.getAmount()).isEqualTo(500L);
    assertThat(bought._2.getCc()).isEqualTo(cc);
  }
}
