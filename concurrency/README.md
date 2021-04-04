# Introduction
Java Concurrency is a term that covers multithreading, concurrency and parallelism on the Java platform. That includes the Java concurrency tools, problems and solutions. Before getting down to business, let's review some basic concepts about multithreading.

Apart from that, in the examples section you'll be able to find the details of all examples developed in this tutorial. If you want to run it, checks the logs to see the different threads involved in each example.

# Multithreading
Multithreading means that you have multiple threads of execution inside the same application. A thread is like a separate CPU executing your application. Thus, a multithreaded application is like an application that has multiple CPUs executing different parts of the code at the same time. A thread is not equal to a CPU though. Usually a single CPU will share its execution time among multiple threads, switching between executing each of the threads for a given amount of time. It is also possible to have the threads of an application be executed by different CPUs.

## Single core vs multiple cores
Back in the old days a computer had a single CPU, and was only capable of executing a single program at a time. Later came multitasking which meant that computers could execute multiple programs/tasks/processes at the same time. It wasn't really "at the same time" though. The single CPU was shared between the programs. The operating system would switch between the programs running, executing each of them for a little while before switching. Therefore, concurrency is only a generalized approximation of real parallel execution in a single-core processor.

However, CPUs with multiple cores are physically able to run multiple tasks at the same time, as many one as cores available. This leads to the so called parallel execution or multithreading.

## Thread
A thread is a unit of execution on concurrent programming. Each thread can only do a single task at once. Multithreading is a technique which allows a CPU to execute many tasks of one process at the same time. These threads can execute individually while sharing theirs resources.

## Multithreading
In parallel execution or multithreading, the tasks to be performed by a process are broken down into sub-parts, and multiple CPUs (or multiple cores) process each sub-task at precisely the same time. Therefore, parallelism is the real way in which multiple tasks can be processed at the same time.

For example, most modern CPUs support multithreading. A simple app on our smartphone can give you a live demo of the same. When you open an app that requires some data to be fetched from the internet, the content area of the app is replaced by a spinner. This will rotates until the data is fetched and displayed. In the background, there are two threads:
- One fetching the data from a network, and
- One rendering the GUI that displays the spinner

To help understand the difference between this two concepts, let's define both of them:
- CPU cores mean the actual hardware component.
- Threads refer to the virtual component which manages the tasks.

## Hyper-Threading
Hyper-threading was Intel's first effort to bring parallel computation to end user's PCs. A single CPU with hyper-threading appears as two logical CPUs for an operating system. In this case, the CPU is single, but the OS considers two CPUs for each core, and CPU hardware has a single set of execution resources for every CPU core. Therefore, CPU assumes as it has multiple cores than it does, and the operating system assumes two CPUs for each single CPU core.

For instance a processor with 6 CPU cores will have 12 logical cores at Hyper-Threading, which means 12 "CPUs" available to run tasks at the same time.

# Synchronous vs asynchronous code
Normally, a given program's code runs straight along, with only one thing happening at once. If a function relies on the result of another function, it has to wait for the other function to finish and return, and until that happens, the entire program is essentially stopped from the perspective of the user.

There's no sense sitting there waiting for something when you could let the other task chug along on another processor core and let you know when it's done. This lets you get other work done in the meantime, which is the basis of asynchronous programming.

Asynchronous techniques are very useful, particularly in web programming. When a web app runs in a browser and it executes an intensive chunk of code without returning control to the browser, the browser can appear to be frozen. This is called blocking; the browser is blocked from continuing to handle user input and perform other tasks until the web app returns control of the processor.

# Java Concurrency
## Concurrent interface
The java.util.concurrent package provides tools for creating concurrent applications. One of the main tools or utilities provided by this interface are:
 - Executor:  is an interface that represents an object that executes provided tasks.
 - ExecutorService: is a complete solution for asynchronous processing. It manages an in-memory queue and schedules submitted tasks based on thread availability.
 - ScheduledExecutorService: is a similar interface to ExecutorService, but it can perform tasks periodically. Executor and ExecutorService‘s methods are scheduled on the spot without introducing any artificial delay.
 - Future: is used to represent the result of an asynchronous operation
 - Semaphore: is used for blocking thread level access to some part of the physical or logical resource.
 - ThreadFactory: acts as a thread (non-existing) pool which creates a new thread on demand. It eliminates the need of a lot of boilerplate coding for implementing efficient thread creation mechanisms.

## Thread
The very basic yet so powerful component of Java concurrency is `Thread`. The Thread of Java is actually associated with the thread of the Operating System. The very basic way to create a Thread is by extending it and overriding the `run` method.

```java
public class TestThread extends Thread {
    @Override
    public void run() {
        ...
    }
}
TestThread t = new TestThread();
// starts thread
t.start();
```

Starting the thread causes the `run()` method to be called. Some things to have into account:
 - Once we extend the Thread class, the extending class losses its ability to extend further as Java does not support multiple inheritances.
 - Each thread has its own object when we extend it, and it's not good for memory health when there are tons of Objects of the extended Thread created.

Java addresses these issues with the Runnable interface. In fact, Thread has an overloaded method that takes Runnable.

### Lifecycle of a thread
The java.lang.Thread class contains a static State enum – which defines its potential states. During any given point of time, the thread can only be in one of these states:
- NEW – a newly created thread that has not yet started the execution.
- RUNNABLE – either running or ready for execution but it's waiting for resource allocation.
- BLOCKED – waiting to acquire a monitor lock to enter or re-enter a synchronized block/method.
- WAITING – waiting for some other thread to perform a particular action without any time limit.
- TIMED_WAITING – waiting for some other thread to perform a specific action for a specified period.
- TERMINATED – has completed its execution.

A NEW Thread (or a Born Thread) is a thread that's been created but not yet started. It remains in this state until we start it using the start() method:

```java
Runnable runnable = new NewState();
Thread t = new Thread(runnable);
Log.info(t.getState()); //NEW
```

When we've created a new thread and called the start() method on that, it's moved from NEW to RUNNABLE state. In a multi-threaded environment, the Thread-Scheduler (which is part of JVM) allocates a fixed amount of time to each thread. So it runs for a particular amount of time, then relinquishes the control to other RUNNABLE threads.

```java
Runnable runnable = new NewState();
Thread t = new Thread(runnable);
t.start();
Log.info(t.getState()); //RUNNABLE
```

A thread is in the BLOCKED state when it's currently not eligible to run. For instance, if we create two threads of a particular class with a synchronized infinite loop, if we do t1.start() and t2.start() the thread 2 will remain in state BLOCKED since t1 is using the infinite loop exclusively thanks to the synchronized keyword.

A thread is in WAITING state when it's waiting for some other thread to perform a particular action. According to JavaDocs, any thread can enter this state by calling any one of the following three methods:
- object.wait()
- thread.join()
- LockSupport.park()

## ThreadLocal
Let's assume we have the following code:

```java
public class User {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    public static void main(String [] args) throws InterruptedException {
        for(int i = 0; i < 1000; i ++) {
            int id = i;
            threadPool.submit(() -> {
                String birthDate = new User().birthDate(id);
        System.out.println(birthDate);
            }
        }
    }
    public String birthDate(int id) {
        Date birthDate = birthDateFromDB(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(birthDate);
    }
}
```

Let's analyze the code because there are some objects we haven't explained yet:
 - threadPool: this object represents a pool of available threads. We could've create one thread per operation, but since the loop has 1000 iterations, it would've implied 1000 threads, which is a memory killer operation.
 - threadPool.submit: what this method does is to send the operation to the pool. If there is an available thread, the operation will be performed by it. Otherwise, it'll wait until there is a thread free.

What does it mean? Well, we have created 1000 tasks which will be executed by 10 threads. But each of the tasks has a specific copy of the SimpleDateFormat object. What a waste of memory!

