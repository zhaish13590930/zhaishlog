package com.zhaish.network.spi;

/**
 * @datetime:2021/3/29 14:24
 * @author: zhaish
 * @desc:
 **/
@CarType(value = "BOMM")
public class MakeBaoma implements IMakeCar{

    @Override
    public void loadNBCar() {
        System.out.println("闭门造宝马");
    }
}
