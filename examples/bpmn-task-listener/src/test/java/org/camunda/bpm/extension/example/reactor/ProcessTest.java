package org.camunda.bpm.extension.example.reactor;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskQuery;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskService;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.reactor.CamundaReactor;
import org.camunda.bpm.extension.reactor.bus.SelectorBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@Deployment(resources = "Process.bpmn")
public class ProcessTest {

  @Rule
  public final ProcessEngineRule processEngineRule = new ProcessEngineRule(Setup.processEngine());

  @Before
  public void init() {
    Setup.init();

    CamundaReactor.eventBus().register(SelectorBuilder.selector(), new EventLogListener());
  }

  @Test
  public void setDueDate() {
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("process_a");

    assertThat(processInstance).isWaitingAt("task_a");

    assertThat(task()).hasDueDate(Process.DUE_DATE);
  }

  @Test
  public void setCanidateGroups() {
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("process_a");

    assertThat(processInstance).isWaitingAt("task_a");

    Task t = taskService().createTaskQuery().singleResult();
    Assertions.assertThat(taskQuery().taskId(t.getId()).taskCandidateGroup(Process.GROUP_1).includeAssignedTasks().singleResult()).isNotNull();
    Assertions.assertThat(taskQuery().taskId(t.getId()).taskCandidateGroup(Process.GROUP_2).includeAssignedTasks().singleResult()).isNotNull();
    Assertions.assertThat(taskQuery().taskId(t.getId()).taskCandidateGroup(Process.GROUP_3).includeAssignedTasks().singleResult()).isNotNull();
  }

}
