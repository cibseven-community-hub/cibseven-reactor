package org.camunda.bpm.extension.example.reactor;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.camunda.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.camunda.bpm.extension.reactor.spring.listener.ReactorTaskListener;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableProcessApplication
public class ReactorSpringStarterApplication {

  public static void main(final String... args) {
    SpringApplication.run(ReactorSpringStarterApplication.class, args);
  }

  @Bean(name = "camundaEventBus")
  public CamundaEventBus camundaEventBus() {
    return new CamundaEventBus();
  }
  
  @Bean(name = "plugin")
  public ReactorProcessEnginePlugin reactorProcessEnginePlugin(CamundaEventBus camundaEventBus) {
	  return new ReactorProcessEnginePlugin(camundaEventBus);
  }
  
  @Component
  @CamundaSelector(event = TaskListener.EVENTNAME_CREATE)
  public static class MyTaskCreateListener extends ReactorTaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
      delegateTask.setAssignee("foo");
    }
  }

}
