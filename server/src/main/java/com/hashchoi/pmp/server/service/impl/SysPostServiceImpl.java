package com.hashchoi.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.hashchoi.pmp.common.response.StatusCode;
import com.hashchoi.pmp.common.utils.CommonUtil;
import com.hashchoi.pmp.common.utils.PageUtil;
import com.hashchoi.pmp.common.utils.QueryUtil;
import com.hashchoi.pmp.model.entity.SysPostEntity;
import com.hashchoi.pmp.model.mapper.SysPostDao;
import com.hashchoi.pmp.server.service.SysPostService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @author : choibin
 * @date : 14:06  2019/9/14 0014
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysPostServiceImpl.class);

    //分页模糊查询
    @Override
    public PageUtil queryPage(Map<String, Object> paramMap) {
        String search = (paramMap.get("search") == null ? "" : paramMap.get("search").toString());

        //调用自封转的分页查询工具
        IPage<SysPostEntity> queryPage = new QueryUtil<SysPostEntity>().getPage(paramMap);

        QueryWrapper wrapper = new QueryWrapper<SysPostEntity>().like(StringUtils.isNotBlank(search),"post_code",search.trim())
                .or(StringUtils.isNotBlank(search))
                .like(StringUtils.isNotBlank(search),"post_name",search.trim());
        IPage<SysPostEntity> resPage = this.page(queryPage,wrapper);

        LOGGER.info("查询结果{}",resPage);
        return new PageUtil(resPage);
    }

    //新增岗位
    @Override
    public void savePost(SysPostEntity entity) {
        if (this.getOne(new QueryWrapper<SysPostEntity>().eq("post_code",entity.getPostCode())) != null){
            throw new RuntimeException(StatusCode.PostCodeHasExist.getMsg());
        }
        entity.setCreateTime(DateTime.now().toDate());
        save(entity);
    }

    //修改岗位
    @Override
    public void updatePost(SysPostEntity entity) {
        SysPostEntity old = this.getById(entity.getPostId());
        if(old != null && !old.getPostCode().equals(entity.getPostCode())){
            if(this.getOne(new QueryWrapper<SysPostEntity>().eq("post_code",entity.getPostCode())) != null){
                throw new RuntimeException(StatusCode.PostCodeHasExist.getMsg());
            }
        }
        entity.setUpdateTime(DateTime.now().toDate());
        updateById(entity);
    }

    //批量删除
    @Override
    public void deletePatch(Long[] ids) {
        //写法一
        //removeByIds(Arrays.asList(ids));

        //写法二
        String delIds = Joiner.on(",").join(ids);
        baseMapper.deleteBatch(CommonUtil.concatStrToInt(delIds,","));

    }
}
