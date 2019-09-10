package com.example.payUtil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weixin")
@PropertySource("classpath:weixin_pay.properties")
public class WeixinPayConfig {
//    weixin.app_id=wx632c8f211f8122c6
//    weixin.mch_id=1497984412
//    weixin.api_key=sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC
    private String app_id;
    private String mch_id;
    private String api_key;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    @Override
    public String toString() {
        return "WeixinPayConfig{" +
                "app_id='" + app_id + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", api_key='" + api_key + '\'' +
                '}';
    }
}
