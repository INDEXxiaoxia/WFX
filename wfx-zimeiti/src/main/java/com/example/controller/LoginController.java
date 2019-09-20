package com.example.controller;

import com.example.Dao.MyWxbGoodRepository;
import com.example.model.WxbGood;
import com.example.model.WxbMemeber;
import com.example.service.GoodService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    GoodService goodService;
    @Autowired
    MyWxbGoodRepository myWxbGoodRepository;

    @RequestMapping("l")
    public String LoginViews(){
        return "login";
    }

    @RequestMapping("/loginIt")
    @ResponseBody
    public String LoginWXBMember(Model model,String username,String password){// WxbMemeber wxbMemeber,
        WxbMemeber wxbMemeber=new WxbMemeber();
        wxbMemeber.setAccount(username);
        wxbMemeber.setPassword(password);
        UsernamePasswordToken token=new UsernamePasswordToken(wxbMemeber.getAccount(),wxbMemeber.getPassword());
        try {
            SecurityUtils.getSubject().login(token);
            importDataToEsLogin();
        }catch (Exception e){
            System.out.println("登录失败！！！");
            return "false";
        }
        return "true";
    }
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        //退出操作成功后，做了重定向的操作访问是一个 完全不存在的请求。
        return "redirect:/login/l";//这个重定向的请求可以写任何url，但是不能写被shiro进行了anon的请求
    }
    //向es中插入原始数据
    public void importDataToEsLogin() {
        List<WxbGood> wxbGoods = goodService.selectGoodListBy_cid_tid_stype("","","");
        for (WxbGood wxbGood : wxbGoods) {
            wxbGood.setTheleval(wxbGood.getWxbCustomer().getLevel());

            myWxbGoodRepository.save(wxbGood);
        }
    }
}
