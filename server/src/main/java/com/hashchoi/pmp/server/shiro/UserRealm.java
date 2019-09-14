package com.hashchoi.pmp.server.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hashchoi.pmp.model.entity.SysUserEntity;
import com.hashchoi.pmp.model.mapper.SysUserDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author : choibin
 * @date : 13:51  2019/9/13 0013
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 资源-权限分配  -授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户认证  登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        final String username = token.getUsername();
        final String password = String.valueOf(token.getPassword());
        LOGGER.info("用户名：{},密码：{}",token.getUsername(),String.valueOf(token.getPassword()));

        SysUserEntity entity = sysUserDao.selectOne(new QueryWrapper<SysUserEntity>().eq("username",username));
        //账户不存在
        if(entity == null){
            throw new UnknownAccountException("账户不存在！");
        }
        if(0 == entity.getStatus()){
            throw new DisabledAccountException("账户被禁用，请联系管理员！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(entity,entity.getPassword(), ByteSource.Util.bytes(entity.getSalt()),getName());
        return info;
    }

    /**
     * 密码匹配器
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
