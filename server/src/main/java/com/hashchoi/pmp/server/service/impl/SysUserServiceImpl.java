package com.hashchoi.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashchoi.pmp.model.entity.SysUserEntity;
import com.hashchoi.pmp.model.mapper.SysUserDao;
import com.hashchoi.pmp.server.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author : choibin
 * @date : 13:10  2019/9/14 0014
 */
@Service("sysUserServiceImpl")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    //更新密码
    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        SysUserEntity entity = new SysUserEntity();
        entity.setPassword(newPassword);
        return this.update(entity,new QueryWrapper<SysUserEntity>().eq("user_id",userId).eq("password",oldPassword));
    }
}
