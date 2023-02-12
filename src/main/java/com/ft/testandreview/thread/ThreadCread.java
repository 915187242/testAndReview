package com.ft.testandreview.thread;

import java.util.concurrent.*;

public class ThreadCread {
    // 方式一：继承Thread类并重写 Run方法
    public static class MyThread1 extends Thread {
        @Override
        public void run() {
            System.out.println("实现线程类方式一");
        }
    }

    // 方式二：实现Runnable接口,Run方法
    public static class MyThread2 implements Runnable{

        @Override
        public void run() {
            System.out.println("实现线程类方式二");
        }
    }

    // 方式三：实现Callable接口call()方法，搭配线程池工具ExecutorService使用
    //利用ExecutorService.submit(callable实现类对象)，返回一个Future
    //利用Future.get得到结果
    //底层用到了Future接口的实现类 FutureTask， 当调用线程池submit() ->   1、将实现callable接口的类的对象传给FutureTask类的对象ftask(FutureTask类实现了 RunableFuture接口，即Runable接口和Future接口，有一个成员变量接收实现了callable接口的类的对象，并且实现的Run方法中，使用了call方法)  -> execute方法(内部执行步骤，后续补充)  ->调用FutureTask的run方法（封装了call方法，并且保存结果） -> 最后返回FutureTask类的对象，并用Future变量指向它；
    //                                                               2、execute方法()
    public static class Task implements Callable<Integer>{
        @Override
        public Integer call() throws Exception{
            System.out.println("实现线程类方式三");
            return 1;
        }
    }

    //综上所述，其实线程的使用，最底层还是实现了Runable接口，并且使用run方法执行线程任务

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //方式一和方式二没有返回值
        // 方式一
        Thread myThread1 = new MyThread1();
        myThread1.start();

        //方式二
        Thread myThread2 = new Thread(new MyThread2());
        myThread2.start();
        // Or 使用函数式编程的方式实现方式二
        new Thread(()->{
            System.out.println("利用函数式编程的方式实现方式二");
        }).start();


        // 方式三
        Task task = new Task();
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> result = executor.submit(task);
        System.out.println("方式三实现callable接口的call方法，并返回结果" + result.get());
        /*
        Future 接口只有几个比较简单的方法：
        public abstract interface Future<V> {
            public abstract boolean cancel(boolean paramBoolean);
            public abstract boolean isCancelled();
            public abstract boolean isDone();
            public abstract V get() throws InterruptedException, ExecutionException;
            public abstract V get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException;
        }
        cancel 方法是试图取消一个线程的执行。
        注意是试图取消，并不一定能取消成功。因为任务可能已完成、已取消、或者一些
        其它因素不能取消，存在取消失败的可能。 boolean 类型的返回值是“是否取消成
        功”的意思。参数 paramBoolean 表示是否采用中断的方式取消线程执行。

        所以有时候，为了让任务有能够取消的功能，就使用 Callable 来代替 Runnable 。如
        果为了可取消性而使用 Future 但又不提供可用的结果，则可以声明 Future<?> 形
        式类型、并返回 null 作为底层任务的结果。
         */
        executor.shutdown();

    }
}
