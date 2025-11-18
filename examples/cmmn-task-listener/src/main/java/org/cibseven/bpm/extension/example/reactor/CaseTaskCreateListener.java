package org.cibseven.bpm.extension.example.reactor;


import org.cibseven.bpm.engine.delegate.DelegateTask;
import org.cibseven.bpm.engine.delegate.TaskListener;
import org.cibseven.bpm.extension.reactor.bus.CamundaEventBus;
import org.cibseven.bpm.extension.reactor.bus.CamundaSelector;

@CamundaSelector(type = "humanTask", event = TaskListener.EVENTNAME_CREATE)
public class CaseTaskCreateListener implements TaskListener {

  public CaseTaskCreateListener(final CamundaEventBus eventBus) {
    eventBus.register(this);
  }

  @Override
  public void notify(DelegateTask delegateTask) {
    delegateTask.setAssignee("me");
  }
}
