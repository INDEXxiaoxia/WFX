package com.example.controller;

import com.example.model.SysModule;
import com.example.service.WeekModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/week")
public class WeektestController {
    @Autowired
    private WeekModuleService weekModuleService;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String MREDIS = "MREDIS";


    @RequestMapping("/find")
    public String weekTest(Model model, String mname, String mid) {
        List<SysModule> moduleList = new ArrayList<>();
        String rmid= (String) redisTemplate.boundHashOps(MREDIS).get("mid");
        String rmname= (String) redisTemplate.boundHashOps(MREDIS).get("mname");
        System.out.println(redisTemplate.boundHashOps(MREDIS).get("moduleList"));
        System.out.println(mid+"-"+rmid);
        if (mid ==rmid &&mname == rmname) {//取redis中的列表

            System.out.println("搜索结果相同，正在从redis获取数据");
            moduleList = (List<SysModule>) redisTemplate.boundHashOps(MREDIS).get("moduleList");
        } else {
            System.out.println("数据库正在查询");
             moduleList = weekModuleService.findModuleByparm(mname, mid);
            redisTemplate.boundHashOps(MREDIS).put("mid",mid);
            redisTemplate.boundHashOps(MREDIS).put("mname",mname);
            redisTemplate.boundHashOps(MREDIS).put("moduleList",moduleList);
        }

//        System.out.println(moduleList);
        model.addAttribute("moduleList", moduleList);
        return "inWeek";
    }

    @RequestMapping("/show")
    public String weekShow() {
        return "week";
    }
}
