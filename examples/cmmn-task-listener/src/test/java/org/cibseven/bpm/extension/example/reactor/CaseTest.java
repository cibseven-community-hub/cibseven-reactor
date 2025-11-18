package org.cibseven.bpm.extension.example.reactor;

import static org.cibseven.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskService;

import org.cibseven.bpm.engine.CaseService;
import org.cibseven.bpm.engine.ProcessEngineConfiguration;
import org.cibseven.bpm.engine.runtime.CaseExecution;
import org.cibseven.bpm.engine.runtime.CaseInstance;
import org.cibseven.bpm.engine.task.Task;
import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Malte.Soerensen
 */
@Deployment(resources = "Case.cmmn")
public class CaseTest {

  @Rule
  public final ProcessEngineRule processEngineRule = new ProcessEngineRule(Setup.processEngine());

  @Before
  public void init() {
    Setup.init();
  }

  @Test
  public void assigneToMe() {
    CaseService caseService = processEngine().getCaseService();
    CaseInstance caseInstance = caseService.createCaseInstanceByKey("Case");

    Task task = taskService().createTaskQuery().caseInstanceId(caseInstance.getId()).singleResult();
    assertThat(task).isAssignedTo("me");

    CaseExecution caseExecution = caseService.createCaseExecutionQuery().activityId("_ab60407c-449e-4048-ba9c-01bb59f2f095").singleResult();
    caseService.completeCaseExecution(caseExecution.getId());
  }
}
