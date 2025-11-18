package org.cibseven.community.reactor.process;

import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.bpm.engine.test.ProcessEngineRule;
import org.cibseven.community.reactor.ReactorProcessEngineConfiguration;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.bus.SelectorBuilder;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.reset;
import static org.cibseven.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.cibseven.bpm.engine.test.mock.Mocks.register;

@Deployment(resources="ProcessWithExistingListeners.bpmn")
public class TasksWithExistingListenersReactorListenerFirstTest {

  private final CamundaEventBus eventBus = new CamundaEventBus();

  @Rule
  public final ProcessEngineRule processEngineRule = ReactorProcessEngineConfiguration.buildRule(eventBus, true);

  @After
  public void tearDown() {
    reset();
  }

  @Test
  public void reactor_listener_is_called_first() {
    final TestTaskCreateListener customTestTaskCreateListener = new TestTaskCreateListener();
    final TestTaskCreateListener reactorTestTaskCreateListener = new TestTaskCreateListener();

    //Custom Listener in BPMN
    register("testTaskCreateListener", customTestTaskCreateListener);

    //Listener via reactor bus
    eventBus.register(SelectorBuilder.selector(reactorTestTaskCreateListener), reactorTestTaskCreateListener);

    runtimeService().startProcessInstanceByKey("processWithExistingListeners");

    assertThat(customTestTaskCreateListener.calledTime).isNotNull();
    assertThat(reactorTestTaskCreateListener.calledTime).isNotNull();
    assertThat(customTestTaskCreateListener.calledTime.after(reactorTestTaskCreateListener.calledTime)).isTrue();
  }



}
