package org.cibseven.community.reactor.plugin.parse;

import org.cibseven.bpm.engine.delegate.CaseExecutionListener;
import org.cibseven.bpm.engine.delegate.TaskListener;
import org.cibseven.bpm.engine.impl.cmmn.behavior.HumanTaskActivityBehavior;
import org.cibseven.bpm.engine.impl.cmmn.model.CmmnActivity;
import org.cibseven.bpm.engine.impl.cmmn.transformer.AbstractCmmnTransformListener;
import org.cibseven.bpm.engine.impl.task.TaskDefinition;
import org.cibseven.bpm.model.cmmn.impl.instance.CasePlanModel;
import org.cibseven.bpm.model.cmmn.instance.CaseTask;
import org.cibseven.bpm.model.cmmn.instance.EventListener;
import org.cibseven.bpm.model.cmmn.instance.HumanTask;
import org.cibseven.bpm.model.cmmn.instance.Milestone;
import org.cibseven.bpm.model.cmmn.instance.PlanItem;
import org.cibseven.bpm.model.cmmn.instance.ProcessTask;
import org.cibseven.bpm.model.cmmn.instance.Stage;

import java.util.Arrays;
import java.util.List;

import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.CLOSE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.COMPLETE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.CREATE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.DISABLE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.ENABLE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.EXIT;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.MANUAL_START;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.OCCUR;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.PARENT_RESUME;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.PARENT_SUSPEND;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.PARENT_TERMINATE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.RESUME;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.RE_ACTIVATE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.RE_ENABLE;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.START;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.SUSPEND;
import static org.cibseven.bpm.engine.delegate.CaseExecutionListener.TERMINATE;

public class RegisterAllCmmnTransformListener extends AbstractCmmnTransformListener {

  static final List<String> TASK_EVENTS = RegisterAllBpmnParseListener.TASK_EVENTS;
  static final List<String> LIFECYCLE_EVENTS = Arrays.asList(
    CREATE,
    ENABLE,
    DISABLE,
    RE_ENABLE,
    START,
    MANUAL_START,
    COMPLETE,
    RE_ACTIVATE,
    TERMINATE,
    EXIT,
    PARENT_TERMINATE,
    SUSPEND,
    RESUME,
    PARENT_SUSPEND,
    PARENT_RESUME,
    CLOSE,
    OCCUR
    );

  private final CaseExecutionListener caseExecutionListener;
  private final TaskListener taskListener;

  public RegisterAllCmmnTransformListener(final TaskListener taskListener, final CaseExecutionListener caseExecutionListener) {
    this.taskListener = taskListener;
    this.caseExecutionListener = caseExecutionListener;
  }

  @Override
  public void transformHumanTask(PlanItem planItem, HumanTask humanTask, CmmnActivity activity) {
    addDelegateListener(activity);
    addTaskListener(taskDefinition(activity));
  }

  @Override
  public void transformProcessTask(PlanItem planItem, ProcessTask processTask, CmmnActivity activity) {
    addDelegateListener(activity);
  }

  @Override
  public void transformCaseTask(PlanItem planItem, CaseTask caseTask, CmmnActivity activity) {
    addDelegateListener(activity);
  }

  @Override
  public void transformStage(PlanItem planItem, Stage stage, CmmnActivity activity) {
    addDelegateListener(activity);
  }

  @Override
  public void transformMilestone(PlanItem planItem, Milestone milestone, CmmnActivity activity) {
    addDelegateListener(activity);
  }

  @Override
  public void transformEventListener(PlanItem planItem, EventListener eventListener, CmmnActivity activity) {
    addDelegateListener(activity);
  }

  @Override
  public void transformCasePlanModel(CasePlanModel casePlanModel, CmmnActivity activity) {
    addDelegateListener(activity);
  }


  void addDelegateListener(final CmmnActivity activity) {
    for(String event: LIFECYCLE_EVENTS) {
      activity.addListener(event, caseExecutionListener);
    }
  }

  void addTaskListener(TaskDefinition taskDefinition) {
    for (String event : TASK_EVENTS) {
      taskDefinition.addTaskListener(event, taskListener);
    }
  }

  /**
   * @param activity the taskActivity
   * @return taskDefinition for activity
   */
  static TaskDefinition taskDefinition(final CmmnActivity activity) {
    final HumanTaskActivityBehavior activityBehavior = (HumanTaskActivityBehavior) activity.getActivityBehavior();
    return activityBehavior.getTaskDefinition();
  }
}
