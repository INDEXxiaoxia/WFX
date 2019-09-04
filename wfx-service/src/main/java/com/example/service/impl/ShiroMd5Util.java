package com.example.service.impl;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroMd5Util {

    /**
     * 将加密前的密码，通过加盐机制，进行多次循环加密
     *
     * @param password 加密前的原始密码
     * @param saltStr  加密的盐值
     * @return
     */
    public static String formatSysMd5(String password, String saltStr) {
        String hashAlgorithmName = "MD5";//加密方式

        ByteSource salt = ByteSource.Util.bytes(saltStr);//以账号作为盐值

        int hashIterations = 56;// 加密56次（注意这里配置的值要与shiro的配置文件中配置的值保持一致。否则无法解密

        SimpleHash hash = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);

        return hash.toString();
    }

    public static void main(String[] args) {
        String pass = formatSysMd5("123","admin");
        System.out.println(pass);
    }
}
