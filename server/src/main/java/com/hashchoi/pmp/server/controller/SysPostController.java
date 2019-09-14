package com.hashchoi.pmp.server.controller;

import com.google.common.collect.Maps;
import com.hashchoi.pmp.common.response.BaseResponse;
import com.hashchoi.pmp.common.response.StatusCode;
import com.hashchoi.pmp.common.utils.PageUtil;
import com.hashchoi.pmp.common.utils.ValidatorUtil;
import com.hashchoi.pmp.model.entity.SysPostEntity;
import com.hashchoi.pmp.server.service.SysPostService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author : choibin
 * @date : 14:02  2019/9/14 0014
 */
@RestController
@RequestMapping("/sys/post")
public class SysPostController extends AbstractController{



    @Autowired
    private SysPostService sysPostService;

    @RequestMapping("list")
    public BaseResponse list(@RequestAttribute Map<String,Object> paramMap){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = Maps.newHashMap();

        try {
          PageUtil page =  sysPostService.queryPage(paramMap);
          resMap.put("page",page);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail);
        }

        response.setData(resMap);
        return response;
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse save(@RequestBody @Validated SysPostEntity entity, BindingResult result){
        String res = ValidatorUtil.checkResult(result);
        if(StringUtils.isBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            logger.info("新增岗位：{}",entity);
            sysPostService.savePost(entity);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //获取详情
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse info(@PathVariable Long id){
        if(id == null || id <= 0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = Maps.newHashMap();
        try{
            logger.info("岗位详情：{}",id);
            resMap.put("post",sysPostService.getById(id));
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);
        return response;
    }


    //进行更新
    @RequestMapping(value = "/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse update(@RequestBody @Validated SysPostEntity entity, BindingResult result){
        String res = ValidatorUtil.checkResult(result);
        if(StringUtils.isBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        if(entity.getPostId() == null || entity.getPostId() <= 0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            logger.info("修改岗位：{}",entity);
            sysPostService.updatePost(entity);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //进行删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse update(@RequestBody Long[] ids){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            logger.info("删除岗位接收到的数据：{}", Arrays.asList(ids));
            sysPostService.deletePatch(ids);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


}
