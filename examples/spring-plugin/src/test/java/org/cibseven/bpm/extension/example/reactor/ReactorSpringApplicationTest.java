package org.cibseven.bpm.extension.example.reactor;

import org.cibseven.bpm.engine.ProcessEngine;
import org.cibseven.bpm.engine.RuntimeService;
import org.cibseven.bpm.engine.TaskService;
import org.cibseven.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.cibseven.bpm.engine.runtime.ProcessInstance;
import org.cibseven.bpm.engine.task.Task;
import org.cibseven.bpm.extension.reactor.CamundaReactor;
import org.cibseven.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReactorSpringApplication.class)
public class ReactorSpringApplicationTest {

  @Autowired
  private ProcessEngine processEngine;

  @Autowired
  private ReactorProcessEnginePlugin plugin;

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
