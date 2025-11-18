package org.cibseven.community.reactor.spring;

import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.plugin.ReactorProcessEnginePlugin;
import org.cibseven.community.reactor.projectreactor.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaReactorConfiguration {

  @Bean
  public CamundaEventBus camundaEventBus() {
    return new CamundaEventBus();
  }

  @Bean
  @Qualifier("camunda")
  public EventBus eventBus(final CamundaEventBus camundaEventBus) {
    return camundaEventBus.get();
  }

  @Bean
  public ReactorProcessEnginePlugin reactorProcessEnginePlugin(final CamundaEventBus camundaEventBus,
        @Value("${camunda.bpm.reactor.reactor-listener-first-on-user-task ?: false}") final boolean reactorListenerFirstOnUserTask) {
    return new ReactorProcessEnginePlugin(camundaEventBus, reactorListenerFirstOnUserTask);
  }

}
