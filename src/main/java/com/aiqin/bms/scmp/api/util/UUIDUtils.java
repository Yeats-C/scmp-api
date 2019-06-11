package com.aiqin.bms.scmp.api.util;

import java.util.UUID;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-16
 * @time: 20:31
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成5位随机数  hzy
     * @return
     */
    public static String randomNumber() {

        String randomNumber = ""; //随机数

        final int num = 5; //5位

        int Random[] = new int[num];
        for(int i = 0 ; i < num ; i++)
        {
            // int ran=-1;
            while(true)
            {
                int ran = (int)(num*Math.random());
                for(int j = 0 ; j < i ; j++)
                {
                    if(Random[j] == ran)
                    {
                        ran = -1;
                        break;
                    }
                }
                if(ran != -1)
                {
                    Random[i] = ran;
                    break;
                }
            }

        }
        for(int i = 0 ; i < num ; i ++)
        {
            randomNumber +=Random[i];
        }
        return randomNumber;
    }
}
