package org.cibseven.community.example.reactor;

import org.cibseven.bpm.engine.ProcessEngine;
import org.cibseven.bpm.engine.ProcessEngineConfiguration;
import org.cibseven.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.cibseven.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.cibseven.community.reactor.CamundaReactor;
import org.cibseven.community.reactor.bus.CamundaEventBus;

public class Setup {

  private static ProcessEngineConfiguration CONFIGURATION = new StandaloneInMemProcessEngineConfiguration() {
    {
      this.databaseSchemaUpdate = DB_SCHEMA_UPDATE_DROP_CREATE;
      this.getProcessEnginePlugins().add(CamundaReactor.plugin());
      this.jobExecutorActivate = false;
      this.isDbMetricsReporterActivate = false;
      this.idGenerator = new StrongUuidGenerator(); // should be used for production
      this.enforceHistoryTimeToLive = false;
    }
  };

  private static ProcessEngine processEngine;

  public static ProcessEngine processEngine() {
    if (processEngine == null) {
      processEngine = CONFIGURATION.buildProcessEngine();
    }
    return processEngine;
  }

  public static void init() {
    CamundaEventBus eventBus = CamundaReactor.eventBus();
    // create and register listeners
    eventBus.register(new KpiExecutionListener());
  }

}
