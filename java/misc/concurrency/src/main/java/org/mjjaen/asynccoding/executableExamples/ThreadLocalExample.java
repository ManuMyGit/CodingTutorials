package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.asynccoding.businessObject.ThreadSafeDateFormatter;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadLocalExample implements Executable {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Override
    public void execute(Object obect) {
        try {
            for (int i = 0; i < 1000; i++) {
                final int id = i;
                threadPool.submit(() -> {
                    Date birthDate = birthDateFromDB(id);
                    SimpleDateFormat sdf = ThreadSafeDateFormatter.dateFormatThreadLocal.get();
                    log.info("Birthdate (cont = " + id + "): " + sdf.format(birthDate));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private Date birthDateFromDB(int i) {
        Date date = new Date();
        return Date.from(date.toInstant().plus(i, ChronoUnit.DAYS));
    }
}
