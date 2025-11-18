package org.camunda.bpm.extension.example.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.extension.reactor.CamundaReactor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReactorSpringStarterApplication.class)
public class ReactorSpringStarterApplicationTest {
	
  @Autowired
  private ProcessEngine processEngine;
  
  @Autowired
  private TaskService taskService;

  @Autowired
  private RuntimeService runtimeService;

  @Test
  public void contains_plugin() throws Exception {
    assertThat(CamundaReactor.eventBus(processEngine)).isNotNull();
  }

  @Test
  public void verify_assignment() throws Exception {
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_a");

    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();

    assertThat(task.getAssignee()).isEqualTo("foo");
  }
}
