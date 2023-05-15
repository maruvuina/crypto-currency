package com.idfinance.lab.cryptocurrency.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "cryptocurrency.schedule.enabled", matchIfMissing = true)
public class SchedulingConfiguration {
}
