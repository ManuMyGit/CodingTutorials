package org.mjjaen.asynccoding.businessObject;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class FibonacciForkJoinCache extends RecursiveTask<Long> {
    private static final int SEQUENTIAL_THRESHOLD = 30;
    private static final ConcurrentMap<Integer, Long> intermediateCalculations = new ConcurrentHashMap<>();
    final Integer number;
    final boolean printLogs;

    public FibonacciForkJoinCache(Integer number, boolean printLogs) {
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
                FibonacciForkJoinCache f1 = new FibonacciForkJoinCache(number - 1, this.printLogs);
                if(intermediateCalculations.containsKey(number - 1)) {
                    f1.complete(intermediateCalculations.get(number - 1));
                } else{
                    f1.fork();
                }
                FibonacciForkJoinCache f2 = new FibonacciForkJoinCache(number - 2, this.printLogs);
                if(intermediateCalculations.containsKey(number - 2)) {
                    f2.complete(intermediateCalculations.get(number - 2));
                } else{
                    f2.fork();
                }
                Long result = f2.compute() + f1.join();
                intermediateCalculations.putIfAbsent(number, result);
                return result;
            }
        }
    }

    private Long computeDirectly(Integer number) {
        if(number <= 1)
            return 1L;
        else {
            Long f1, f2 = null;
            if(intermediateCalculations.containsKey(number - 1))
                f1 = intermediateCalculations.get(number - 1);
            else {
                f1 = computeDirectly(number - 1);
                intermediateCalculations.put(number - 1, f1);
            }
            if(intermediateCalculations.containsKey(number - 2))
                f2 = intermediateCalculations.get(number - 2);
            else {
                f2 = computeDirectly(number - 2);
                intermediateCalculations.put(number - 2, f2);
            }
            return f1 + f2;
        }
    }
}
