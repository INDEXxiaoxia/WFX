package com.qianfeng.repeat.controller;

import com.qianfeng.repeat.model.UserInfo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class MyController {

    @RequestMapping("/myinit")
    public HashMap myInit() {
        HashMap map = new HashMap();
        map.put("message", "springmvc 框架基础环境准备成功");
        return map;
    }

    //复习前后端传参问题
    /*
        前后端传参，归纳均为 Object类型
            Object类型：
                基本数据类型
                    get/post/put/delete
                对象类型
                集合类型



     */
    //参数为基本数据类型，直接在方法的参数列表中来接收值，GET请求
    @RequestMapping("/testGetParam")
    public HashMap testGetParam(Integer id, String name, Double salary) {
        HashMap map = new HashMap();
        map.put("id", id);
        map.put("name", name);
        map.put("salary", salary);
        return map;
    }

    //路径参数
    /*
        路径参数：配套使用：
                前端传参要求： 参数的值直接接在url后面，且以 “/"结尾
                后端方法接参： 在URL上，通过 { 名称自已定义}
                            方法的参数名上，必须添加注解@PathVariable("注解中的名称需要与 URL中参数名相同）

        这种传参方式属于：restful风格

     */
    @RequestMapping("/testPathParam/{id}/{name}/{password}")
    public HashMap testPathParam(@PathVariable("id") String id, @PathVariable("name") String name, @PathVariable("password") String password) {
        HashMap map = new HashMap();

        map.put("id", id);
        map.put("name", name);
        map.put("password", password);

        return map;
    }

    //使用这种方式，对象类型接收参数时，一般不使用GET类型请求
    // 如果前后端，采用的异步请求，参数类型为对象的时候，必须要添加 @RequestBody
    @RequestMapping(value = "/testGetObject")
    //RequestBody直接将当前对象的类型中的属性进行与请求中的参数进行直接匹配
    public UserInfo testGetObject(@RequestBody UserInfo userInfo) {
//        HashMap map = new HashMap();
//        map.put("id", id);
//        map.put("name", name);
//        map.put("salary", salary);
        return userInfo;

    }
}
