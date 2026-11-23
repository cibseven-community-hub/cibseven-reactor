package org.cibseven.community.reactor.spring;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(CamundaReactorConfiguration.class)
@EnableConfigurationProperties(CamundaReactorConfigProperties.class)
public class CamundaReactorAutoConfiguration {

  // empty

}
