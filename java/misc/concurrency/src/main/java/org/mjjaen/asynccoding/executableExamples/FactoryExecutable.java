package org.mjjaen.asynccoding.executableExamples;

public class FactoryExecutable {
    public static Executable createExecutableExample(ExecutableType executableType) {
        switch (executableType) {
            case FIBONACCI:
                return new FibonacciExample();
            case THREAD:
                return new ThreadExample();
            case THREAD_LOCAL:
                return new ThreadLocalExample();
            case RUNNABLE:
                return new RunnableExample();
            case CALLABLE:
                return new CallableExample();
            case SEMAPHORE:
                return new SemaphoreExample();
            case FUTURE:
                return new FutureExample();
            case EXECUTOR:
                return new ExecutorExample();
            case COMPLETABLE_FUTURE:
                return new CompletableFutureExample();
            default:
                throw new IllegalArgumentException("Example not allowed");
        }
    }
}