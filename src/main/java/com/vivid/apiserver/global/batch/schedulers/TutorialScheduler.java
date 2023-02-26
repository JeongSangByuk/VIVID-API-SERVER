package com.vivid.apiserver.global.batch.schedulers;

//@Component
//@RequiredArgsConstructor
//public class TutorialScheduler {
//
//    private final Job job;  // tutorialJob
//    private final JobLauncher jobLauncher;
//
//
//    // 5초마다 실행
//    @Scheduled(fixedDelay = 2 * 1000L)
//    public void executeJob () {
//        try {
//            jobLauncher.run(
//                    job,
//                    new JobParametersBuilder()
//                            .addString("datetime", LocalDateTime.now().toString())
//                            .toJobParameters()  // job parameter 설정
//            );
//        } catch (JobExecutionException ex) {
//        }
//    }
//
//
//}
