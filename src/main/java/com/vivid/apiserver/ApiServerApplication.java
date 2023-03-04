package com.vivid.apiserver;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


//@EnableCaching
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10800)
//@EnableBatchProcessing
//@EnableScheduling
//@EnableAspectJAutoProxy
@EnableFeignClients
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