One of the ways to solve it could be to create the SimpleDateFormat as a static object. That way, there would be just one copy of the SimpleDateFormat object across the 1000 tasks. But this way is not thread-safe, so we could face concurrency issues among the threads. So, we neither can use 1 task per object because even though it is safe it is extremely inefficient nor we can use a single instance of the SimpleDateFormat object because it is safe.

If we think about the atomicity of the problem, since we can have just 10 threads running in parallel, what we need is precisely 10 copies of the SimpleDateFormat, 1 per thread. This is the definition of ThreadLocal, to be able to create per-thread instances for memory efficiency and thread-safety. This is how we could code this example by using ThreadLocal:

```java
class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
        @Override
        public SimpleDateFormat get() {
            return super.get();
        }
    };
}

public class User {
    ...
    public String birthDate(int id) {
        Date birthDate = birthDateFromDB(id);
        SimpleDateFormat sdf = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return sdf.format(birthDate);
    }
}
```

This is how the code works:
 - Every time the get() method is called, each thread will get its own instance of the date format object.
 - The first call to the get() method by any thread will trigger the internal call to the initialValue() method. So the initialValue() method will be called once per thread.
 - The subsequent calls to the get() method will return the same value calculated above.

With Java 8 we could reduce the creation of the ThreadLocal like this:

```java
public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
```

Let's see this another example:
 - Let's imagine that we have a web server. When the request gets to the server, it gets to the Service1. From Service1 it goes to Service2. Then to Service3 and finally to Service4. All of them need to know the user which triggered the request. How could we achieve this?

The first approximation would be to pass the user from service to service, which is very inconvenient. Another approximation could be to use a map, so the Service1 puts the user in the map so that the other services can get it. But, what if the server has more than one thread? Well, then we require locks or a concurrent HashMap to ensure that it's being accessed in a thread-safe matter.

However, if we use a ThreadLocal instead of a map, then based on which thread is actually calling the get, the ThreadLocal will return the user stored in the copy of the thread, because there is an instance of the ThreadLocal per thread. This could be the declaration of the context:

```java
public static ThreadLocal<User> holder = new ThreadLocal();
```

That means there is no need of data structure, there is no need of locks and there is no need of synchronization required. We need to ensure that when the object has been used, it is removed from the context. In our example of 4 services, when the Service4 is done with the request, it must remove the object from the context:

```java
UserContextHolder.holder.remove();
```

We can use ThreadLocal when we need thread-safety in a multi thread environment plus performance in the way the data is shared-accessed, to ensure the context for a particular thread.

Spring itself makes use of this concept of ThreadLocal and context holder in several classes. For instance, it uses the class RequestContextHolder to store any of the request attributes through the scope of a particular request, and a request is managed by a single thread.

## Runnable and Callable
Runnable is an interface that has only one method: run(). Though Runnable has a run() method, it's not a Thread, but just a Java class until it's taken control by (passed to) Thread. The starting of the thread causes the runnable object's run() method to be called. Here an example of Runnable:

```java
class MyThread implements Runnable {
    @Override
    public void run() {
        ...
    }
}
Thread t1 = new Thread(new DemoThreadB());
t1.start();
```

However, neither Thread nor Runnable:
- The so called runnable method doesn't return anything.
- It doesn't have exception handling. We have to surround our code that throws an Exception with try and catch block.

