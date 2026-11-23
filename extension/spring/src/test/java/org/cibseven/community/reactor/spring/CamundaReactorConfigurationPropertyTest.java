package org.cibseven.community.reactor.spring;

import org.cibseven.community.reactor.plugin.ReactorProcessEnginePlugin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CamundaReactorConfiguration.class)
@TestPropertySource(properties = "camunda.bpm.reactor.reactor-listener-first-on-user-task=true")
public class CamundaReactorConfigurationPropertyTest {

  @Autowired
  private ReactorProcessEnginePlugin plugin;

  @Test
  public void reactor_listener_first_on_user_task_is_read_from_property() {
    assertThat(plugin.isReactorListenerFirstOnUserTask()).isTrue();
  }
}
