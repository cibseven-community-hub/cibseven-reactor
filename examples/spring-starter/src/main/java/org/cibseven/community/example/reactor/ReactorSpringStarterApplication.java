package org.cibseven.community.example.reactor;

import org.cibseven.bpm.engine.delegate.DelegateTask;
import org.cibseven.bpm.engine.delegate.TaskListener;
import org.cibseven.community.reactor.bus.CamundaSelector;
import org.cibseven.community.reactor.spring.listener.ReactorTaskListener;
import org.cibseven.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableProcessApplication
public class ReactorSpringStarterApplication {

  public static void main(final String... args) {
    SpringApplication.run(ReactorSpringStarterApplication.class, args);
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