This was solved in Java 1.5 with `Callable<V>`. `Callable<V>` is a generic interface which returns a generic type value. Callable is too a functional interface (only 1 method) and call() is the only method, a no-argument method that throws Exception and returns generic type value. Here an example of Callable (we'll talk about Future in a bit):

```java
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "myValue";
    }
}

...
Callable<String> callable = new MyCallable();
Future<String> future = executor.submit(callable);
...
```

The main differences between Callable and Runnable are:
- The Runnable interface is a functional interface and has a single run() method which doesn't accept any parameters and does not return any values. This is suitable for situations where we are not looking for a result of the thread execution, for example, incoming events logging.
- The Callable interface is a generic interface containing a single call() method – which returns a generic value V. The result of call() method is returned within a Future object.
- For exception handling, with Runnable, since the method signature does not have the “throws” clause specified, there is no way to propagate further checked exceptions. However, Callable's call() method contains “throws Exception” clause so we can easily propagate checked exceptions further. In case of running a Callable using an ExecutorService (see Executors section), the exceptions are collected in the Future object, which can be checked by making a call to the Future.get() method. This will throw an ExecutionException – which wraps the original exception.

## Semaphore
Semaphores can be used to limit the number of concurrent threads accessing a specific resource, or to facilitate inter-thread communication like in producer-consumer kind of scenario. We can use them in Java thanks to the class Semaphore inside the package java.util.concurrent.

The semaphore controls the access to the shared resource through the use of a counter. If the counter is greater than zero, then access is allowed. If it is zero, then access is denied. What the counter is counting are permits that allow access to the shared resource. Thus, to access the resource, a thread must be granted a permit from the semaphore.

In general, to use a semaphore, the thread that wants access to the shared resource tries to acquire a permit.
- If the semaphore’s count is greater than zero, then the thread acquires a permit, which causes the semaphore’s count to be decremented.
- Otherwise, the thread will be blocked until a permit can be acquired.
- When the thread no longer needs an access to the shared resource, it releases the permit, which causes the semaphore’s count to be incremented.
- If there is another thread waiting for a permit, then that thread will acquire a permit at that time.

There are two constructors in Semaphore class:
- `Semaphore(int num)`: num specifies the initial permit count. Thus, it specifies the number of threads that can access a shared resource at any one time. If it is one, then only one thread can access the resource at any one time. By default, all waiting threads are granted a permit in an undefined order. When num = 1 a so called Binary Semaphore is created, which is used such that it only has at most one permit available, and can serve as a mutual exclusion lock.
- `Semaphore(int num, boolean fair)`: by setting how to true, we can ensure that waiting threads are granted a permit in the order in which they requested access.

To handle the permits we have the following methods available:
- `public void acquire()`: acquires a permit, if one is available and returns immediately, reducing the number of available permits by one. If no permit is available then the current thread becomes disabled for thread scheduling purposes and lies dormant until one of two things happens: some other thread invokes the release method for this semaphore and the current thread is next to be assigned a permit; or some other thread interrupts the current thread.
- `public void acquire(int permits)`: same as above but with n permits, instead just 1.
- `public void acquireUninterruptibly()`: acquires a permit, if one is available and returns immediately, reducing the number of available permits by one. If no permit is available then the current thread becomes disabled and lies dormant until a permit is available.
- `public void acquireUninterruptibly(int permits)`: same as above but with n permits, instead just 1.
- `public boolean tryAcquire()`: acquires a permit, if one is available and returns immediately, with the value true, reducing the number of available permits by one. If no permit is available then this method will return immediately with the value false.
- `public boolean tryAcquire(int permits)`: same as above but with n permits, instead just 1.
- `public boolean tryAcquire(long timeout, TimeUnit unit)`: acquires a permit, if one is available and returns immediately, with the value true, reducing the number of available permits by one. If no permit is available then current thread becomes disabled and lies dormant until one of three things happens: some other thread releases a permit; or some other thread interrupts this thread; or the specified waiting time elapses.
- `public boolean tryAcquire(int permits, long timeout, TimeUnit unit)`: same as above but with n permits, instead just 1.
- `public void release()`: releases a permit, returning it to the semaphore
- `public void release(int permits)`: release n permits, returning them to the semaphore.
- `public int availablePermits()`: return the number of permits available.
- `public int drainPermits()`: acquires and returns all permits that are immediately available.
- `public boolean isFair()`: return whether or not the semaphore is fair (first-in first-out).
- `public final boolean hasQueuedThreads()`: queries whether any threads are waiting to acquire.
- `public final int getQueueLength()`: returns an estimate of the number of threads waiting to acquire

## Future
Future is an interface that's been around since Java 1.5 and can be quite useful when working with asynchronous calls and concurrent processing. Simply put, the Future class represents a future result of an asynchronous computation – a result that will eventually appear in the Future after the processing is complete. Long running methods are good candidates for asynchronous processing and the Future interface. This enables us to execute some other process while we are waiting for the task encapsulated in Future to complete.

This interface has five methods:
 - `boolean cancel(boolean mayInterruptIfRunning)`: attempts to cancel execution of this task.  This attempt will fail if the task has already completed, has already been cancelled, or could not be cancelled for some other reason. If successful, and this task has not started when cancel() method is called, this task should never run.  If the task has already started, then the mayInterruptIfRunning parameter determines whether the thread executing this task should be interrupted in an attempt to stop the task. After this method returns, subsequent calls to the isDone() method will always return true. Subsequent calls to isCancelled() method will always return true if this method returned true.
 - `boolean isCancelled()`: returns true if this task was cancelled before it completed normally.
 - `boolean isDone()`: returns true if this task was completed. Completion may be due to normal termination, an exception, or cancellation.
 - `V get() throws InterruptedException, ExecutionException`: waits if necessary for the computation to complete, and then retrieves its result
 - `V get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException`: waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available.

## Threads management
As we've seen, it is very easy to run an asynchronous method in Java. The only thing we need to do is to create a Thread and to start it. What Java does is to have its main thread, business as usual, and to create a new thread to execute the asynchronous code and eventually to kill the thread when the it's done. We could extend this to create as many threads as we want. For instance, what if we want to run 1000 tasks asynchronously? We could create 1000 different threads to get the tasks executed. The problem is that in Java 1 threads corresponds to 1 OS thread. It means that if we create 1000 threads in Java, 1000 OS threads will be created, which is a really expensive operation.

Instead, what we need is a tool which as a set of available threads so the 1000 tasks can be submitted to that tool. Basically, what we need is a pool of threads (in the same way that we have a pool of connections to work with a database). To create a thread is an expensive operation, so what we get with the pool is to have a set of threads available (we'll discuss the strategies of the pool later) so we can get our tasks executed without killing the server. The tasks will be picked up by the available threads of the pool. Let's talk about the main features of a thread pool.

### Thread pool
As we said, a thread pool is a place where available threads for our application are stored. A pool has several parameters to be configured:
- Maximum pool size: the maximum number of threads to allow in the pool. This value depends on queue capacity since the thread pool will only create a new thread if the number of items in its queue exceeds queue capacity.
- Core pool size: the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set.
- Idle alive time: when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
- Work queue: the queue to use for holding tasks before they are executed.  This queue will hold only the Runnable tasks submitted by the execute method. It is helpful here to understand what a blocking queue and a synchronous queue are, because this is how the tasks are managed:
  - Blocking queue: it is a queue with producer threads and consumer threads. It is a thread-safe data structure which can handle multiple consumer threads and producer threads at the same time. When the queue is empty, if a consumer threads tries to get an element from the queue, it gets blocked until an element is available. When the queue is full, if a producer threads tries to put an element into the queue, it gets blocked until a gap in the queue is available.
  - Synchronous queue: a synchronous queue is a blocking queue of size "1". However, the put operation is slightly different. When the producer tries to put an element into the queue, it'll get blocked immediately even tough the queue is empty. This is because the queue will force the producer to be blocked until a consumer comes to get the available element, which is when the producer will get unblocked. Why did we say size "1" before? Because the size of the queue is really 0. There is a direct handoff between Producer and Consumer, the queue doesn't hold anything. This kind of queue doesn't have peek methods, iterate methods, ..., and are ideal for handoffs.

Java offers essentially the following pool types:
- Fixed thread pool. It is a pool with maximum pool size = minimum pool size and an unbounded queue to store all tasks. Since the queue never gets full, new threads are never created.
- Single thread pool. It is a pool with maximum pool size = minimum pool size = 1 an unbounded queue to store all tasks. Since the queue never gets full, new threads are never created.
- Cached thread pool. It is a pool with an unbounded number of threads. Due to that, there is no need to store the tasks, so a synchronous queue with a single slot is used.
- Schedule thread pool. Special queue that deals with delays and schedules.
- Custom. We can configure our own pools. A bounded queue is used to store the tasks. If queue gets full, new threads are created until reaching the maximum pool size.

The last step of a thread pool is its shut down. Java offers the following methods:
 - `ExecutorService.shutdown()`: it'll initiate the shutdown without forcing the tasks to finish. Both tasks in the pool and queued ones will be finished. No new tasks will be accepted. If a task is sent to the pool, the exception RejectionExecutionException will be thrown. For the ForkJoinPool pool obtained by calling the static ForkJoinPool.commonPool() method, its run state is unaffected by attempts to shutdown() or shutdownNow(). However this pool and any ongoing processing are automatically terminated upon program System.exit(int). Any program that relies on asynchronous task processing to complete before program termination should invoke commonPool().awaitQuiescence, before exit. This method must be called in a finally block to ensure it is always executed.
 - `ExecutorService.isShutdown()`: to check whether the shutdown has been initiated.
 - `ExecutorService.isTerminated()`: to check whether the shutdown has been completed.
 - `ExecutorService.awaitTermination(Integer time, TimeUnit unit)`: blocks until all tasks are completed or it timeout occurs.
 - `List<Runnable> ExecutorService.shutdownNow()`: will initiate the shutdown (all the tasks in the pool will get completed) and return all queued ones.

### Tasks rejection
What if the pool has the maximum number of threads available and the queue is full of pending tasks and another tasks gets to the queue? Then, the task can't be executed, the thread pool will reject the task. During the creation of the pool, we can configure the way the pool rejects the task:
 - Abort policy: submitting new tasks throws RejectedExecutionException (Runtime exception).
 - Discard policy: submitting new tasks silently discards it.
 - Discard oldest policy: submitting new tasks drops existing oldest tasks, and new task is added to the queue.
 - Caller runs policy: submitting the tasks will execute the task on the caller thread itself. This can create feedback loop where caller thread is busy expecting the task and cannot submit new tasks at fast pace.

### Executors, Executor and ExecutorService
The Executors helper class contains several methods for the creation of pre-configured thread pool instances for us. Those classes are a good place to start with – use it if you don't need to apply any custom fine-tuning. The Executor and ExecutorService interfaces are used to work with different thread pool implementations in Java. ExecutorService extends Executor. Usually, we should keep our code decoupled from the actual implementation of the thread pool and use these interfaces throughout our application. The Executor interface has a single execute method to submit Runnable instances for execution.

The pre-configured thread pools available in the Executors class are:
 - `newFixedThreadPool(int nThreads)`: creates a thread pool with a fixed number of threads, which means that core pool size = maximum pool size. Idle alive time = 0 and the queue used is a `LinkedBlockingQueue<Runnable>`.
 - `newWorkStealingPool()`: creates a work-stealing thread pool using all availableProcessors available processors as its target parallelism level. But this work-stealing mechanism is already used by ForkJoinPool and is highly useful when our task(s) spawn smaller tasks, which can be proactively picked up by any available thread, reducing the thread idle time. So, why is this different? In fact, it is not. If we check the code, we can see that it returns a ForkJoinPool with some configuration. This new pool adds one more gray area on when to use ExecutorService and when to use ForkJoinPool.
 - `newWorkStealingPool(int parallelism)`: same as above but with a specific parallelism instead of the available processors (Runtime.getRuntime().availableProcessors()).
 - `newFixedThreadPool(int nThreads, ThreadFactory threadFactory)`: creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue, using the provided ThreadFactory to create new threads when needed.
 - `newSingleThreadExecutor()`: creates a thread pool with a single thread.
 - `newSingleThreadExecutor(ThreadFactory threadFactory)`: creates an Executor that uses a single worker thread operating off an unbounded queue, and uses the provided ThreadFactory to create a new thread when needed.
 - `newCachedThreadPool()`: creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available.  These pools will typically improve the performance of programs that execute many short-lived asynchronous tasks. Calls to execute() will reuse previously constructed threads if available. If no existing thread is available, a new thread will be created and added to the pool. Threads that have not been used for sixty seconds are terminated and removed from the cache.
 - `newCachedThreadPool(ThreadFactory threadFactory)`: creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available, and uses the provided ThreadFactory to create new threads when needed.
 - `newSingleThreadScheduledExecutor()`: creates a single-threaded executor that can schedule commands to run after a given delay, or to execute periodically. Tasks are guaranteed to execute sequentially, and no more than one task will be active at any given time.
 - `newSingleThreadScheduledExecutor(ThreadFactory threadFactory)`: creates a single-threaded executor that can schedule commands to run after a given delay, or executed periodically, and uses the provided ThreadFactory to create a new thread when needed.
 - `newScheduledThreadPool(int corePoolSize)`: creates a thread pool that can schedule commands to run after a given delay, or to execute periodically with a fixed number of threads.
 - `newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory)`: creates a thread pool that can schedule commands to run after a given delay, or to execute periodically with a fixed number of threads, and uses the provided ThreadFactory to create a new thread when needed.

Apart from that, we can create our custom pool. To do that, we can use the `ThreadPoolExecutor` class. The thread factory is Executors.defaultThreadFactory(). This is the constructor and what we can configure:
 - `ExecutorService executorService = 
  new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,workQueue);`
 - Core pool size.
 - Maximum pool size.
 - Idle alive time.
 - Queue. It's an implementation of the `BlockingQueue<Runnable>` interface (such as new `LinkedBlockingQueue<Runnable>`).
 - By default, the rejection handler will throw RejectedExecutionException when both the pool and the queue are full. However, we can pass a custom exception handler (class which must implement RejectedExecutionHandler) to define the behavior for rejected tasks, such us login or other operations performed on rejection.

Eventually there are a few more util methods:
- `public static ExecutorService unconfigurableExecutorService(ExecutorService executor)`: returns an object that delegates all defined ExecutorService methods to the given executor, but not any other methods that might otherwise be accessible using casts. This provides a way to safely "freeze" configuration and disallow tuning of a given concrete implementation.
- `public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService executor)`: same as above but for a ScheduledExecutorService.
- `public static <T> Callable<T> callable(Runnable task, T result)`: Returns a Callable object that, when called, runs the given task and returns the given result.
- `public static Callable<Object> callable(Runnable task)`: returns a Callable object that, when called, runs the given task and returns null.

### Assigning tasks to the Executor Service
ExecutorService can execute Runnable and Callable tasks. We can assign tasks to the ExecutorService using several methods including execute(), which is inherited from the Executor interface, and also submit(), invokeAny() and invokeAll():
 - `executorService.execute(runnableTask)`: this method is void and doesn't give any possibility to get the result of a task's execution or to check the task's status (is it running).
 - `Future<String> future = executorService.submit(callableTask)`: this method submits a Callable or a Runnable task to an ExecutorService and returns a result of type Future.
 - `String result = executorService.invokeAny(callableTasks)`: this method assigns a collection of tasks to an ExecutorService, causing each to run, and returns the result of a successful execution of one task (if there was a successful execution).
 - `List<Future<String>> futures = executorService.invokeAll(callableTasks)`: this method assigns a collection of tasks to an ExecutorService, causing each to run, and returns the result of all task executions in the form of a list of objects of type Future:

### Thread Factory
The ThreadFactory interface defined in the java.util.concurrent package is based on the factory design pattern. As its name suggests, it is used to create new threads on demand. As we've seen, threads can be created creating a class that extends the Thread class or implements the Runnable interface. However, ThreadFactory is another choice to create new threads. This interface provides a factory method that creates and returns new threads when called. This factory method takes a Runnable object as an argument and creates a new thread using it.

Why use ThreadFactory? We could directly create threads from the Runnable commands by calling the Thread constructor that we did in the newThread(Runnable) method. Here are some reasons:
- We can give the threads more meaningful custom names. It helps in analyzing their purposes and how they work.
- We can have the statistics about the created threads like the count of threads and other details. We can restrict the creation of new threads based on the statistics.
- We can set the daemon status of threads.
- We can set the thread priority.
- We can have all the features confined in one class.

The Executors class has a static method called defaultThreadFactory(). This method returns the default thread factory used by many methods (like the ones seen before where no thread factory is passed). This default ThreadFactory creates all the new threads in the same ThreadGroup (a ThreadGroup represents a group of threads). All the created new threads are non-daemon with priority set to the smallest of Thread.NORM_PRIORITY and the maximum priority permitted in the ThreadGroup. The threads created by this default ThreadFactory are given names in the form of pool-N-thread-M (as examples, pool-1-thread-1, pool-1-thread-2, pool-2-thread-1 etc.) where N is the sequence number of this factory, and M is the sequence number of the threads created by this factory.

## Fork-join
The fork/join framework was presented in Java 7. It provides tools to help speed up parallel processing by attempting to use all available processor cores – which is accomplished through a divide and conquer approach. In practice, this means that the framework first “forks”, recursively breaking the task into smaller independent subtasks until they are simple enough to be executed asynchronously. After that, the “join” part begins, in which results of all subtasks are recursively joined into a single result, or in the case of a task which returns void, the program simply waits until every subtask is executed. To provide effective parallel execution, the fork/join framework uses a pool of threads called the ForkJoinPool, which manages worker threads of type ForkJoinWorkerThread.

This algorithm is usually used in the following scenarios, where subtasks creation and management can be achieved properly:
- Sorting problems.
- Matrix multiplication.
- Best move finder for a game.
- Tree traversal.

### ForkJoinPool
ForkJoinPool is pretty similar to ExecutorService:
- It's got a blocking queue to store the tasks.
- It's got a set of threads which take the tasks from the blocking queue.
- It can be used both with Runnable and Callable.

However, the ForkJoinPool has the following differences with ExecutorService:
- Tasks produce sub tasks (fork-join). It means that this pool is optimized for the kind of problems where tasks can produce more independent subtasks.
  - Let's assume there is a main tasks, which is computationally very intensive. We can break it down into multiple subtasks, so each of them can be solve individually (run on separate cores). That means that all sub tasks can run in parallel. This step is called fork. One all sub tasks are done, then the different results need to be combined to produce the final result. This step is called join.
- Per-thread queueing and work-stealing. As a result of the fork step the subtasks created are not stored in the general queue.
  - There is a dequeue (double entry queue) linked to each specific thread, and the subtasks are stored in each individual dequeue. The threads peak tasks from the head of theirs own dequeue.
  - Free threads try to “steal” work from dequeues of busy threads. The threads steal tasks from the tail of other dequeues or from the global entry queue. This approach minimizes the possibility that threads will compete for tasks. It also reduces the number of times the thread will have to go looking for work, as it works on the biggest available chunks of work first.
  - There are several advantages of having a queue per thread:
    - Threads just keep picking tasks from their own local dequeue.
    - No blocking issues since each thread has its own dequeue. However, this is not a general rule because of the tasks stealing.

The Fibonacci number is a good example of task which can be break down into different subtasks. By definition, Fibonacci(n) = Fibonacci(n - 1) + Fibonacci(n - 2). That means that Fibonacci(n) can be broken into 2 subtasks, which can be broken into 2 subtasks each, subtasks which are totally independent from each other.

```java
if(n <= 1)
    return 1;
else {
    Fib f1 = new Fib(n - 1); //fork subtask 1
    Fib f2 = new Fib(n - 2); //fork subtask 2
    f1.solve();
    f2.solve();
    return f1.number + f2.number; //join results
}
```

The general algorithm could be:

```
public Result solve(Task t) {
    split into smaller tasks
    for each of these tasks
        solve(ti)
    wait for all small tasks to complete
    join all individual results
    return result
}
```

In Java 8, the most convenient way to get access to the instance of the ForkJoinPool is to use its static method commonPool(). As its name suggests, this will provide a reference to the common pool, which is a default thread pool for every ForkJoinTask.

```java
ForkJoinPool commonPool = ForkJoinPool.commonPool();
```

### Tasks submitting
Since ForkJoinPool are instances of ExecutorService, the same methods can be used to submit tasks:
 - Execute runnable: execute(Runnable).
 - Submit runnable: submit(Runnable).
 - Submit callable: submit(Callable).

Apart from that there are some specific methods:
- Arrange async execution: execute(ForkJoinTask).
- Await and obtain result: invoke(ForkJoinTask). 
- Arrange exec and obtain Future: submit(ForkJoinTask).

`forkJoinPool.execute(customRecursiveTask);`
`int result = customRecursiveTask.join();`

`int result = forkJoinPool.invoke(customRecursiveTask);`

Alternatively, you can use separate fork() and join() methods. The fork() method submits a task to a pool, but it doesn't trigger its execution. The join() method must be used for this purpose. In the case of RecursiveAction, the join() returns nothing but null; for RecursiveTask<V>, it returns the result of the task's execution.

```java
customRecursiveTaskFirst.fork();
result = customRecursiveTaskLast.join();

forkJoinPool.execute(customRecursiveTask);
int result = customRecursiveTask.join();
```

### ForkJoinTask
ForkJoinTask is the base type for tasks executed inside ForkJoinPool. In practice, one of its two subclasses should be extended: the `RecursiveAction` for void tasks and the `RecursiveTask<V>` for tasks that return a value. They both have an abstract method compute() in which the task’s logic is defined. For instance, to implement the Fibonacci algorithm we can extend RecursiveTask, which is a child of ForkJoinTask. Let's see the example:

```java
public class Fibonacci extends RecursiveTask<Integer> {
    final int n;
    Fibonacci(int n) {this.n = n;}
    public Integer compute() {
        if(n <= 1)
            return 1;
        else {
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            return f2.compute() + f1.join();
        }
    }
}
```

However, to use the ForkJoin until the last calculation is not always as effective as to use the sequential calculation. For instance, it is faster to calculate Fibonacci(10) directly rather than to split this task into smaller tasks. The time needed to fork and join the tasks is not worth it with small calculations. That's why we need to define a threshold to calculate the result right away even though it is not the base case.

This is how the Fibonacci example would look like with a threshold:

```java
public class Fibonacci extends RecursiveTask<Integer> {
    final int n;
    Fibonacci(int n) {this.n = n;}
    public Integer compute() {
        if(n <= 1)
            return 1;
        else {
            if(n <= THRESHOLD)
                return calculateResultDirectly(n);
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            return f2.compute() + f1.join();
        }`
    }`
}`
```

Let's see another example with the RecursiveAction class:

```java
public class CustomRecursiveAction extends RecursiveAction {
    private String workload = "";
    private static final int THRESHOLD = 4;
    private static Logger logger = Logger.getAnonymousLogger();
    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }
    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
           processing(workload);
        }
    }
    private List<CustomRecursiveAction> createSubtasks() {
        List<CustomRecursiveAction> subtasks = new ArrayList<>();
        String partOne = workload.substring(0, workload.length() / 2);
        String partTwo = workload.substring(workload.length() / 2, workload.length());
        subtasks.add(new CustomRecursiveAction(partOne));
        subtasks.add(new CustomRecursiveAction(partTwo));
        return subtasks;
    }`
    private void processing(String work) {
        String result = work.toUpperCase();
        logger.info("This result - (" + result + ") - was processed by " + Thread.currentThread().getName());
    }
}
```

