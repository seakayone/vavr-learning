package net.signal7.learning.vavr.value.tuple;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

class TupleTests {

  @Test
  void unzip_Tuple() {
    List<Tuple2<Integer, String>> listOfTuples =
        List.of(
            Tuple.of(1, "one"),
            Tuple.of(2, "two")
        );

    final Tuple2<List<Integer>, List<String>> unzip = listOfTuples.unzip(e -> Tuple.of(e._1, e._2));
    assertThat(unzip._1).containsExactly(1, 2);
    assertThat(unzip._2).containsExactly("one", "two");
  }
}
