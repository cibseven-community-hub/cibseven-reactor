package org.cibseven.community.reactor.plugin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cibseven.community.reactor.CamundaReactor.selector;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.cibseven.bpm.engine.RepositoryService;
import org.cibseven.bpm.engine.delegate.DelegateTask;
import org.cibseven.bpm.engine.delegate.TaskListener;
import org.cibseven.bpm.engine.repository.ProcessDefinition;
import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.bpm.engine.test.ProcessEngineRule;
import org.cibseven.community.reactor.CamundaReactorTestHelper;
import org.cibseven.community.reactor.ReactorProcessEngineConfiguration;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.event.DelegateEvent;
import org.cibseven.community.reactor.event.DelegateEventConsumer;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;


@Deployment(resources = {"ProcessA.bpmn"})
public class ReactorProcessEnginePluginTest {

  private final CamundaEventBus eventBus = new CamundaEventBus();

  @Rule
  public final ProcessEngineRule camunda = ReactorProcessEngineConfiguration.buildRule(eventBus);

  private final Logger logger = getLogger(this.getClass());

  final Map<String, LinkedHashSet<DelegateEvent>> events = new LinkedHashMap<>();

  private final DelegateEventConsumer delegateEventConsumer = new DelegateEventConsumer() {
    @Override
    public void accept(DelegateEvent event) {
      LinkedHashSet<DelegateEvent> e = events.get(event.getKey());
      if (e == null) {
        events.put(event.getKey().toString(), new LinkedHashSet<DelegateEvent>());
        accept(event);
        return;
      }

      e.add(event);
    }
  };

  @Test
  public void register_events() throws Exception {
    eventBus.register(selector().process("process_a"), delegateEventConsumer);
    eventBus.register(selector().process("process_a").element("task_a"), delegateEventConsumer);
    eventBus.register(selector().process("process_a").element("task_a").event("complete"), delegateEventConsumer);
    eventBus.register(selector().process("process_a").element("task_a").event("start"), delegateEventConsumer);
    eventBus.register(selector().process("process_a").element("task_a").event("end"), delegateEventConsumer);
    eventBus.register(selector().event("create"), delegateEventConsumer);
    eventBus.register(selector(), delegateEventConsumer);

    camunda.getRuntimeService().startProcessInstanceByKey("process_a");

    for (Map.Entry<String, LinkedHashSet<DelegateEvent>> e : events.entrySet()) {
      logger.info("* " + e.getKey());
      for (DelegateEvent v : e.getValue()) {
        logger.info("    * " + v);
      }
    }

    assertThat(events).isNotEmpty();
  }

  @Test
  public void simulate_assign_task_on_event() {
    // a caching component is always present
    assertThat(eventBus.get().getConsumerRegistry()).hasSize(1);

    eventBus.register(selector().event("create").task(), new TaskListener() {
      @Override
      public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("foo");
        delegateTask.addCandidateGroup("bar");
        delegateTask.setName("my task");
      }
    });

    assertThat(eventBus.get().getConsumerRegistry()).hasSize(2);

    ProcessDefinition processDefinition = CamundaReactorTestHelper.processDefinition();
    RepositoryService repositoryService = mock(RepositoryService.class);
    when(repositoryService.getProcessDefinition(processDefinition.getId())).thenReturn(processDefinition);

    DelegateTask task = CamundaReactorTestHelper.delegateTask();
    when(task.getProcessEngineServices().getRepositoryService()).thenReturn(repositoryService);

    eventBus.notify(task);
    verify(task).setAssignee("foo");
    verify(task).addCandidateGroup("bar");
    verify(task).setName("my task");
  }
}
