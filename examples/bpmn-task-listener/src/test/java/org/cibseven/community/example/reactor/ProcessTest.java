package org.cibseven.community.example.reactor;

import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskQuery;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskService;

import org.assertj.core.api.Assertions;
import org.cibseven.bpm.engine.runtime.ProcessInstance;
import org.cibseven.bpm.engine.task.Task;
import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.bpm.engine.test.ProcessEngineRule;
import org.cibseven.community.reactor.CamundaReactor;
import org.cibseven.community.reactor.bus.SelectorBuilder;
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
