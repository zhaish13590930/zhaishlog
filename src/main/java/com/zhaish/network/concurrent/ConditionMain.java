package com.zhaish.network.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @datetime:2021/3/23 13:42
 * @author: zhaish
 * @desc:
 **/
public class ConditionMain {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public static void main(String[] args) {
        ConditionMain main = new ConditionMain();
        System.out.println("main begin");
        WaitThread t = new WaitThread();
        t.setCondition(main.condition);
        t.setLock(main.lock);
        t.start();
        System.out.println("WaitThread started");
        try {
            main.lock.lock();
            main.condition.await();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }finally {
            main.lock.unlock();
        }
        System.out.println("main end");
    }
    static class WaitThread extends Thread{
        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public void setLock(Lock lock) {
            this.lock = lock;
        }

        private Lock lock;
        private Condition condition;
        @Override
        public void run(){
            System.out.println("Wait begin");
            try {
                Thread.sleep(2000);
                this.lock.tryLock();
                this.condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                this.lock.unlock();
            }
            System.out.println("Wait end");


        }
    }
}
