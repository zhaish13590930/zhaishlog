package com.zhaish.network.factoryschdule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaish
 * @date 2021/6/29 11:41
 * @desc
 **/
public class Pipelining {
    private String step;
    private int index;
    private List<Product> todoList = new ArrayList<>();
    private List<Product> finished = new ArrayList<>();
    private Pipelining prev;
    private Pipelining next;

    public Pipelining(String step, int index, Pipelining prev) {
        this.step = step;
        this.index = index;
        this.prev = prev;
    }


    public void tick(int cycle){
        if(todoList.size()>0){
            //1.排序
            todoList.sort((a,b)->{
                int cp = index;
                int t1 = a.getTimes()[cp];
                int t2 = b.getTimes()[cp];

                if (t1<t2){
                    return -1;
                }else if(t1==t2){
                    //先不考虑等于的case 后面的排序
                    cp++;
                    return a.getTimes()[cp] - b.getTimes()[cp];
                }else {
                    return 1;
                }
            });
            //2.取出最小的
            Product min = todoList.get(0);
            int remain = min.getTimes()[index];
            if(remain>1){
                min.getTimes()[index] = remain-1;
                if(min.getStatus()[index] == 0){
                    print(cycle,min.getName()+" 开始工序 "+step);
                    min.getStatus()[index] = 1;
                }
            }else if(remain == 1){
                min.getTimes()[index] = 0;
                todoList.remove(0);
                finished.add(min);
                print(cycle,min.getName()+" 完成工序 "+step);
                min.getStatus()[index] = 2;
            }

        }else {
            print(cycle,"流水线"+step+"空闲");
        }


    }

    public boolean hasNoWork(){
        return todoList.size() ==0 && finished.size() ==0;
    }

    public List<Product> getFinished() {
        return finished;
    }


    private void print(int cycle, String msg){
        System.out.println(getCycle(cycle) +msg);
    }

    private String getCycle(int cycle){
        return "轮次 "+cycle+" ";
    }
    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Pipelining getNext() {
        return next;
    }

    public void setNext(Pipelining next) {
        this.next = next;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Product> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Product> todoList) {
        this.todoList = todoList;
    }

    public Pipelining getPrev() {
        return prev;
    }

    public void setPrev(Pipelining prev) {
        this.prev = prev;
    }
}
