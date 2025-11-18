package org.cibseven.bpm.extension.reactor.event;

import org.cibseven.bpm.engine.delegate.DelegateCaseExecution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DelegateCaseExecutionEventTest {

  @Mock
  private DelegateCaseExecution execution;


  @Test
  public void id_is_not_null() {
    assertThat(DelegateCaseExecutionEvent.wrap(execution).getId()).isNotNull();
  }
}
