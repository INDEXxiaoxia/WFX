package com.example.model;

import com.example.model.vo.WxbGoodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
//implements Serializable
@Table
@Document(indexName = "idx_wxb_good")
public class WxbGood extends WxbGoodVO implements Cloneable, Serializable {


    @Id
    @org.springframework.data.annotation.Id
    @Field(type = FieldType.Text)
    private String goodId;
    private String goodName;
    private String customerId;
    private String goodPic;
    private String goodPic1;
    private String goodPic2;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",store = true)
    private String promoteDesc;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",store = true)
    private String skuTitle;
    private String skuCost;
    private String skuPrice;
    private String skuPmoney;
    private String copyIds;
    private String copyDesc;
    private String forwardLink;
    private Long orderNo;
    private String typeId;
    private String tags;
    private Long state;
    @Transient
    private String stateStr;
    private java.util.Date createTime;
    private Long toped;
    private Long recomed;
    private java.util.Date topedTime;
    private java.util.Date recomedTime;
    private String spcId;
    private String zonId;
    private Long sellNum;
    private String website;
    private Long iswxpay;
    private Long isfdfk;
    private Long leixingId;
    private String kfqq;
    @Transient
    private Long theleval;
    @Transient
    private List<String> SkuStringList;
    @Transient
    private List<String> tageStringList;

    public List<String> getTageStringList() {
        List<String> tageStringList = new ArrayList<>();
        if (!(this.tags==""||this.tags==null)){
            String[] split = this.tags.split(",");
            tageStringList=Arrays.asList(split);
        }else {
            tageStringList=null;
        }
        return tageStringList;
    }


    public Long getTheleval() {
        return theleval;
    }

    public void setTheleval(Long theleval) {
        this.theleval = theleval;
    }


    public List<String> getSkuStringList() {
        List<String> SkuStringList = new ArrayList<>();
        if (this.getSkuTitle() != null && this.getSkuTitle() != ""&&this.getSkuTitle().length()!=0) {

            String[] splitTitle = this.getSkuTitle().split("\\|");
            String[] splitPrice = this.getSkuPrice().split("\\|");
//            String[] splitCost = this.getSkuCost().split("|");
            String[] splitPmoney = this.getSkuPmoney().split("\\|");
            for (int i = 0; i < splitTitle.length; i++) {
                SkuStringList.add(
                        String.format("套餐:%s单价:%s提成:%s", splitTitle[i], splitPrice[i], splitPmoney[i])
                );
            }

        } else {
            SkuStringList.add("无套餐");
        }
        return SkuStringList;
    }
    @Override
    public Object clone() {
        WxbGood gg=new WxbGood();
        try {
            gg=(WxbGood)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return gg;
    }
    
    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }


    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getGoodPic() {
        return goodPic;
    }

    public void setGoodPic(String goodPic) {
        this.goodPic = goodPic;
    }


    public String getGoodPic1() {
        return goodPic1;
    }

    public void setGoodPic1(String goodPic1) {
        this.goodPic1 = goodPic1;
    }


    public String getGoodPic2() {
        return goodPic2;
    }

    public void setGoodPic2(String goodPic2) {
        this.goodPic2 = goodPic2;
    }


    public String getPromoteDesc() {
        return promoteDesc;
    }

    public void setPromoteDesc(String promoteDesc) {
        this.promoteDesc = promoteDesc;
    }


    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }


    public String getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(String skuCost) {
        this.skuCost = skuCost;
    }


    public String getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(String skuPrice) {
        this.skuPrice = skuPrice;
    }


    public String getSkuPmoney() {
        return skuPmoney;
    }

    public void setSkuPmoney(String skuPmoney) {
        this.skuPmoney = skuPmoney;
    }


    public String getCopyIds() {
        return copyIds;
    }

    public void setCopyIds(String copyIds) {
        this.copyIds = copyIds;
    }


    public String getCopyDesc() {
        return copyDesc;
    }

    public void setCopyDesc(String copyDesc) {
        this.copyDesc = copyDesc;
    }


    public String getForwardLink() {
        return forwardLink;
    }

    public void setForwardLink(String forwardLink) {
        this.forwardLink = forwardLink;
    }


    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    String[] stateArray = {"待审核", "已上架", "已下架"};

    public String getStateStr() {
        return stateArray[this.state.intValue() % 3];
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }


    public Long getToped() {
        return toped;
    }

    public void setToped(Long toped) {
        this.toped = toped;
    }


    public Long getRecomed() {
        return recomed;
    }

    public void setRecomed(Long recomed) {
        this.recomed = recomed;
    }


    public java.util.Date getTopedTime() {
        return topedTime;
    }

    public void setTopedTime(java.util.Date topedTime) {
        this.topedTime = topedTime;
    }


    public java.util.Date getRecomedTime() {
        return recomedTime;
    }

    public void setRecomedTime(java.util.Date recomedTime) {
        this.recomedTime = recomedTime;
    }


    public String getSpcId() {
        return spcId;
    }

    public void setSpcId(String spcId) {
        this.spcId = spcId;
    }


    public String getZonId() {
        return zonId;
    }

    public void setZonId(String zonId) {
        this.zonId = zonId;
    }


    public Long getSellNum() {
        return sellNum;
    }

    public void setSellNum(Long sellNum) {
        this.sellNum = sellNum;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public Long getIswxpay() {
        return iswxpay;
    }

    public void setIswxpay(Long iswxpay) {
        this.iswxpay = iswxpay;
    }


    public Long getIsfdfk() {
        return isfdfk;
    }

    public void setIsfdfk(Long isfdfk) {
        this.isfdfk = isfdfk;
    }


    public Long getLeixingId() {
        return leixingId;
    }

    public void setLeixingId(Long leixingId) {
        this.leixingId = leixingId;
    }


    public String getKfqq() {
        return kfqq;
    }

    public void setKfqq(String kfqq) {
        this.kfqq = kfqq;
    }

    @Override
    public String toString() {
        return "WxbGood{" +
                "goodId='" + goodId + '\'' +
                ", goodName='" + goodName + '\'' +
                ", customerId='" + customerId + '\'' +
                ", goodPic='" + goodPic + '\'' +
                ", goodPic1='" + goodPic1 + '\'' +
                ", goodPic2='" + goodPic2 + '\'' +
                ", promoteDesc='" + promoteDesc + '\'' +
                ", skuTitle='" + skuTitle + '\'' +
                ", skuCost='" + skuCost + '\'' +
                ", skuPrice='" + skuPrice + '\'' +
                ", skuPmoney='" + skuPmoney + '\'' +
                ", copyIds='" + copyIds + '\'' +
                ", copyDesc='" + copyDesc + '\'' +
                ", forwardLink='" + forwardLink + '\'' +
                ", orderNo=" + orderNo +
                ", typeId='" + typeId + '\'' +
                ", tags='" + tags + '\'' +
                ", state=" + state +
                ", stateStr='" + stateStr + '\'' +
                ", createTime=" + createTime +
                ", toped=" + toped +
                ", recomed=" + recomed +
                ", topedTime=" + topedTime +
                ", recomedTime=" + recomedTime +
                ", spcId='" + spcId + '\'' +
                ", zonId='" + zonId + '\'' +
                ", sellNum=" + sellNum +
                ", website='" + website + '\'' +
                ", iswxpay=" + iswxpay +
                ", isfdfk=" + isfdfk +
                ", leixingId=" + leixingId +
                ", kfqq='" + kfqq + '\'' +
                ", stateArray=" + Arrays.toString(stateArray) +
                '}';
    }
}
