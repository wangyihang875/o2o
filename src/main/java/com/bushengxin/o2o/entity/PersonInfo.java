package com.bushengxin.o2o.entity;

import lombok.Data;
import java.util.Date;

@Data
public class PersonInfo {
    private Long userId;
    private String name;
    private String email;
    private String profileImg;
    private Date birthday;
    private String gender;
    private String phone;
    private Integer customerFlag;
    private Integer shopOwnerFlag;
    private Integer adminFlag;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
}
