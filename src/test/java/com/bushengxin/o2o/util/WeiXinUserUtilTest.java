package com.bushengxin.o2o.util;

import com.bushengxin.o2o.util.weixin.WeiXinUserUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class WeiXinUserUtilTest {

    @Test
    public void testGetUserAccessToken() throws IOException {
        Properties pro = new Properties();
        pro.load(WeiXinUserUtil.class.getClassLoader().getResourceAsStream("weixin.properties"));
        System.out.println(pro.getProperty("weixinappid"));
        String appId = DESUtils.getDecryptString(pro.getProperty("weixinappid"));
        String appsecret = DESUtils.getDecryptString(pro.getProperty("weixinappsecret"));
        System.out.println("appId="+appId);
        System.out.println("appsecret="+appsecret);
    }
}
