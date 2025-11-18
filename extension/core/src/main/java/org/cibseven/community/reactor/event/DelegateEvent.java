package org.cibseven.community.reactor.event;

import org.cibseven.bpm.engine.delegate.DelegateCaseExecution;
import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.cibseven.bpm.engine.delegate.DelegateTask;
import org.cibseven.community.reactor.projectreactor.Event;

public abstract class DelegateEvent<T> extends Event<T> {

  public static DelegateTaskEvent wrap(DelegateTask delegateTask) {
    return new DelegateTaskEvent(delegateTask);
  }
  public static DelegateExecutionEvent wrap(DelegateExecution delegateExecution) {
    return new DelegateExecutionEvent(delegateExecution);
  }
  public static DelegateCaseExecutionEvent wrap(DelegateCaseExecution delegateCaseExecution) {
    return new DelegateCaseExecutionEvent(delegateCaseExecution);
  }

  private static final long serialVersionUID = 1L;

  public DelegateEvent(T data) {
    super(data);
    getId();
  }
}
