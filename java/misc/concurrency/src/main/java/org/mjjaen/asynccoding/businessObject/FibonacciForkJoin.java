package org.mjjaen.asynccoding.businessObject;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

@Slf4j
public class FibonacciForkJoin extends RecursiveTask<Long> {
    private static final int SEQUENTIAL_THRESHOLD = 30;
    final Integer number;
    final boolean printLogs;

    public FibonacciForkJoin(Integer number, boolean printLogs) {
        this.number = number;
        this.printLogs = printLogs;
    }

    @Override
    protected Long compute() {
        if(this.printLogs)
            log.info("Fibonacci of: " + number);
        if (number <= 1)
            return 1L;
        else {
            if (number <= SEQUENTIAL_THRESHOLD) {
                 return computeDirectly(number);
            } else {
                FibonacciForkJoin f1 = new FibonacciForkJoin(number - 1, this.printLogs);
                f1.fork();
                FibonacciForkJoin f2 = new FibonacciForkJoin(number - 2, this.printLogs);
                return f2.compute() + f1.join();
            }
        }
    }

    private Long computeDirectly(Integer number) {
        if(number <= 1)
            return 1L;
        else {
            Long f1 = computeDirectly(number - 1);
            Long f2 = computeDirectly(number - 2);
            return f1 + f2;
        }
    }
}
