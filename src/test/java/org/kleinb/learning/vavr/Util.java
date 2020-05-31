package org.kleinb.learning.vavr;

import java.util.Random;
import java.util.function.Consumer;

public final class Util {

  private Util() {
    // util class, prevent instantiation
  }

  public static String randomBySeed(long seed) {
    return String.valueOf(new Random(seed).nextLong());
  }

  public static <T> Consumer<T> noop() {
    return t -> {
    };
  }
}
