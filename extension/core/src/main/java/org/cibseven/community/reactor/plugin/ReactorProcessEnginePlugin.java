package org.cibseven.community.reactor.plugin;

import org.cibseven.bpm.engine.ProcessEngine;
import org.cibseven.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.cibseven.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.cibseven.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.cibseven.bpm.engine.impl.cmmn.transformer.CmmnTransformListener;
import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.event.ProcessEnginePluginEvent;
import org.cibseven.community.reactor.plugin.parse.RegisterAllBpmnParseListener;
import org.cibseven.community.reactor.plugin.parse.RegisterAllCmmnTransformListener;

import java.util.ArrayList;
import java.util.List;

public class ReactorProcessEnginePlugin extends AbstractProcessEnginePlugin {

  private final CamundaEventBus eventBus;

  private boolean reactorListenerFirstOnUserTask = false;

  public ReactorProcessEnginePlugin(final CamundaEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public ReactorProcessEnginePlugin(final CamundaEventBus eventBus, final boolean reactorListenerFirstOnUserTask) {
    this.eventBus = eventBus;
    this.reactorListenerFirstOnUserTask = reactorListenerFirstOnUserTask;
  }

  public ReactorProcessEnginePlugin enableReactorListenerFirstOnUserTask() {
    reactorListenerFirstOnUserTask = true;
    return this;
  }

  @Override
  public void preInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {

    customPreBPMNParseListeners(processEngineConfiguration)
      .add(new RegisterAllBpmnParseListener(eventBus.getTaskListener(), eventBus.getExecutionListener(), reactorListenerFirstOnUserTask));

    customPreCMMNTransformListeners(processEngineConfiguration).add(
      new RegisterAllCmmnTransformListener(eventBus.getTaskListener(), eventBus.getCaseExecutionListener()));

    eventBus.notify(ProcessEnginePluginEvent.preInit(processEngineConfiguration));
  }

  @Override
  public void postInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {
    eventBus.notify(ProcessEnginePluginEvent.postInit(processEngineConfiguration));
  }

  @Override
  public void postProcessEngineBuild(final ProcessEngine processEngine) {
    eventBus.notify(ProcessEnginePluginEvent.postProcessEngineBuild(processEngine));
  }

  public CamundaEventBus getEventBus() {
    return eventBus;
  }

  private static List<BpmnParseListener> customPreBPMNParseListeners(final ProcessEngineConfigurationImpl processEngineConfiguration) {
    if (processEngineConfiguration.getCustomPreBPMNParseListeners() == null) {
      processEngineConfiguration.setCustomPreBPMNParseListeners(new ArrayList<>());
    }
    return processEngineConfiguration.getCustomPreBPMNParseListeners();
  }

  private static List<CmmnTransformListener> customPreCMMNTransformListeners(final ProcessEngineConfigurationImpl processEngineConfiguration) {
    if (processEngineConfiguration.getCustomPreCmmnTransformListeners() == null) {
      processEngineConfiguration.setCustomPreCmmnTransformListeners(new ArrayList<>());
    }
    return processEngineConfiguration.getCustomPreCmmnTransformListeners();
  }
}
