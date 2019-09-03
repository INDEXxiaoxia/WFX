//package com.example.config;
//
//import com.example.service.WfxRealm;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//
//@Configuration//相当于是一个xml文件
//public class ShiroConfig {
//
//    // 1.配置 shiroFilter
//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
//        //哪些请求可以匿名访问
//        chain.addPathDefinition("/demo/**","anon");
//        chain.addPathDefinition("/plugins/**", "anon");
//        chain.addPathDefinition("/lib/**", "anon");
//        chain.addPathDefinition("/static/**", "anon");
//        //除了以上的请求外，其它请求都需要登录
//        chain.addPathDefinition("/**", "authc");
//        return chain;
//    }
//
//    // 2. 配置SecurityManager
//    @Bean
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(wfxRealm());
//        return securityManager;
//    }
//
//    // 3. 配置自定义的Realm
//    @Bean
//    public WfxRealm wfxRealm() {
//        return new WfxRealm();
//    }
//
//    // 4.初始化Shiro的生命周期
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//
//    /**
//     * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
//     * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，
//     * 导致返回404。加入这项配置能解决这个bug
//     */
//    @Bean
//    @DependsOn("lifecycleBeanPostProcessor")
//    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//}
