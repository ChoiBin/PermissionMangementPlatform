package com.hashchoi.pmp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashchoi.pmp.model.entity.SysUserEntity;
import org.springframework.stereotype.Service;

/**
 * @author : choibin
 * @date : 13:07  2019/9/14 0014
 */
public interface SysUserService extends IService<SysUserEntity> {

    boolean updatePassword(Long userId,String oldPassword,String newPassword);


}
