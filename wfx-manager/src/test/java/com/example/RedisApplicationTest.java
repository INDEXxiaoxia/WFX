package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void  testSaveToRedis(){
        /*
            redis中存储5种不同数据类型的方法
                存储基本数据类型
                存储list类型
                存储set类型
                存储hash类型

                存储Zset类型
         */
        //向redis中保存一个字符串
        redisTemplate.opsForValue().set("bossName","张三丰");
    }

    @Test
    public void testGetRedisByKey(){
        String bossName = (String) redisTemplate.opsForValue().get("bossName");
        System.out.println("redis中获取的值是："+bossName);
    }
}
