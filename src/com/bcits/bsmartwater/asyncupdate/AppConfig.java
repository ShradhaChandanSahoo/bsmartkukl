package com.bcits.bsmartwater.asyncupdate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@ComponentScan("com.bcits.bsmartwater.asyncupdate")
public class AppConfig {

}

