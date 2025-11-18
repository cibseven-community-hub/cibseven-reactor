package org.cibseven.bpm.extension.reactor.event;

import org.cibseven.bpm.engine.ProcessEngineException;
import org.cibseven.bpm.engine.delegate.CaseExecutionListener;
import org.cibseven.bpm.engine.delegate.DelegateCaseExecution;
import java.util.function.Consumer;

public class DelegateCaseExecutionEvent extends DelegateEvent<DelegateCaseExecution> {

  public static Consumer<DelegateCaseExecutionEvent> consumer(final CaseExecutionListener listener) {
    return event -> {
      try {
        listener.notify(event.getData());
      } catch (Exception e) {
        throw new ProcessEngineException(e);
      }
    };
  }


  private static final long serialVersionUID = 1L;

  public DelegateCaseExecutionEvent(final DelegateCaseExecution data) {
    super(data);
  }
}
