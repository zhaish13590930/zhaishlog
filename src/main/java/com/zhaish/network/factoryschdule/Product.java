package com.zhaish.network.factoryschdule;

/**
 * @author zhaish
 * @date 2021/6/29 13:42
 * @desc
 **/
public class Product {
    private String name;
    public int[] times;
    public int[] status;

    public Product(int name,int[] times) {
        this.times = times;
        this.name = "产品"+name;
        this.status = new int[times.length];
    }

    public int[] getStatus() {
        return status;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }

    public int[] getTimes() {
        return times;
    }

    public void setTimes(int[] times) {
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
