package org.cibseven.community.example.reactor;


import org.cibseven.bpm.engine.delegate.DelegateTask;
import org.cibseven.bpm.engine.delegate.TaskListener;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.bus.CamundaSelector;

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
