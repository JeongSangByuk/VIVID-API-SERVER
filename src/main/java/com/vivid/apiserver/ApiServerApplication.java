package com.vivid.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


//@EnableCaching
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10800)
//@EnableBatchProcessing
//@EnableScheduling
//@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class ApiServerApplication {

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

}
