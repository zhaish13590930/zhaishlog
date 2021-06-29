package com.zhaish.network.factoryschdule;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaish
 * @date 2021/6/29 11:36
 * @desc
 **/
public class ShorttestTime {

    /**
     * "        产品
     * 工序"1	2	3	4	5	6	7	8	9	10	11	12	13	14	15
     * A	50	28	25	22	23	26	20	5	25	42	12	45	20	21	24
     * B	18	19	16	24	21	20	16	12	24	24	16	74	32	20	32
     * C	36	32	80	35	33	37	38	15	15	15	24	20	45	36	45
     * D	12	40	14	13	12	11	17	16	65	8	13	62	21	18	12
     * E	43	45	50	48	49	12	47	54	53	12	30	42	51	32	55
     * @param args
     */
    public static void main(String[] args) {
        List<Product> list = new ArrayList<>();
        String ss ="50\t28\t25\t22\t23\t26\t20\t5\t25\t42\t12\t45\t20\t21\t24\n" +
                    "18\t19\t16\t24\t21\t20\t16\t12\t24\t24\t16\t74\t32\t20\t32\n" +
                    "36\t32\t80\t35\t33\t37\t38\t15\t15\t15\t24\t20\t45\t36\t45\n" +
                    "12\t40\t14\t13\t12\t11\t17\t16\t65\t8\t13\t62\t21\t18\t12\n" +
                    "43\t45\t50\t48\t49\t12\t47\t54\t53\t12\t30\t42\t51\t32\t55";
        //ss = ss.replaceAll("\\t"," ");
        String[] lines = ss.split("\\\n");
        String[][] rx = new String[5][15];
        AtomicInteger lineNum = new AtomicInteger(-1);
        Arrays.stream(lines).forEach(line->{
            lineNum.getAndIncrement();
            String[] rows = line.split("\\t");
            rx[lineNum.get()] = rows;
        });
        System.out.println(rx);
        int pName = 0;
        for (int i = 0; i < 15; i++) {
            pName++;
            Product p = new Product(pName, new int[5]);
            list.add(p);
            for (int i1 = 0; i1 < 5; i1++) {
                p.times[i1] = Integer.valueOf(rx[i1][i]);
            }
        }
        System.out.println(JSON.toJSONString(list));
        AtomicBoolean go = new AtomicBoolean(true);
        Pipelining p1 = new Pipelining("A",0,null);
        Pipelining p2 = new Pipelining("B",1,p1);
        Pipelining p3 = new Pipelining("C",2,p2);
        Pipelining p4 = new Pipelining("D",3,p3);
        Pipelining p5 = new Pipelining("E",4,p4);
        p1.setNext(p2);
        p2.setNext(p3);
        p3.setNext(p4);
        p4.setNext(p5);
        p5.setNext(null);

        int cycle = 1;
        while (go.get()){
            System.out.println("轮次 "+cycle+" 开始");
            if(cycle ==5 || cycle == 6){
                System.out.println(111);
            }
            fetch1(list,p1);
            p1.tick(cycle);

            fetch2(p2);
            p2.tick(cycle);

            fetch2(p3);
            p3.tick(cycle);

            fetch2(p4);
            p4.tick(cycle);

            fetch2(p5);
            p5.tick(cycle);

            System.out.println("轮次 "+cycle+" 完成");
            cycle++;
            if(list.isEmpty() &&p1.getFinished().isEmpty() && p2.hasNoWork() && p3.hasNoWork() &&p4.hasNoWork() && p5.getTodoList().isEmpty()){
                go.set(false);
            }
            if(cycle > 10000){
                go.set(false);
            }
        }

    }


    public static void fetch1(List<Product> list,Pipelining pipelining){
        if(pipelining.getTodoList().isEmpty()){
            if(list.size()>0){
                list.sort((a,b)->{
                    int cp = 0;
                    int t1 = a.getTimes()[cp];
                    int t2 = b.getTimes()[cp];

                    if (t1<t2){
                        return -1;
                    }else if(t1==t2){
                        //先不考虑等于的case 后面的排序
                        cp++;
                        return a.getTimes()[cp] - b.getTimes()[cp];
                        //return 0;
                    }else {
                        return 1;
                    }
                });
                Product min = list.remove(0);
                pipelining.getTodoList().add(min);
            }
        }


    }
    public static void fetch2(Pipelining pipelining){
        if(pipelining.getTodoList().isEmpty()){
            Pipelining prev = pipelining.getPrev();
            List<Product> prevFinished = prev.getFinished();
            if(!prevFinished.isEmpty()){
                pipelining.getTodoList().addAll(prevFinished);
                prevFinished.clear();
            }
        }
    }
}
