package org.cibseven.community.reactor.projectreactor.registry;

import org.cibseven.community.reactor.projectreactor.selector.Selectors;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

// FIXME: fails!
@Ignore
public class CachableRegistrationTest {

  @Test
  public void getSelectorShouldReturnNoMatchSelectorAfterCancel() throws Exception {
    //GIVEN a cachable registration
    final CachableRegistration<String, String> reg = new CachableRegistration<>(Selectors.$("MyKey"), "SomeVal", () -> {});

    //WHEN the registriation is canceled
    reg.cancel();

    //THEN it should return a NO_MATCH selector (without some class cast exception)
    assertThat(reg.getSelector().matches("MyKey")).isFalse();
  }
}
