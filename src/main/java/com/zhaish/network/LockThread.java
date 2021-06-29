package com.zhaish.network;

/**
 * @datetime:2019/12/4 16:59
 * @author: zhaish
 * @desc:
 **/
public class LockThread extends Thread{
    private static Object lock = new Object();
    private void print(Object o){
        System.out.println(this.getName()+":"+o);
    }

    @Override
    public void run(){
        for (int i = 0;i < 1000;i++){
            while(i%5==3){
                synchronized (lock) {
                    try {
                        print("==");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            print(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LockThread lockThread = new LockThread();
        lockThread.setName("A");
        lockThread.start();
        LockThread lockThread2 = new LockThread();
        lockThread2.setName("B");
        lockThread2.start();
        Thread t = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock){
                        lock.notifyAll();
                    }
                    //TODO j加break条件
                }
            }
        };
        t.start();
    }
}
