package com.ft.testandreview.thread;

public class TGroup {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("group1");
        threadGroup.setMaxPriority(6);
        Thread thread = new Thread(threadGroup, "t1");
        thread.setPriority(7);
        System.out.println("线程组的最大优先级"+threadGroup.getMaxPriority());//6
        System.out.println("线程的优先级"+thread.getPriority());//6
        //如果某个线程优先级大于线程所在线程组的最大优先级，那么该线程的优先
        //级将会失效，取而代之的是线程组的最大优先级。


        /*
        ThreadGroup是一个标准的向下引用的树状结构，这样设计的原因是
        防止"上级"线程被"下级"线程引用而无法有效地被GC回收。

        线程组是一个树状的结构，每个线程组下面可以有多个线程或者线程
        组。线程组可以起到统一控制线程的优先级和检查线程的权限的作用。
         */
    }
}
