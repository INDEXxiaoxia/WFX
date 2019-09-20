package com.example.model.vo;

import com.example.model.WxbCustomer;
import lombok.Data;

import javax.persistence.Transient;

@Data
public class WxbGoodVO {
    @Transient
    private WxbCustomer wxbCustomer;
}
