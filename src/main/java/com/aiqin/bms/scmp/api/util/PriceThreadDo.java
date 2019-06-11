package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.product.jobs.DoPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PriceThreadDo {
    private static List<Integer> list = new ArrayList<>();
    private static int threadNum = 0;
    private static boolean ifStart = false;

    private static ExecutorService pool;
    public int getInt(){
        if(list.size()!=0){
            int i = list.get(0);
            list.remove(0);
            return i;
        }else{
            return 0;
        }
    }

    public void addInt(int row){
        list.add(row);
    }

    public static ExecutorService getPool(){
        return pool;
    }

    public static  void subThreadNum(){
        threadNum =threadNum-1;
    }

    public static void doExecutor(DoPrice doPrice){
        threadNum =threadNum+1;
        pool.execute(doPrice);
    }

    public static int getThreadNum() {
        return threadNum;
    }
    public static void setPool(int num){
        pool=Executors.newFixedThreadPool(num);
    }
    public static boolean isIfStart() {
        return ifStart;
    }

    public static void setIfStart(boolean ifStart) {
        PriceThreadDo.ifStart = ifStart;
    }
}
