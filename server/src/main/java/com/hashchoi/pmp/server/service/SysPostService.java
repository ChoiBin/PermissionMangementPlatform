package com.hashchoi.pmp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashchoi.pmp.common.utils.PageUtil;
import com.hashchoi.pmp.model.entity.SysPostEntity;

import java.util.Map;

/**
 * @author : choibin
 * @date : 14:05  2019/9/14 0014
 */
public interface SysPostService extends IService<SysPostEntity> {

    PageUtil queryPage(Map<String,Object> paramMap);

    void savePost(SysPostEntity entity);

    void updatePost(SysPostEntity entity);

    void deletePatch(Long[] ids);
}
