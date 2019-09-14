package com.hashchoi.pmp.server.controller;

import com.hashchoi.pmp.model.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @author : choibin
 * @date : 13:00  2019/9/13 0013
 */
@Controller
public abstract class AbstractController {

    //日志
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getCurrLoginUser(){
        //获取当前登录用户的详情
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    protected Long getUserId(){
        return getCurrLoginUser().getUserId();
    }

    protected String getUserName(){
        return getCurrLoginUser().getUsername();
    }

    protected  Long getDeptId(){
        return getCurrLoginUser().getDeptId();
    }


}
