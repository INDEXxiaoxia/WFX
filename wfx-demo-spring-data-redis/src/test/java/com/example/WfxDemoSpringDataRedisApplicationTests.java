package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WfxDemoSpringDataRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        //存储同一个key的值时，后面会将前面同key的值覆盖
        redisTemplate.boundValueOps("componey1").set("千锋互联", 5, TimeUnit.SECONDS);
//        redisTemplate.boundValueOps("componey1").set("千锋互联2", 50, TimeUnit.SECONDS);

        redisTemplate.opsForValue().set("componey2", "千锋互联", 5, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("componey2", "千锋互联2", 5, TimeUnit.SECONDS);
    }

    @Test
    public void getRedisTest() {
        for (int i = 0 ; i < 7 ; i++){
            Object componey2 = redisTemplate.opsForValue().get("componey2");
            Object componey1 = redisTemplate.boundValueOps("componey1").get();
            System.out.println(componey1+","+componey2);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void setHashRedis(){
        BoundHashOperations department = redisTemplate.boundHashOps("department");
        department.put("yanfa",20);
        department.put("shichang",200);

        redisTemplate.opsForHash().put("department2","yanfa",30);
        redisTemplate.opsForHash().put("department2","shichang",300);
    }

    @Test
    public void getHashRedis(){
        Object yanfa = redisTemplate.boundHashOps("department").get("yanfa");
        Object yanfa2 = redisTemplate.opsForHash().get("department2","yanfa");

        System.out.println(yanfa+",,"+yanfa2);
    }



    @Test
    public void testModuleListSave(){
        /*
            要将 1.菜单列表的集合数据保存到redis
                2.查询时，要先判断redis中是否有数据，然后再选择从redis还是数据库查询
         */
        List<String> moduleNameList = new ArrayList<>();
        moduleNameList.add("系统功能");
        moduleNameList.add("系统功能2");
        moduleNameList.add("系统功能3");
        moduleNameList.add("系统功能4");
        moduleNameList.add("系统功能5");

        redisTemplate.opsForValue().set("moduleList",moduleNameList);

    }

    @Test
    public void testGetModuleList(){
        //条件查询
        /*
            先根据查询的条件，拼接Sql语句，再到数据库中查询
         */
        List<String> moduleList = (List<String>) redisTemplate.opsForValue().get("moduleList");
        for (String s : moduleList) {
            if (s.contains("2")){
                System.out.println(s);
            }
        }



    }
}
