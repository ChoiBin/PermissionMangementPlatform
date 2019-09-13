package com.hashchoi.pmp.server.controller;

import com.hashchoi.pmp.common.response.BaseResponse;
import com.hashchoi.pmp.common.response.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : choibin
 * @date : 20:33  2019/9/12 0012
 */
@Controller
@RequestMapping("/base")
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse info(String name){
        BaseResponse response = new BaseResponse(StatusCode.Success);

        if(StringUtils.isBlank(name)){
            name = "权限管理平台";
        }
        response.setData(name);
        return response;

    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public String page(String name, ModelMap modelMap){
        modelMap.put("name",name);
        modelMap.put("test","test");
        return "pageOne";
    }
}
