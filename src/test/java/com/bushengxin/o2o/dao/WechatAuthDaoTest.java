package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.entity.Area;
import com.bushengxin.o2o.entity.WechatAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WechatAuthDaoTest extends BaseTest {
    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    public void testQueryWechatInfoByOpenId(){
       WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("");
    }
}
