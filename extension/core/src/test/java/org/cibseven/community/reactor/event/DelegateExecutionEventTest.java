package org.cibseven.community.reactor.event;

import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DelegateExecutionEventTest {

  @Mock
  private DelegateExecution execution;


  @Test
  public void id_is_not_null() {
    assertThat(DelegateExecutionEvent.wrap(execution).getId()).isNotNull();
  }
}
