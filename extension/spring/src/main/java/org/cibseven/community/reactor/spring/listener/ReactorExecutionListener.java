package org.cibseven.community.reactor.spring.listener;

import org.cibseven.bpm.engine.delegate.ExecutionListener;
import org.cibseven.bpm.engine.delegate.TaskListener;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class ReactorExecutionListener implements ExecutionListener {

  @Autowired
  public void register(CamundaEventBus camundaEventBus) {
    camundaEventBus.register(this);
  }

}
