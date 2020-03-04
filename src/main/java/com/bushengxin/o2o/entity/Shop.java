package com.bushengxin.o2o.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Shop {
    private Area area;
    private PersonInfo owner;
    private ShopCategory shopCategory;
    private Long shopId;
//    private Long ownerId;
//    private Long shopCategoryId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String phone;
    private String shopImg;
//    private Double longitude;
//    private Double latitude;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //-1.不可用 0.审核中 1.可用
    private Integer enableStatus;
    private String advice;
//    private List<ShopAuthMap> staffList;
//    private ShopCategory parentCategory;
}
