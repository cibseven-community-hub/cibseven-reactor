package org.cibseven.community.reactor.process;

import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.bpm.engine.test.ProcessEngineRule;
import org.cibseven.bpm.engine.test.assertions.ProcessEngineTests;
import org.cibseven.bpm.engine.variable.Variables;
import org.cibseven.community.reactor.CamundaReactor;
import org.cibseven.community.reactor.ReactorProcessEngineConfiguration;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.bus.SelectorBuilder;
import org.cibseven.community.reactor.event.DelegateEvent;
import org.cibseven.community.reactor.event.DelegateEventConsumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.reset;

public class MultiInstanceTaskTest {

  @Rule
  public final ProcessEngineRule processEngineRule = ReactorProcessEngineConfiguration.buildRule();

  private CamundaEventBus eventBus;

  @Before
  public void setUp() {
    eventBus = CamundaReactor.eventBus();
  }

  @After
  public void tearDown() {
    reset();
  }

  @Test
  @Deployment(resources="MultiInstanceTaskProcess.bpmn")
  public void start_multi_instance_process() {

    eventBus.register(SelectorBuilder.selector(), new DelegateEventConsumer() {

      @Override
      public void accept(final DelegateEvent t) {
        System.out.println(t);
      }
    });

    ProcessEngineTests.runtimeService().startProcessInstanceByKey("multiInstanceTaskProcess", Variables.createVariables().putValue("items", Arrays.asList("foo","bar")));


  }

}
