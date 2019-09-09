package com.example.mapper;

import com.example.model.WxbGood;
import tk.mybatis.mapper.common.Mapper;
// 接口实现tkmapper,即可对当前表进行CRUD操作
public interface GoodMapper extends Mapper<WxbGood> {
}