This pattern can be used to develop your own RecursiveAction classes. To do this, create an object which represents the total amount of work, chose a suitable threshold, define a method to divide the work, and define a method to do the work.

To implement ForkJoinTask tasks properly, to be effective, we need:
- To avoid any kind of synchronization.
- Not to use any shared variable.
- Not to do any blocking IO operation.
- To use pure functions.
- They need to be 100% isolated.

Since ForkJoinTasks are instances of Future we can use the common methods:
- `get()`: to get the value.
- `get(timeout)`: to get the value with a timeout.
- `cancel()`: to cancel the task.
- `isCancelled()`: to check whether the task is cancelled.
- `isDone()`: to check whether the task is done.

Using the fork/join framework can speed up processing of large tasks, but to achieve this outcome, some guidelines should be followed:
- Use as few thread pools as possible – in most cases, the best decision is to use one thread pool per application or system.
- Use the default common thread pool, if no specific tuning is needed.
- Use a reasonable threshold for splitting ForkJoinTask into subtasks
- Avoid any blocking in your ForkJoinTasks

## CompletableFuture
The CompletableFuture class implements the Future interface, so we can use it as a Future implementation, but with additional completion logic. For example, we can create an instance of this class with a no-arg constructor to represent some future result, hand it out to the consumers, and complete it at some time in the future using the complete method. When working with CF, if no executor is specified, it will be submitted to ForkJoinPool.commonPool(). The consumers may use the get method to block the current thread until this result is provided. In the example below, we have a method that creates a CompletableFuture instance, then spins off some computation in another thread and returns the Future immediately.

