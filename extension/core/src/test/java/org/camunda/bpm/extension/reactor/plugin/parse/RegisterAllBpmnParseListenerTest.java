package org.camunda.bpm.extension.reactor.plugin.parse;

import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.form.handler.TaskFormHandler;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.delegate.TaskListener.*;
import static org.mockito.Mockito.mock;

public class RegisterAllBpmnParseListenerTest {

  private final TaskListener taskListener = mock(TaskListener.class);

  private final ExecutionListener executionListener = mock(ExecutionListener.class);

  private final RegisterAllBpmnParseListener parseListener = new RegisterAllBpmnParseListener(taskListener, executionListener, false);

  @Test
  public void add_taskListeners_to_taskDefinition() {
    final TaskDefinition taskDefinition = new TaskDefinition(mock(TaskFormHandler.class));
    parseListener.addTaskListener(taskDefinition);

    assertThat(taskDefinition.getTaskListeners()).hasSize(6);

    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_ASSIGNMENT)).containsExactly(taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_COMPLETE)).containsExactly(taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_CREATE)).containsExactly(taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_DELETE)).containsExactly(taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_TIMEOUT)).containsExactly(taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_UPDATE)).containsExactly(taskListener);
  }

  @Test
  public void add_taskListeners_to_already_existin_listeners() {
    final TaskDefinition taskDefinition = new TaskDefinition(mock(TaskFormHandler.class));
    final TaskListener taskListenerFromBpmn = mock(TaskListener.class);
    taskDefinition.addTaskListener(EVENTNAME_ASSIGNMENT, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_COMPLETE, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_CREATE, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_DELETE, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_TIMEOUT, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_UPDATE, taskListenerFromBpmn);

    parseListener.addTaskListener(taskDefinition);

    assertThat(taskDefinition.getTaskListeners()).hasSize(6);

    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_ASSIGNMENT)).containsExactly(taskListenerFromBpmn, taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_COMPLETE)).containsExactly(taskListenerFromBpmn, taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_CREATE)).containsExactly(taskListenerFromBpmn, taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_DELETE)).containsExactly(taskListenerFromBpmn, taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_TIMEOUT)).containsExactly(taskListenerFromBpmn, taskListener);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_UPDATE)).containsExactly(taskListenerFromBpmn, taskListener);
  }

  @Test
  public void add_taskListeners_to_already_existin_listeners_with_reactor_listener_first() {
    final TaskDefinition taskDefinition = new TaskDefinition(mock(TaskFormHandler.class));
    final TaskListener taskListenerFromBpmn = mock(TaskListener.class);
    taskDefinition.addTaskListener(EVENTNAME_ASSIGNMENT, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_COMPLETE, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_CREATE, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_DELETE, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_TIMEOUT, taskListenerFromBpmn);
    taskDefinition.addTaskListener(EVENTNAME_UPDATE, taskListenerFromBpmn);

    final RegisterAllBpmnParseListener parseListener = new RegisterAllBpmnParseListener(taskListener, executionListener, true);
    parseListener.addTaskListener(taskDefinition);

    assertThat(taskDefinition.getTaskListeners()).hasSize(6);

    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_ASSIGNMENT)).containsExactly(taskListener, taskListenerFromBpmn);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_COMPLETE)).containsExactly(taskListener, taskListenerFromBpmn);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_CREATE)).containsExactly(taskListener, taskListenerFromBpmn);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_DELETE)).containsExactly(taskListener, taskListenerFromBpmn);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_TIMEOUT)).containsExactly(taskListener, taskListenerFromBpmn);
    assertThat(taskDefinition.getTaskListenersForEvent(EVENTNAME_UPDATE)).containsExactly(taskListener, taskListenerFromBpmn);
  }
}
