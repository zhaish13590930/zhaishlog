package com.zhaish.network.spi;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @datetime:2021/3/29 14:25
 * @author: zhaish
 * @desc:
 **/
public class TestSpi {
    public static void main(String[] args) {
        ServiceLoader<IMakeCar> load = ServiceLoader.load(IMakeCar.class);
        Iterator<IMakeCar> it = load.iterator();
        if(it.hasNext()){
            IMakeCar makeCar = it.next();
            Annotation[] annotations = makeCar.getClass().getAnnotations();
            CarType carType = makeCar.getClass().getAnnotation(CarType.class);
            System.out.println("cartype:"+carType.value());
            makeCar.loadNBCar();
        }
    }
}