```java
public Future<String> calculateAsync() throws InterruptedException {
    CompletableFuture<String> completableFuture = new CompletableFuture<>();
    Executors.newCachedThreadPool().submit(() -> {
        Thread.sleep(500);
        completableFuture.complete("Hello");
        return null;
    });
    return completableFuture;
}
```

If we already know the result of a computation, we can use the static completedFuture method with an argument that represents a result of this computation. Consequently, the get method of the Future will never block, immediately returning this result instead:

```java
Future<String> completableFuture = CompletableFuture.completedFuture("Hello");
String result = completableFuture.get();
assertEquals("Hello", result);
```

### Encapsulated logic
Static methods runAsync and supplyAsync allow us to create a CompletableFuture instance out of Runnable and Supplier functional types correspondingly. Both Runnable and Supplier are functional interfaces that allow passing their instances as lambda expressions thanks to the new Java 8 feature.

The Runnable interface is the same old interface. The Supplier interface is a generic functional interface with a single method that has no arguments and returns a value of a parameterized type. This allows us to provide an instance of the Supplier as a lambda expression that does the calculation and returns the result. It is as simple as:

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
assertEquals("Hello", future.get());
```

### Processing results
The most generic way to process the result of a computation is to feed it to a function. The thenApply method does exactly that; it accepts a Function instance, uses it to process the result, and returns a Future that holds a value returned by a function:

```java
CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");
assertEquals("Hello World", future.get());
```

If we don't need to return a value down the Future chain, we can use an instance of the Consumer functional interface. Its single method takes a parameter and returns void. There's a method for this use case in the CompletableFuture. The thenAccept method receives a Consumer and passes it the result of the computation. Then the final future.get() call returns an instance of the Void type:

```java
CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<Void> future = completableFuture.thenAccept(s -> System.out.println("Computation returned: " + s));
future.get();
```

Finally, if we neither need the value of the computation, nor want to return some value at the end of the chain, then we can pass a Runnable lambda to the thenRun method:

```java
CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<Void> future = completableFuture.thenRun(() -> System.out.println("Computation finished."));
future.get();
```

These are the main methods with their characteristics:
- thenRun() (thenRunAsync()): it doesn't accept arguments, it doesn't return anything.
- thenAccept() (thenAcceptAsync()): it accepts the result of the previous stage. It doesn't return anything.
- thenApply() (thenApplyAsync()): it accepts the result of the previous stage. It returns the result of the current stage.
- thenCompose() (thenComposeAsync()): it accepts the result of the previous stage. It returns the future result of the current stage.
- thenCombine() (thenCombineAsync()): it accepts the result of two previous stages. It returns the result of the current stage.
- whenComplete() (whenCompleteAsync()): it accepts the return or exception from the previous stage. It doesn't return anything.

### Combining Futures
The best part of the CompletableFuture API is the ability to combine CompletableFuture instances in a chain of computation steps. The result of this chaining is itself a CompletableFuture that allows further chaining and combining.

```java
CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
    .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
