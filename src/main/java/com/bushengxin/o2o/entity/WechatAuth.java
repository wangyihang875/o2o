package com.bushengxin.o2o.entity;

import lombok.Data;
import java.util.Date;

@Data
public class WechatAuth {
    private Long wechatAuthId;
    private Long userId;//为方便可以将psersonInfo的id提出来
    private String openId;
    private Date createTime;
    private PersonInfo personInfo;
}
