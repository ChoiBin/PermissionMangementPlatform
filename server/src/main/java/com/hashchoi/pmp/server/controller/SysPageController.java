package com.hashchoi.pmp.server.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : choibin
 * @date : 12:27  2019/9/13 0013
 */
@Controller
public class SysPageController {


    @RequestMapping(value = {"index.html","/"})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "login.html")
    public String login(){
        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index.html";
        }
        return "login";
    }

    @RequestMapping(value = "main.html")
    public String main(){
        return "main";
    }

    @RequestMapping("404.html")
    public String notFound(){
        return "404";
    }
}
