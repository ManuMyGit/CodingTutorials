package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class CompletableFutureExample implements Executable {
    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Override
    public void execute(Object object) {
        try {
            completableFutureAsFuture();
            basicCreationAndExecution();
            blockingConsumption();
            normalVsAsyncExecution();
            combiningFutures();
            futuresInParallel();
            handlingErrors();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
            ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS);
        }
    }

    private void completableFutureAsFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        threadPool.submit(() -> {
            completableFuture.complete("This is a basic example of CompletableFuture as Future.");
            return null;
        });
        String string = completableFuture.get();
        log.info("This message will be printed secondly because the get is a blocking operation.");
        log.info(string);
    }

    private void basicCreationAndExecution() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(100);
            log.info("Supply async method task");
            return "Heavy process";
        });
        completableFuture.thenAcceptAsync((string) -> {
            log.info("Completion of the future");
            log.info("Result of the process: " + string);
        });
    }

    public void blockingConsumption() throws ExecutionException, InterruptedException {
        log.info("Both get and join are blocking methods. However, the get method throws checked exceptions whereas the join method throws an unchecked exception in case the Future does not complete normally");
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(2000);
            return 1;
        });
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(2500);
            return 5;
        });
        log.info("Getting the result via blocking method get: " + completableFuture1.get());
        log.info("Getting the result via blocking method join: " + completableFuture2.join());
    }

    private void normalVsAsyncExecution() {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(100);
            log.info("Future created in the default executor, ForkJoinPool. The thread name refers to that pool.");
            return null;
        });
        CompletableFuture<Void> completableFutureAsync = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(100);
            log.info("Future created in a fixed FixedThreadPool. The thread name refers to that pool.");
            return null;
        }, threadPool);
        completableFuture.whenComplete((result, error) ->  log.info("Completable future in its final stage calling thenAccept, it is executed in the same thread its previous stage, ForkJoiPool."));
        completableFutureAsync.whenCompleteAsync((result, error) -> log.info("Completable future async in its final stage calling thenAccept, it is executed in the default executor, ForkJoiPool."), ForkJoinPool.commonPool());
    }

    private void combiningFutures() {
        //Then apply: gets an object (in this case Integer) and returns an object (in this case another Integer).
        CompletableFuture<Integer> completableFuture1 = create(2);
        completableFuture1.thenApply(result -> heavyTask(result + 1)).thenAccept(result -> log.info("Result of thenApply operation: " + result));
        //Then compose: gets an object (in this case Integer) and returns the unwrapped value of the completable future returned
        CompletableFuture<CompletableFuture<Integer>> completableFutureCompletableFuture = create(3).thenApply(result -> create(result + 1));
        completableFutureCompletableFuture.thenAccept(result -> log.info("Result of thenApply operation chaining two completable future (is not an object): " + result));
        CompletableFuture<Integer> completableFuture2 = create(3).thenCompose(result -> create(result + 1));
        completableFuture2.thenAccept(result -> log.info("Result of thenCompose operation chaining two completable future (it is an object, flattered result): " + result));

        //Then combine: it combines two futures, it waits until both are done, and it returns an object with the calculation we run over the two results
        CompletableFuture<Integer> completableFuture3 = create(4);
        CompletableFuture<Integer> completableFuture4 = create(5);
        completableFuture3.thenCombine(completableFuture4, Integer::sum).thenAccept(result -> log.info("Result of thenCombine operation: " + result));
    }

    private Integer heavyTask(Integer i) {
        CompletableFutureExample.sleep(500);
        return i * 2;
    }

    private CompletableFuture<Integer> create(Integer number) {
        return CompletableFuture.supplyAsync(() -> heavyTask(number));
    }

    private void futuresInParallel() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> "World");
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);
        combinedFuture.get();
        String result1 = completableFuture1.get();
        String result2 = completableFuture2.get();
        String result3 = completableFuture3.get();
        log.info("Get method of a allOff combined completable future doesn't return any value. We need to collect the values one by one");
        log.info("Result of the three futures executed in parallel by allOf: " + result1.concat(" ").concat(result2).concat(" ").concat(result3));
        log.info("There is another way of collection the values, by using CompletableFuture.join() and Java Streams API");
        String combined = Stream.of(completableFuture1, completableFuture2, completableFuture3).map(CompletableFuture::join).collect(Collectors.joining(" "));
        log.info("Result of the three futures executed in parallel collection the results with join and Streams API by allOf: " + result1.concat(" ").concat(result2).concat(" ").concat(result3));

        completableFuture1 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(1000);
            return "Hello";
        });
        completableFuture2 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(200);
            return "Beautiful";
        });
        completableFuture3 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(300);
            return "World";
        });
        CompletableFuture<Object> combinedFuture2 = CompletableFuture.anyOf(completableFuture1, completableFuture2, completableFuture3);
        String result = (String) combinedFuture2.get();
        log.info("Get method of a anyOf combined completable future return the value of the first future solved, in this case completableFuture2 since it has the shortest sleeping time");
        log.info("Result of the three futures executed in parallel by anyOf: " + result);
    }

    private void handlingErrors() throws ExecutionException, InterruptedException {
        final int age = -1;
        CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
            if(age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if(age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).exceptionally(ex -> { //The error will not be propagated further in the callback chain if we handle it once
            log.info("First way of handling errors: exceptionally method. Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });
        log.info("Maturity : " + maturityFuture.get());

        CompletableFuture<String> maturityFuture2 = CompletableFuture.supplyAsync(() -> {
            if(age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if(age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).handle((result, exception) -> {
            if(exception != null) {
                log.info("Second way of handling errors: handle method. Oops! We have an exception - " + exception.getMessage());
                return "Unknown!";
            }
            return result;
        });
        log.info("Maturity : " + maturityFuture2.get());

    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
