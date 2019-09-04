package com.example;

import com.example.model.SysModule;
import com.example.service.SysModuleService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.reflect.generics.tree.VoidDescriptor;

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



    @Test
    public void getMd5Password(){
        String password = "123456";
        String saltStr = "czbz";

        String hashAlgorithmName = "MD5";//加密方式
        ByteSource salt = ByteSource.Util.bytes(saltStr);//以账号作为盐值

        int hashIterations = 56;// 加密56次（注意这里配置的值要与shiro的配置文件中配置的值保持一致。否则无法解密

        SimpleHash hash = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        System.out.println(hash.toString());
    }
}