assertEquals("Hello World", completableFuture.get());
```

The thenCompose method, together with thenApply, implement basic building blocks of the monadic pattern. They closely relate to the map and flatMap methods of Stream and Optional classes also available in Java 8. Both methods receive a function and apply it to the computation result, but the thenCompose (flatMap) method receives a function that returns another object of the same type. This functional structure allows composing the instances of these classes as building blocks.

```java
CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
    .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2));`
assertEquals("Hello World", completableFuture.get());`
```

### Difference among thenApply(), thenCompose() and thenCombine()
We can use thenApply() to work with a result of the previous call. However, a key point to remember is that the return type will be combined of all calls. So this method is useful when we want to transform the result of a CompletableFuture call (it would be the equivalent of streams map):

```java
CompletableFuture<Integer> finalResult = compute().thenApply(s-> s + 1);
```

The thenCompose() method is similar to thenApply() in that both return a new Completion Stage. However, thenCompose() uses the previous stage as the argument. It will flatten and return a Future with the result directly, rather than a nested future as we observed in thenApply() (it would be the equivalent of streams flatMap):

```java
CompletableFuture<Integer> computeAnother(Integer i){
    return CompletableFuture.supplyAsync(() -> 10 + i);
}
CompletableFuture<Integer> finalResult = compute().thenCompose(this::computeAnother);
```

While thenCompose() is used to combine two Futures where one future is dependent on the other, thenCombine() is used when we want two Futures to run independently and do something after both are complete (it would be the equivalent of streams reduce).

### Running Multiple Futures in Parallel
When we need to execute multiple Futures in parallel, we usually want to wait for all of them to execute and then process their combined results. The `CompletableFuture.allOf` static method allows to wait for completion of all of the Futures provided as a var-arg.

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");
CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
combinedFuture.get();
assertTrue(future1.isDone());
assertTrue(future2.isDone());
assertTrue(future3.isDone());
```

The limitation of this method is that it does not return the combined results of all Futures. Instead, we have to manually get results from Futures. Fortunately, CompletableFuture.join() method and Java 8 Streams API makes it simple:

```java
String combined = Stream.of(future1, future2, future3)
  .map(CompletableFuture::join)
  .collect(Collectors.joining(" "));
assertEquals("Hello Beautiful World", combined);
```

### Handling Errors
For error handling in a chain of asynchronous computation steps, we have to adapt the throw/catch idiom in a similar fashion. Instead of catching an exception in a syntactic block, the CompletableFuture class allows us to handle it in a special handle method.

```java
String name = null;
CompletableFuture<String> completableFuture =  CompletableFuture.supplyAsync(() -> {
      if (name == null) {
          throw new RuntimeException("Computation error!");
      }
      return "Hello, " + name;
   }).handle((s, t) -> s != null ? s : "Hello, Stranger!");
assertEquals("Hello, Stranger!", completableFuture.get());
```

As an alternative scenario, the completeExceptionally method is intended for having the ability to complete the future with an exception:

```java
CompletableFuture<String> completableFuture = new CompletableFuture<>();
completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));
completableFuture.get(); // ExecutionException
```

### Async methods
Most methods of the fluent API in CompletableFuture class have two additional variants with the Async postfix. From my point of view, all of these methods (like thenApply and thenApplyAsync) are terribly named and are confusing. For instance, with these two methods, there is nothing in thenApplyAsync that is more asynchronous than thenApply from the contract of these methods. These methods (the async ones) are usually intended for running a corresponding step of execution in another thread.

The methods **without** the Async postfix run the next execution stage using the calling thread. In contrast, the Async method without the Executor argument runs a step using the common fork/join pool implementation of Executor that is accessed with the ForkJoinPool.commonPool() method. Finally, the Async method with an Executor argument runs a step using the passed Executor. Let's see this example:

```java
CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future = completableFuture
    .thenApplyAsync(s -> s + " World");
assertEquals("Hello World", future.get());
```

The only visible difference is the thenApplyAsync method, but under the hood the application of a function is wrapped into a ForkJoinTask instance. This allows us to parallelize our computation even more and use system resources more efficiently. Basically if we can parallelize the different steps, we should be using the *Async methods since it'll make use of as many threads as possible without waiting to the previous steps.

### Completable Future API
Let's see now the most common methods of the completable future API:
- `static CompletableFuture<Void>	allOf​(CompletableFuture<?>... cfs)`: returns a new CompletableFuture that is completed when all of the given CompletableFutures complete.
- `static CompletableFuture<Object>	anyOf​(CompletableFuture<?>... cfs`): returns a new CompletableFuture that is completed when any of the given CompletableFutures complete, with the same result.
- `boolean cancel​(boolean mayInterruptIfRunning)`: if not already completed, completes this CompletableFuture with a CancellationException.
- `boolean complete​(T value)`: if not already completed, sets the value returned by get() and related methods to the given value.
- `CompletableFuture<T> completeAsync​(Supplier<? extends T> supplier)`: completes this CompletableFuture with the result of the given Supplier function invoked from an asynchronous task using the default executor (fork-join).
- `CompletableFuture<T> completeAsync​(Supplier<? extends T> supplier, Executor executor)`: completes this CompletableFuture with the result of the given Supplier function invoked from an asynchronous task using the given executor.
- `static <U> CompletableFuture<U> completedFuture​(U value)`: returns a new CompletableFuture that is already completed with the given value.
- `boolean completeExceptionally​(Throwable ex)`: if not already completed, causes invocations of get() and related methods to throw the given exception.
- `CompletableFuture<T> completeOnTimeout​(T value, long timeout, TimeUnit unit)`: completes this CompletableFuture with the given value if not otherwise completed before the given timeout.
- `T get()`: waits if necessary for this future to complete, and then returns its result.
- `T get​(long timeout, TimeUnit unit)`: waits if necessary for at most the given time for this future to complete, and then returns its result, if available.
- `T getNow​(T valueIfAbsent)`: returns the result value (or throws any encountered exception) if completed, else returns the given valueIfAbsent.
- `T join()`: returns the result value when complete, or throws an (unchecked) exception if completed exceptionally.
- `static CompletableFuture<Void> runAsync​(Runnable runnable)`: returns a new CompletableFuture that is asynchronously completed by a task running in the ForkJoinPool.commonPool() after it runs the given action.
- `static CompletableFuture<Void> runAsync​(Runnable runnable, Executor executor)`: returns a new CompletableFuture that is asynchronously completed by a task running in the given executor after it runs the given action.
- `static <U> CompletableFuture<U> supplyAsync​(Supplier<U> supplier)`: returns a new CompletableFuture that is asynchronously completed by a task running in the ForkJoinPool.commonPool() with the value obtained by calling the given Supplier.
- `static <U> CompletableFuture<U> supplyAsync​(Supplier<U> supplier, Executor executor)`: returns a new CompletableFuture that is asynchronously completed by a task running in the given executor with the value obtained by calling the given Supplier.

## Synchronized keyword
Simply put, in a multi-threaded environment, a race condition occurs when two or more threads attempt to update mutable shared data at the same time. Java offers a mechanism to avoid race conditions by synchronizing thread access to shared data. A piece of logic marked with synchronized becomes a synchronized block, allowing only one thread to execute at any given time.

The synchronized keyword can be used on different levels:
- Instance methods: instance methods are synchronized over the instance of the class owning the method. Which means only one thread per instance of the class can execute this method. If there is more than 1 instance, the synchronization won't work between instances. Here an example:

```java
public synchronized void synchronisedCalculate() {
    setSum(getSum() + 1);
}
```

- Static methods: these methods are synchronized on the Class object associated with the class and since only one Class object exists per JVM per class, only one thread can execute inside a static synchronized method per class, irrespective of the number of instances it has. Here an example:

```java
public static synchronized void syncStaticCalculate() {
    staticSum = staticSum + 1;
}
```

- Code blocks: sometimes we do not want to synchronize the entire method but only some instructions within it. This can be achieved by applying synchronized to a block. When we do this, we need to send to the block the monitor object, where the code inside gets synchronized on the monitor object. Simply put, only one thread per monitor object can execute inside that block of code. In case the method is static, we would pass the class name in place of the object reference. And the class would be a monitor for synchronization of the block. Here an example:

