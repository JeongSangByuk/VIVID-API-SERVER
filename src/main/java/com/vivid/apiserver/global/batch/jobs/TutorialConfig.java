package com.vivid.apiserver.global.batch.jobs;

//import com.chicplay.mediaserver.global.batch.tasklets.TutorialTasklet;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

//@Configuration
//@RequiredArgsConstructor
//public class TutorialConfig {
//
//    // Job 빌더 생성용
//    private final JobBuilderFactory jobBuilderFactory;
//
//    // Step 빌더 생성용
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job tutorialJob() {
//        return jobBuilderFactory.get("tutorialJob")
//                .start(tutorialStep())  // Step 설정
//                .build();
//    }
//
//    @Bean
//    public Step tutorialStep() {
//        return stepBuilderFactory.get("tutorialStep")
//                .tasklet(new TutorialTasklet()) // Tasklet 설정
//                .build();
//    }
//}
