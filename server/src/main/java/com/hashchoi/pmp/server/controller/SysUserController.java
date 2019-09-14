package com.hashchoi.pmp.server.controller;

import com.google.common.collect.Maps;
import com.hashchoi.pmp.common.response.BaseResponse;
import com.hashchoi.pmp.common.response.StatusCode;
import com.hashchoi.pmp.model.entity.SysUserEntity;
import com.hashchoi.pmp.server.service.SysUserService;
import com.hashchoi.pmp.server.shiro.ShiroUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author : choibin
 * @date : 12:36  2019/9/14 0014
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService userService;


    //获取用户登录信息
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public BaseResponse currInfo(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = Maps.newHashMap();
        try {
            resMap.put("user",getCurrLoginUser());
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail);
        }
        response.setData(resMap);
        return response;
    }

    //修改登录密码
    @RequestMapping("/password")
    public BaseResponse updatePassword(String password,String newPassword){
        if(StringUtils.isBlank(password) || StringUtils.isBlank(newPassword)){
            return new BaseResponse(StatusCode.PasswordCanNotBlank);
        }
        try {
            //先检验旧密码，后改成新密码
            SysUserEntity user = getCurrLoginUser();
            final String salt = user.getSalt();

            String oldPas = ShiroUtil.sha256(password,salt);
            if(!user.getPassword().equals(oldPas)){
                return new  BaseResponse(StatusCode.OldPasswordNotMatch);
            }
            String newPas = ShiroUtil.sha256(newPassword,salt);

            if(oldPas.equals(newPas)){
                return new BaseResponse(StatusCode.OldpasswordEqualNewPassword);
            }

            //执行更新密码的逻辑
            userService.updatePassword(user.getUserId(),oldPas,newPas);
        }catch (Exception e){
            return  new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return  new BaseResponse(StatusCode.Success);
    }



}
