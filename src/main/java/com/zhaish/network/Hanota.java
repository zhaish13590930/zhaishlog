package com.zhaish.network;

/**
 * @datetime:2021/2/2 14:03
 * @author: zhaish
 * @desc:
 **/
public class Hanota {
    public static void main(String[] args) {
        String aa = "b";
        hannoi(3);
        System.out.println("====================");
        hannoi(6);
    }
    public static void hannoi(int n){
        if(n>0){
            func(n,"左","中","右");
        }
    }

    private static void func(int n, String from, String to, String other) {
        if(n == 1){
            System.out.println("Move 1 from "+from +" to "+ to);
        }else {
            func(n-1,from,other,to);
            System.out.println("Move "+n +" from "+from + " to "+to);
            func(n-1,other,to,from);
        }

    }
}