```java
public void performSynchronisedTask() {
    synchronized (this) {
        setCount(getCount()+1);
    }
}

public static void performStaticSyncTask() {
    synchronized (SynchronisedBlocks.class) {
        setStaticCount(getStaticCount() + 1);
    }
}
```

## Volatile keyword
The volatile keyword solves a visibility problem between threads. When there are two threads executing the same code, a local cache is used by every single thread to store the value of the variables. Hence, if for instance the thread 1 changes the value of a variable, that change is not seen by the thread 2 since in the local cache of the thread 2 the variable keeps the same value.

Let's say that thread 2 is executing a while, which is controlled by a flag whose initial value is false, which is managed by the thread 1. While the flag is false, the while code will be executed. No matter whether or not the thread 1 updates the value to true, the loop will keep executing because the thread 2 saves its own copy of the flag.

To solve this we have the volatile keyword. When we use this keyword, the variable will be stored in a shared cache among threads instead of the local caches. That way, when the thread 1 updates the value of the flag, the thread 2 will pick up the new value immediately because both threads are using the same place of memory.

Typically volatiles are used for flags, for boolean values.

## Atomic values
In the previous section we solved the visibility problem, but we still have a synchronization problem. Let's imagine that we have a variable called value, whose definition is:

```java
int myData = 1;
```

After that, we have two different threads which execute this sentence: `myData++`.

It is obvious that without the use of the volatile keyword, the result after the two threads are executed is that the value will be 2. But, what will happen if we use the volatile keyword? We could think that since both threads are sharing the variable, the first thread will increment the value by one and the second thread too, so the final result is 3. However, the instruction `myData++` consist of 2 different steps:
1. Read value.
2. Update and write value.

Since we don't control the execution of the threads, we don't control neither the JVM nor the CPU, the execution of both threads could get mixed up in the following way:
3. Thread1 reads the value of myData = 1.
4. Thread2 reads the value of myData = 1.
5. Thread1 updates and writes the value of myData -> myData = 2.
6. Thread2 updates and writes the value of myData -> myData = 2.

Even with the volatile keyword, the result is not the expected one. What we need here is atomicity. Once myData is being managed, no other thread can interrupt that operation. This could be achieved easily if we put the `myData++` code within a `Synchronized` block. However, Java provides another more elegant solution by using the Atomic types. Among the most common used we have AtomicInteger, AtomicLong, AtomicBoolean, and AtomicReference, which represent an int, long, boolean, and object reference respectively. So we could solve the previous problem like this:

```java
AtomicInteger myData = new AtomicInteger(1);
myData.increment();
```

Increment is just one of the multiple operations that in this case AtomicInteger offers:
 - incrementAndGet(): it increments the value and return the new one. This would be the equivalent to a = b++;
 - decrementAndGet(): it decrements the value and return the new one. This would be the equivalent to a = b--;
 - addAndGet(int delta): it adds the delta value to the current value and returns the new one.
 - compareAndSet(int expectedValue, int newValue): if the current value of the variable is expectedValue, then set the newValue. Otherwise, do nothing.

Typically AtomicLong and AtomicInteger are used for counters which are used by multiple threads. 

## Locks
Simply put, a lock is a more flexible and sophisticated thread synchronization mechanism than the standard synchronized block. The Lock interface has been around since Java 1.5. It's defined inside the java.util.concurrent.lock package and it provides extensive operations for locking.

There are few differences between the use of synchronized block and using Lock API's:
- A synchronized block is fully contained within a method – we can have Lock API's lock() and unlock() operation in separate methods
- A synchronized block doesn't support the fairness, any thread can acquire the lock once released, no preference can be specified. We can achieve fairness within the Lock APIs by specifying the fairness property. It makes sure that longest waiting thread is given access to the lock
- A thread gets blocked if it can't get an access to the synchronized block. The Lock API provides tryLock() method. The thread acquires lock only if it's available and not held by any other thread. This reduces blocking time of thread waiting for the lock
- A thread which is in “waiting” state to acquire the access to synchronized block, can't be interrupted. The Lock API provides a method lockInterruptibly() which can be used to interrupt the thread when it's waiting for the lock.

### Lock API
Let's take a look at the methods in the Lock interface:
- `void lock()`: acquire the lock if it's available; if the lock isn't available a thread gets blocked until the lock is released.
- `void lockInterruptibly()`: this is similar to the lock(), but it allows the blocked thread to be interrupted and resume the execution through a thrown java.lang.InterruptedException.
- `boolean tryLock()`: this is a non-blocking version of lock() method; it attempts to acquire the lock immediately, return true if locking succeeds.
- `boolean tryLock(long timeout, TimeUnit timeUnit)`: this is similar to tryLock(), except it waits up the given timeout before giving up trying to acquire the Lock.
- `void unlock()`: unlocks the Lock instance.

In addition to the Lock interface, we have a ReadWriteLock interface which maintains a pair of locks, one for read-only operations, and one for the write operation. The read lock may be simultaneously held by multiple threads as long as there is no write. ReadWriteLock declares methods to acquire read or write locks:
- `Lock readLock()`: returns the lock that's used for reading
- `Lock writeLock()`: returns the lock that's used for writing

### ReentrantLock implementation
ReentrantLock class implements the Lock interface. It offers the same concurrency and memory semantics, as the implicit monitor lock accessed using synchronized methods and statements, with extended capabilities. We need to make sure that we are wrapping the lock() and the unlock() calls in the try-finally block to avoid the deadlock situations.

### ReentrantReadWriteLock implementation
ReentrantReadWriteLock class implements the ReadWriteLock interface. Let's see rules for acquiring the ReadLock or WriteLock by a thread:
- Read Lock: if no thread acquired the write lock or requested for it then multiple threads can acquire the read lock.
- Write Lock: if no threads are reading or writing then only one thread can acquire the write lock

### StampedLock implementation
StampedLock is introduced in Java 8. It also supports both read and write locks. However, lock acquisition methods return a stamp that is used to release a lock or to check if the lock is still valid. Another feature provided by StampedLock is optimistic locking. Most of the time read operations don't need to wait for write operation completion and as a result of this, the full-fledged read lock isn't required.

## Thread-safety
Although multithreading is a powerful feature, it comes at a price. In multithreaded environments, we need to write implementations in a thread-safe way. This means that different threads can access the same resources without exposing erroneous behavior or producing unpredictable results. This programming methodology is known as “thread-safety”. Let's see different approaches to achieve it.

### Stateless Implementations
In most cases, errors in multithreaded applications are the result of incorrectly sharing state between several threads. Therefore, the first approach that we'll look at is to achieve thread-safety using stateless implementations.

A function is stateless if given a specific input, it always produces the same output. The method neither relies on external state nor maintains state at all. Hence, it's considered to be thread-safe and can be safely called by multiple threads at the same time.

### Immutable Implementations
If we need to share state between different threads, we can create thread-safe classes by making them immutable. To put it simply, a class instance is immutable when its internal state can't be modified after it has been constructed. The easiest way to create an immutable class in Java is by declaring all the fields private and final and not providing setters.

### Thread-Local Fields
If we actually need to maintain state, we can create thread-safe classes that don't share state between threads by making their fields thread-local. We can easily create classes whose fields are thread-local by simply defining private fields in Thread classes (those which extend Thread).

### Synchronized Collections
We can easily create thread-safe collections by using the set of synchronization wrappers included within the collections framework. This means that the methods can be accessed by only one thread at a time, while other threads will be blocked until the method is unlocked by the first thread. Thus, synchronization has a penalty in performance, due to the underlying logic of synchronized access.

### Concurrent Collections
Alternatively to synchronized collections, we can use concurrent collections to create thread-safe collections. Java provides the java.util.concurrent package, which contains several concurrent collections, such as ConcurrentHashMap.

