package com.example.model.vo;

import com.example.model.util.GoodUtillll;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Map;

@Data
public class OrderVO {
    @Transient
    private Map<String,Integer> skuMap;
}
