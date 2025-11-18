package org.cibseven.bpm.extension.example.reactor;

import org.cibseven.bpm.engine.delegate.DelegateTask;
import org.cibseven.bpm.engine.delegate.TaskListener;
import org.cibseven.bpm.extension.reactor.bus.CamundaSelector;
import org.cibseven.bpm.extension.reactor.spring.CamundaReactorConfiguration;
import org.cibseven.bpm.extension.reactor.spring.EnableCamundaEventBus;
import org.cibseven.bpm.extension.reactor.spring.listener.ReactorTaskListener;
import org.cibseven.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableProcessApplication
@EnableCamundaEventBus
public class ReactorSpringApplication {

  public static void main(final String... args) {
    SpringApplication.run(ReactorSpringApplication.class, args);
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