Unlike their synchronized counterparts, concurrent collections achieve thread-safety by dividing their data into segments. In a ConcurrentHashMap, for instance, several threads can acquire locks on different map segments, so multiple threads can access the Map at the same time.

Concurrent collections are much more performant than synchronized collections, due to the inherent advantages of concurrent thread access. It's worth mentioning that synchronized and concurrent collections only make the collection itself thread-safe and not the contents.

### Atomic Objects
As we saw in above sections, it's also possible to achieve thread-safety using the set of atomic classes that Java provides, including AtomicInteger, AtomicLong, AtomicBoolean, and AtomicReference.

### Synchronized Methods
While the earlier approaches are very good for collections and primitives, we will at times need greater control than that. So, another common approach that we can use for achieving thread-safety is implementing synchronized methods. Simply put, only one thread can access a synchronized method at a time while blocking access to this method from other threads. Other threads will remain blocked until the first thread finishes or the method throws an exception.

### Synchronized Statements
We can synchronize just a part of a method, again by using the synchronized keyword.

### Volatile Fields
Synchronized methods and blocks are handy for addressing variable visibility problems among threads. Even so, the values of regular class fields might be cached by the CPU. Hence, consequent updates to a particular field, even if they're synchronized, might not be visible to other threads. To prevent this situation, we can use volatile class fields.

### Reentrant Locks
Java provides an improved set of Lock implementations, whose behavior is slightly more sophisticated than the intrinsic locks. With intrinsic locks, the lock acquisition model is rather rigid: one thread acquires the lock, then executes a method or code block, and finally releases the lock, so other threads can acquire it and access the method. There's no underlying mechanism that checks the queued threads and gives priority access to the longest waiting threads. ReentrantLock instances allow us to do exactly that, hence preventing queued threads from suffering some types of resource starvation.

### Read/Write Locks
Another powerful mechanism that we can use for achieving thread-safety is the use of ReadWriteLock implementations. A ReadWriteLock lock actually uses a pair of associated locks, one for read-only operations and other for writing operations. As a result, it's possible to have many threads reading a resource, as long as there's no thread writing to it. Moreover, the thread writing to the resource will prevent other threads from reading it.

# Examples
The code found in this tutorial has the following structure:
- Class AsyncCodingApplication: this is the class where examples can be run.
- The interface FactoryExecutable can be used to run every single example in this tutorial. The interface offers just one method, execute(Object), which is implemented by any example with one object sent as a parameter to send the information needed to run the example.
- The enum ExecutableType has all example types available.

## Fibonacci example
This example (ExecutableType.FIBONACCI) calculates the Fibonacci value of a certain number. It runs 4 different calculations within the class FibonacciExample. The Fibonacci value is calculated as F(n) = F(n - 1) + F(n - 2). As we can see, this is a recursive calculation. In this example the value is calculated in 4 different ways and at the end it shows the time needed to run every calculation.
1. By calculating F(n - 1) and F(n - 2) without any type of performance improvement. This is the worst implementation since we're calculating a lot of times the same vale. For instance, F(10) = F(9) + F(8), and F(9) = F(8) + F(7). As we can see, F(8) is calculated twice. This is a single thread calculation.
2. By storing the intermediate calculations in a cache. As we saw in the previous example, F(8) is calculated more than once, so if we save F(8) in a common cache, the algorithm will be faster. This is a single thread calculation.
3. By using the ForkJoin pool. Without applying any type of optimization, we're calculating the Fibonacci value taking advantage of the multitasking. This is a multithreading calculation.
4. By using the ForkJoin pool with cache optimization. It makes use of a ConcurrentMap, which is thread-safe. This is a multithreading calculation.

The fastest algorithm is by far the second one, because it cuts the tree of potential calculations up to the number to calculate. There is no even point of trying to calculate F(500) with no optimizations. The ForkJoin algorithm is faster than the one with no optimizations, but slower than the cache optimized one. Finally, the ForkJoin with cache optimization is faster than the ForkJoin one but again slower than the one thread cache optimized.

These are some results from my tests:
- Fibonacci(40)
  1. Single thread no optimization: 1346 ms
  2. Single thread cache optimization: 0 ms
  3. ForkJoin no optimization: 249 ms
  4. ForkJoin cache optimization: 1 ms
- Fibonacci(43)
  1. Single thread no optimization: 5345 ms
  2. Single thread cache optimization: 0 ms
  3. ForkJoin no optimization: 1007 ms
  4. ForkJoin cache optimization: 2 ms
- Fibonacci(46)
  1. Single thread no optimization: 22852 ms
  2. Single thread cache optimization: 0 ms
  3. ForkJoin no optimization: 4148 ms
  4. ForkJoin cache optimization: 3 ms

## ThreadLocal example
In this example, 1000 different parallel tasks are run within the 10-sized FixedThreadPool. The purpose of each task is very simple, just to print a Date. However, to print the date it makes use of the object SimpleDateFormat. We have 3 ways to achieve this:
- To create 1000 instances of SimpleDateFormat, 1 per task. This is 100% thread-safe, but highly memory efficient.
- To create a single instance of SimpleDateFormat and to share it across the 1000 tasks. Since SimpleDateFormat is not thread-safe, this could lead to concurrency problems.
- To create 10 instances of SimpleDateFormat, 1 per thread. This is 100% thread-safe (because only 10 tasks can be executed at the same time, 1 per thread) and memory efficient. This is achieve by using the class ThreadSafeDateFormatter, which makes use of the factory method ThreadLocal.withInitial.

## Callable example
In this example a callable is created in two different ways:
- With a constructor.
- With a lambda. The advantage of using a lambda, apart from saving some code, is that we can pass parameters to the lambda by using final variables.

The result is collected in a Future to show the result by using the blocking get() operation.

## Semaphore example
In this example a fair semaphore has been used as a lock method (permits = 1) as well as keep the order. Basically what the example do is to populate a stack with 10 elements, from 0 to 9, so after the tasks are done, the first pop must be 9, the second pop must be 8, and so and so on.

Ten callable calls are run within a 10-sized threadPool. Even though the tasks run simultaneously, they are triggered one by one (because there is one thread in charge of doing that) and since the push operation is O(1), there are many chances that the order of the stack is the expected one. To avoid that, a random sleep has been introduced before the push operation. Now there is no way to know which operation is gonna be performed first.

However, by introducing a lock before doing anything else, thanks to the random sleep time, we can simulate the scenario. If the lock is removed from the code, the test will fail. If the lock takes place, the test will pass.

## Executor example
In this example we show how to use single, fixed, cached, custom and scheduled thread pool:
- SingleThreadPool: in this example we create a pool of 1 thread and two runnable tasks, we submit both of them and we print logs to verify that one task is executed after the other one.
- FixedThreadPool: in this example we create a fixed pool of 2 threads and we submit 6 runnable tasks. We print logs to verify that the tasks are executed in set of two.
- CachedThreadPool: in this example we create a cached thread pool, which is well known due to its "unlimited" number of threads in the pool (in fact, this type of pool doesn't have queue itself). We print logs to verify that no matter how many tasks you send, all of them are executed at the same time.
- Custom and scheduled thread pool: in this example we test at the same time custom and scheduled thread pool by submitting 7 tasks with a sleep time of 1 second per task:
  - We create a custom thread pool of core = 2, maximum threads = 4 and queue length = 2. That means that at any time, at its maximum capacity, the pool can handle 4 times at the same time (4 threads) with 2 more pending on the queue. The pool will manage up to 6 tasks, but from that, the tasks will be rejected until current task/s are done.
  - We create a 1-size scheduled thread pool to monitor the previous pool. We print the main characteristics of the pool so we can see how the number of threads goes from 2 to 3, the number of elements in the queue, the number of tasks completed, ... This pool has been created with a custom ThreadFactory to print a special name for the thread in that pool.
  - We create a RejectedExecutionHandlerImpl to print the rejected tasks. In the example, we submit 7 tasks at the same time, so 1 task is rejected.