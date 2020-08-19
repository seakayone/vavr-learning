package org.kleinb.learning.vavr.examples.coffee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuyCoffeeImperative {

  class Cafe {

    Coffee buyCoffee(CreditCard cc) {
      var cup = new Coffee();
      cc.charge(cup.getPrice());
      return cup;
    }

    List<Coffee> buyCoffees(CreditCard cc, int n) {
      var coffees = new ArrayList<Coffee>();
      while (n > 0) {
        var cup = buyCoffee(cc);
        coffees.add(cup);
        n--;
      }
      return coffees;
    }
  }

  @Value
  class Coffee {

    long price = 250;
  }

  @Data
  @AllArgsConstructor
  class CreditCard {

    private long balance;

    void charge(long amount) {
      balance = balance - amount;
    }
  }

  @BeforeEach
  void setup() {
    this.cafe = new Cafe();
    this.cc = new CreditCard(500);
  }

  private Cafe cafe;
  private CreditCard cc;

  @Test
  void buy_a_coffee() {
    var cup = cafe.buyCoffee(cc);

    assertThat(cup).isNotNull();
    assertThat(cc.getBalance()).isEqualTo(250);
  }

  @Test
  void buy_2_coffees() {
    var cup = cafe.buyCoffees(cc, 2);

    assertThat(cup).hasSize(2);
    assertThat(cc.getBalance()).isEqualTo(0);
  }
}
