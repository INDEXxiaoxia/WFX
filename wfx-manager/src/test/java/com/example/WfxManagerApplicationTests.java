package com.example;

import com.example.model.SysModule;
import com.example.service.SysModuleService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WfxManagerApplicationTests {

    @Autowired
    private SysModuleService sysModuleService;

    @Test
    public void contextLoads() {
        PageInfo<SysModule> pageInfo = sysModuleService.findAll(1,10);
        for (SysModule sysModule : pageInfo.getList()) {
            System.out.println(sysModule);
        }
    }


}
