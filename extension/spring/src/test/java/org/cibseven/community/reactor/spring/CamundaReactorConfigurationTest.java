package org.cibseven.community.reactor.spring;

import org.cibseven.community.reactor.bus.CamundaEventBus;
import org.cibseven.community.reactor.plugin.ReactorProcessEnginePlugin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CamundaReactorConfiguration.class)
public class CamundaReactorConfigurationTest {

  @Autowired
  private CamundaEventBus bus;

  @Autowired
  private ReactorProcessEnginePlugin plugin;

  @Test
  public void inject_sucessfully() {
    assertThat(plugin.getEventBus()).isEqualTo(bus);
  }

  @Test
  public void reactor_listener_first_on_user_task_defaults_to_false() {
    assertThat(plugin.isReactorListenerFirstOnUserTask()).isFalse();
  }
}
