package com.zhaish.network.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @datetime:2021/3/24 18:36
 * @author: zhaish
 * @desc:
 **/
public class ABThenC {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(3);
        Thread a = new Thread(()->{
            System.out.println("A begin;");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdl.countDown();
            System.out.println("A end;");
        });
        Thread b = new Thread(()->{
            System.out.println("B begin;");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdl.countDown();
            System.out.println("B end;");
        });
        Thread c = new Thread(()->{
            System.out.println("C begin;");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdl.countDown();
            System.out.println("C end;");
        });
        a.start();
        b.start();
        c.start();
        System.out.println("wait for a b c done!");
        //cdl.await();
        cdl.await(2, TimeUnit.SECONDS);
        System.out.println("exit");
    }
}
