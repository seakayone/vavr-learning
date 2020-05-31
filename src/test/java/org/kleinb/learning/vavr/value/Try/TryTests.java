package org.kleinb.learning.vavr.value.Try;

import static org.assertj.vavr.api.VavrAssertions.assertThat;

import io.vavr.control.Try;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.val;
import org.junit.jupiter.api.Test;

class TryTests {

  @Test
  void create_an_url() throws MalformedURLException {
    val url = Try.of(() -> new URL("http://signal7.de"));
    assertThat(url).contains(new URL("http://signal7.de"));
  }

  @Test
  void access_an_url() {
    val in = Try.of(() -> new URL("http://signal7.de"))
        .flatMap(url -> Try.of(url::openConnection))
        .flatMap(con -> Try.of(con::getInputStream));
    assertThat(in).containsInstanceOf(InputStream.class);
  }

}
