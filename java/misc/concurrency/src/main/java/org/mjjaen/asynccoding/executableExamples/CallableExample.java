package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CallableExample implements Executable, Callable<Integer> {
    private final Integer number = 20;
    @Override
    public void execute(Object obect) {
        Callable<Integer> callableObj = new CallableExample();
        ExecutorService service =  Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(callableObj);
        Integer result=0;
        try {
            result = future.get();
            log.info("Result of the action: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Callable with lambda
        final Integer numberLambda = 20;
        Callable<Integer> callableObjLambda = () -> {
            return numberLambda * 4;
        };
        future = service.submit(callableObj);
        try {
            result = future.get();
            log.info("Result of the action: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    @Override
    public Integer call() throws Exception {
        Integer result = number * 4;
        return result;
    }
}
