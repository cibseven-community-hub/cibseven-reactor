package org.cibseven.community.example.reactor;


import org.cibseven.bpm.engine.delegate.CaseExecutionListener;
import org.cibseven.bpm.engine.delegate.DelegateCaseExecution;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.bus.CamundaSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CamundaSelector(type = "humanTask")
public class CaseHumanTaskLifecycleListener implements CaseExecutionListener {
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public CaseHumanTaskLifecycleListener(CamundaEventBus eventBus) {
    eventBus.register(this);
  }

  @Override
  public void notify(DelegateCaseExecution delegateCaseExecution) throws Exception {
    logger.info("{} {}", delegateCaseExecution.getEventName(), delegateCaseExecution.getActivityName());
  }
}
