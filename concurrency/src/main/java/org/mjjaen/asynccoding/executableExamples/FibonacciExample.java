package org.mjjaen.asynccoding.executableExamples;

import org.mjjaen.asynccoding.businessObject.FibonacciForkJoin;
import org.mjjaen.asynccoding.businessObject.FibonacciForkJoinCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class FibonacciExample implements Executable {
    @Override
    public void execute(Object o) {
        Integer number = null;
        try {
            number = (Integer)o;
        } catch (Exception e) {
            System.out.println(o + " can't be converted into Integer.");
        }
        calculateFibonacciWithNoOptimizations(number);
        calculateFibonacciCacheOptimized(number);
        calculateFibonacciForkJoin(number);
        calculateFibonacciForkJoinCacheOptimized(number);
    }

    private void calculateFibonacciWithNoOptimizations(Integer number) {
        Long initialTime = System.currentTimeMillis();
        Long result = calculateFibonacci(number);
        System.out.println("Fibonacci with no optimizations: the factorial of " + number + " is " + result);
        Long endTime = System.currentTimeMillis();
        System.out.println("Fibonacci with no optimizationsd: time needed to execute the task: " + (endTime - initialTime) + " ms");
    }

    private Long calculateFibonacci(Integer number) {
        if(number <= 1)
            return 1L;
        else {
            Long f1 = calculateFibonacci(number - 1);
            Long f2 = calculateFibonacci(number - 2);
            return f1 + f2;
        }
    }

    private void calculateFibonacciCacheOptimized(Integer number) {
        Long initialTime = System.currentTimeMillis();
        HashMap<Integer, Long> intermediateCalculations = new HashMap<Integer, Long>();
        Long result = calculateFibonacciCacheOptimized(number, intermediateCalculations);
        System.out.println("Fibonacci with cache optimizations: the factorial of " + number + " is " + result);
        Long endTime = System.currentTimeMillis();
        System.out.println("Fibonacci with cache optimizations: time needed to execute the task: " + (endTime - initialTime) + " ms");
    }

    private Long calculateFibonacciCacheOptimized(Integer number, Map<Integer, Long> intermediateResults) {
        if(number <= 1)
            return 1L;
        else {
            Long f1, f2 = null;
            if(intermediateResults.containsKey(number - 1))
                f1 = intermediateResults.get(number - 1);
            else {
                f1 = calculateFibonacciCacheOptimized(number - 1, intermediateResults);
                intermediateResults.put(number - 1, f1);
            }
            if(intermediateResults.containsKey(number - 2))
                f2 = intermediateResults.get(number - 2);
            else {
                f2 = calculateFibonacciCacheOptimized(number - 2, intermediateResults);
                intermediateResults.put(number - 2, f2);
            }
            return f1 + f2;
        }
    }

    private void calculateFibonacciForkJoin(Integer number) {
        FibonacciForkJoin fibonacciForkJoin = new FibonacciForkJoin(number, false);
        Long initialTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(fibonacciForkJoin);
        System.out.println("Fibonacci fork-join method: the factorial of " + number + " is " + result);
        Long endTime = System.currentTimeMillis();
        System.out.println("Gibonacci fork-join method: time needed to execute the task: " + (endTime - initialTime) + " ms");
    }

    private void calculateFibonacciForkJoinCacheOptimized(Integer number) {
        FibonacciForkJoinCache fibonacciForkJoinCache = new FibonacciForkJoinCache(number, false);
        Long initialTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(fibonacciForkJoinCache);
        System.out.println("Fibonacci fork-join with cache method: the factorial of " + number + " is " + result);
        Long endTime = System.currentTimeMillis();
        System.out.println("Gibonacci fork-join wich cache method: time needed to execute the task: " + (endTime - initialTime) + " ms");
    }
}
