package com.binisoftware.jobrunr;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessScheduler {

    private final ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        JobRunr.configure()
                .useJobActivator(applicationContext::getBean)
                .useStorageProvider(new InMemoryStorageProvider())
                .useBackgroundJobServer()
                .useDashboard()
                .initialize();
        test1();
    }
    @Job(name = "Job for Test1")
    public void test1() {
        BackgroundJob.scheduleRecurrently(Cron.every15seconds(), () -> System.out.println("Easy!"));
    }

}
