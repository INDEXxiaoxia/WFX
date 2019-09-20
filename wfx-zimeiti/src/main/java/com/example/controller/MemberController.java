package com.example.controller;

import com.example.Dao.MyUseTool;
import com.example.Dao.MyWxbGoodRepository;
import com.example.mapper.GoodMapper;
import com.example.model.WxbCustomer;
import com.example.model.WxbGood;
import com.example.model.WxbGoodType;
import com.example.model.WxbMemeber;
import com.example.model.util.ToTubcUse;
import com.example.model.util.ToVexm;
import com.example.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wxbM")
public class MemberController {
    @Autowired
    GoodService goodService;
    @Autowired
    CustomerService customerService;
    @Autowired
    GoodTypeService goodTypeService;
    @Autowired
    MyWxbGoodRepository myWxbGoodRepository;
    @Autowired
    EsServiceTest esServiceTest;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    RsServiceTest rsServiceTest;
    @Autowired
    WxbOrderService wxbOrderService;
    @RequestMapping("/01")
//    @ResponseBody
    public String testes(Model model) {
        WxbMemeber ThisMember = (WxbMemeber) SecurityUtils.getSubject().getPrincipal();
        ToTubcUse toTubcUseParam=wxbOrderService.findTubcPar(ThisMember.getMemeberId());
        model.addAttribute("ThisMember", ThisMember);
        model.addAttribute("toTubcUseParam", toTubcUseParam);
        return "index";
    }
    @RequestMapping("/getvexm")
    @ResponseBody
    //折线图
    public List<ToVexm> getvexm(Model model){
        WxbMemeber ThisMember = (WxbMemeber) SecurityUtils.getSubject().getPrincipal();
        List<ToVexm> toVexmList=wxbOrderService.findToxmList(ThisMember.getMemeberId());
        return toVexmList;
    }
    //圆形图标
    @RequestMapping("/getTubc")
    @ResponseBody
    public ToTubcUse getTubc(){
        WxbMemeber ThisMember = (WxbMemeber) SecurityUtils.getSubject().getPrincipal();
        ToTubcUse toTubcUseParam=wxbOrderService.findTubcPar(ThisMember.getMemeberId());
        return toTubcUseParam;
    }



    @RequestMapping("/MemberSeeGoods")
    public String MemberSeeGoods(Model model, String customer_id, String type_id, String sort_type,String alisaname,Integer pageP,Integer sizeP) {

        if (customer_id == null) customer_id = "";
        if (type_id == null) type_id = "";
        if (sort_type == null) sort_type = "";
        if (alisaname == null) alisaname = "";
        if (pageP == null) pageP = 0;
        //获取shiro
        WxbMemeber ThisMember = (WxbMemeber) SecurityUtils.getSubject().getPrincipal();



//        List<WxbGood> wxbGoodList = goodService.selectGoodListBy_cid_tid_stype(customer_id, type_id, sort_type);
//        List<WxbGood> wxbGoodList =findGLByCidTidGnameOrderByStp(customer_id,type_id,sort_type,alisaname);
        AggregatedPage<WxbGood> wxbGoodList =null;
//        if (rsServiceTest.isSamee(customer_id, type_id, sort_type, alisaname, pageP, sizeP)){
            //查询结果与redis中相同
            //取redis中的列表
//             wxbGoodList = rsServiceTest.getPagelist();
//        }else {
            //查询条件不同，
            // 使用RS查询 并将查询到的数据存入redis
            wxbGoodList = esServiceTest.testEss2(customer_id, type_id, sort_type, alisaname, pageP, sizeP);
//            rsServiceTest.savePageList(wxbGoodList);
//        }


        //下拉框 从redis中取
//        List<WxbCustomer> wxbCustomerList = customerService.findAll();
//        List<WxbGoodType> wxbGoodTypeList = goodTypeService.findAll();
//        rsServiceTest.SaveDownPullList(wxbCustomerList,wxbGoodTypeList);
        List<WxbCustomer> wxbCustomerList=rsServiceTest.getwxbCustomerListR();
        List<WxbGoodType> wxbGoodTypeList=rsServiceTest.getwxbGoodTypeListR();

//        回显数据
        WxbCustomer SelectwxbCustomer = new WxbCustomer();
        WxbGoodType SelectwxbGoodType = new WxbGoodType();
        if (customer_id == null || customer_id == "") {
            SelectwxbCustomer.setCustomerId("");
            SelectwxbCustomer.setCustomerName("选择商户");
        } else {
            SelectwxbCustomer = customerService.selectById(customer_id);

        }
        if (type_id == null || type_id == "") {
            SelectwxbGoodType.setTypeId("");
            SelectwxbGoodType.setTypeName("商品分类");
        } else {

            SelectwxbGoodType = goodTypeService.selectById(type_id);
        }
        String sortString="默认排序";
        if (sort_type.equals("toped")){
            sortString="按商品置顶排序";
        }else if (sort_type.equals("recomed")){
            sortString="按商品推荐排序";
        }else if (sort_type.equals("level")){
            sortString="按商户等级排序";
        }
        model.addAttribute("alisaname",alisaname);
        model.addAttribute("sort_type",sort_type);
        model.addAttribute("sortString",sortString);
        model.addAttribute("wxbGoodList", wxbGoodList);
        model.addAttribute("ThisMember", ThisMember);
        model.addAttribute("wxbCustomerList", wxbCustomerList);
        model.addAttribute("wxbGoodTypeList", wxbGoodTypeList);
        model.addAttribute("SelectwxbCustomer", SelectwxbCustomer);
        model.addAttribute("SelectwxbGoodType", SelectwxbGoodType);

        return "产品及文案";
    }


    //--------------------------ESSSSSSSSSSSSSSSSSSSSSSSSSSSSS
    @RequestMapping("/022")
    public String INData(){
        importDataToEs();
        return "index";
    }
    //向es中插入原始数据
    public void importDataToEs() {
        List<WxbGood> wxbGoods = goodService.selectGoodListBy_cid_tid_stype("","","");
        for (WxbGood wxbGood : wxbGoods) {
            wxbGood.setTheleval(wxbGood.getWxbCustomer().getLevel());

            myWxbGoodRepository.save(wxbGood);
        }
    }

    public List<WxbGood> findGLByCidTidGnameOrderByStp(String customer_id, String type_id, String sort_type, String alisaname) {
        if (customer_id==null) customer_id="";
        if (type_id==null) customer_id="";
        if (sort_type==null) sort_type="";
        if (alisaname==null) alisaname="";
        List<WxbGood> wxbGoodList = new ArrayList<>();

//        if (sort_type == "toped") {
//            wxbGoodList = myWxbGoodRepository.findByCustomerIdAndTypeIdAndGoodNameLikeOrderByToped(customer_id, type_id, alisaname);
//        } else if (sort_type == "recommed") {
//            wxbGoodList = myWxbGoodRepository.findByCustomerIdAndTypeIdAndGoodNameLikeOrderByRecomed(customer_id, type_id, alisaname);
//        } else {
//            wxbGoodList = myWxbGoodRepository.findByCustomerIdAndTypeIdAndGoodNameLike(customer_id, type_id, alisaname);
//        }

        wxbGoodList = esServiceTest.findGLByCidTidGnameOrderByStp(customer_id, type_id, sort_type, alisaname);
        for (WxbGood wxbGood : wxbGoodList) {
            wxbGood.setWxbCustomer(customerService.findById(wxbGood.getCustomerId()));
        }

        return wxbGoodList;
    }

//--------------------------ESSSSSSSSSSSSSSSSSSSSSSSSSSSSS
}
