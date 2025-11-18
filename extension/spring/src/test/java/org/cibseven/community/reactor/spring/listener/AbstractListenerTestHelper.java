package org.cibseven.community.reactor.spring.listener;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.cibseven.bpm.engine.delegate.DelegateCaseExecution;
import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.cibseven.bpm.engine.delegate.DelegateTask;
import org.cibseven.bpm.engine.repository.ProcessDefinition;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.springframework.beans.factory.annotation.Autowired;


public class AbstractListenerTestHelper {

  @Autowired
  protected CamundaEventBus camundaEventBus;


  public static DelegateTask delegateTask() {
    return delegateTask("task1", "process:1:1", "create");
  }

  public static DelegateTask delegateTask(String taskName, String processDefinitionId, String eventName) {
    final DelegateTask task = mock(DelegateTask.class, RETURNS_DEEP_STUBS);
    when(task.getBpmnModelElementInstance().getElementType().getTypeName()).thenReturn("userTask");

    when(task.getTaskDefinitionKey()).thenReturn(taskName);
    when(task.getProcessDefinitionId()).thenReturn(processDefinitionId);
    when(task.getEventName()).thenReturn(eventName);
    
    // Extract process key from processDefinitionId (e.g., "process:1:1" -> "process")
    String processKey = processDefinitionId.split(":")[0];
    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(processDefinition.getKey()).thenReturn(processKey);
    when(task.getProcessEngineServices().getRepositoryService().getProcessDefinition(processDefinitionId))
        .thenReturn(processDefinition);

    return task;
  }

  public static DelegateExecution delegateExecution() {
    final DelegateExecution execution = mock(DelegateExecution.class, RETURNS_DEEP_STUBS);

    when(execution.getBpmnModelElementInstance().getElementType().getTypeName()).thenReturn("serviceTask");
    when(execution.getProcessDefinitionId()).thenReturn("process:1:1");
    when(execution.getEventName()).thenReturn("start");
    when(execution.getCurrentActivityId()).thenReturn("service1");
    
    // Extract process key from processDefinitionId
    String processKey = "process";
    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(processDefinition.getKey()).thenReturn(processKey);
    when(execution.getProcessEngineServices().getRepositoryService().getProcessDefinition("process:1:1"))
        .thenReturn(processDefinition);

    return execution;
  }

  public static DelegateCaseExecution delegateCaseExecution() {
    final DelegateCaseExecution execution = mock(DelegateCaseExecution.class, RETURNS_DEEP_STUBS);

    when(execution.getCmmnModelElementInstance().getElementType().getTypeName()).thenReturn("serviceTask");
    when(execution.getCaseDefinitionId()).thenReturn("process:1:1");
    when(execution.getEventName()).thenReturn("start");
    when(execution.getActivityId()).thenReturn("service1");
    
    // Extract case key from caseDefinitionId
    String caseKey = "process";
    org.cibseven.bpm.engine.repository.CaseDefinition caseDefinition = mock(org.cibseven.bpm.engine.repository.CaseDefinition.class);
    when(caseDefinition.getKey()).thenReturn(caseKey);
    when(execution.getProcessEngineServices().getRepositoryService().getCaseDefinition("process:1:1"))
        .thenReturn(caseDefinition);

    return execution;
  }

}
