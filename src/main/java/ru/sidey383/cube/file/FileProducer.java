package ru.sidey383.cube.file;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadPoolExecutor;

public interface FileProducer {

    void draw(File f) throws IOException;

    static void printStatus(ThreadPoolExecutor pool) {
        Instant start = Instant.now();
        while (true) {
            int activeCount = pool.getActiveCount();
            long taskCount = pool.getTaskCount();
            long completedTaskCount = pool.getCompletedTaskCount();
            long tasksToDo = taskCount - completedTaskCount - activeCount;
            Duration duration = Duration.between(start, Instant.now());
            System.out.printf("%s: TaskCount %d Run %d Completed %d Await %d\n",
                    duration.getSeconds() + "." + duration.toMillisPart(),
                    taskCount,
                    activeCount,
                    completedTaskCount,
                    tasksToDo);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Status print stop");
                return;
            }
        }
    }

}
