# 企业员工角色权限管理平台
## 介绍

​	该项目主要是用作大四学期的练手项目，为后面毕业设计打下基础，顺带学习一下这个管理平台当中的技术，特别是权限这一块的技术。

​	那么首先先介绍一下这个项目，这个企业级应用系统中后端应用权限的管理，主要涵盖了六大核心业务模块、十几张数据库表，可以基于此去做**企业级应用系统的二次开发，甚至可以用于商用**！其中的核心业务模块主要包括用户模块、部门模块、岗位模块、角色模块、菜单模块和系统日志模块；除此以外还有额外的模块，包括字典管理模块、商品分类模块以及考勤管理模块等等，主要是为了更好地巩固相应的技术栈以及企业应用系统业务模块的开发流程！

## 	涉及到的技术栈

​	该项目在技术栈层面涵盖了前端和后端的大部分常用技术，包括Spring Boot、Spring MVC、Mybatis、Mybatis-Plus、Shiro(身份认证与资源授权跟会话等等)、Spring AOP、防止XSS攻击、防止SQL注入攻击、过滤器Filter、验证码Kaptcha、热部署插件Devtools、POI、Vue、LayUI、ElementUI、JQuery、HTML、Bootstrap、Freemarker、一键打包部署运行工具Wagon等等，如下图所示：
  ![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/1.png)
  
##  项目中可以学到什么
  ![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/2.png)
  熟悉系统开发与运行流程
  ![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/3.png)
## 开发流程

### 项目搭建

#### 搭建模块
![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/4.png)

#### 体验MVC开发流程
![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/5.png)

  项目搭建完毕后，编写代码进行测试的过程中，发现每当前后端修改完代码都要重新启动项目，这势必会影响以后的开发效率，所以在项目开发过程中打算引入热部署插件Devtools，引入这个热部署插件后，修改完前后端代码，IDEA都会帮我们自动重启编译，省去了重新启动项目的麻烦。

**引入Spring-boot-devtools 的依赖Jar**

```xml
       <!-- 引入热部署jar包 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <!-- optional=true,依赖不会传递，该项目依赖devtools；之后依赖该项目的项目如果想要使用devtools，需要重新引入 -->
            <optional>true</optional>
        </dependency>

....省略其他依赖

	<!-- 引入插件 -->
         <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>

                <configuration>
                    <!-- IntelliJ IDEA本地测试去掉fork也生效 -->
                    <fork>true</fork>
                </configuration>
            </plugin>
```

**开启 IDEA 自动编译及 automake 功能**

打开idea-File-settings-Build,Execution,Deployment-Compiler-勾选Build project automatically
![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/6.png)
按住Ctrl+shift+alt+/，点击Register，勾选compiler.automake.allow.when.app.running
![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/7.png)

## 项目中的核心技术点

### 1、使用shiro来进行身份验证

## 身份验证

因为之前没有学过shiro，在这个项目中第一次使用shiro，入门shiro可以参看[W3C shiro学习](https://www.w3cschool.cn/shiro/andc1if0.html)

首先先配置一个shiro配置类**ShiroConfig**，里面配置了SecurityManager、ShiroFilterFactoryBean、LifecycleBeanPostProcessor、AuthorizationAttributeSourceAdvisor四个bean，这几个bean具体是什么下面一一解释。

**securityManager**：安全管理器；即所有与安全有关的操作都会与 SecurityManager 交互；且它管理着所有 Subject；它是 Shiro 的核心，它负责与其他组件进行交互，可以把它看成Spring MVC中的DispatcherServlet 前端控制器。

**ShiroFilterFactoryBean**：Shiro提供了与Web集成的支持，其通过一个`ShiroFilter`入口来拦截需要安全控制的URL，然后进行相应的控制。可以用LinkedHashMap添加拦截的uri，其中authc指定需要认证的uri，anon指定排除认证的uri。

LifecycleBeanPostProcessor：用来管理shiro一些bean的生命周期。

AuthorizationAttributeSourceAdvisor：使shiro认证注解（`@RequiresPermissions、@RequiresRoles、@RequiresUser、@RequiresGuest`）工作。[具体可以看这个链接](https://blog.csdn.net/wangjun5159/article/details/51889628)

```java
/**
 * @author : choibin
 * @date : 13:38  2019/9/13 0013
 */
@Configuration
public class ShiroConfig {
    //安全器管理-管理所有的subject
    @Bean
    public SecurityManager securityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    //过滤链配置
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设定用户登录认证时的跳转转发 没有授权时的跳转链接
        shiroFilterFactoryBean.setLoginUrl("login.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");

        //过滤器链配置
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    //关于shiro的bean生命周期的管理
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();

        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
```

**身份认证的核心代码**

SysLoginController

```java
....省略其他代码
  @ResponseBody
    @RequestMapping(value = "/sys/login",method = RequestMethod.POST)
    public BaseResponse login(String username,String password,String captcha){
        logger.info("用户名：{}，密码：{}，验证码：{}",username,password,captcha);

        //校验验证码
        String kaptcha = ShiroUtil.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!kaptcha.equals(captcha)){
            return new BaseResponse(StatusCode.InvalidCode);
        }

        //提交登录
        try{
            Subject subject = SecurityUtils.getSubject();
            if(!subject.isAuthenticated()){
                UsernamePasswordToken token  = new UsernamePasswordToken(username,password);
                subject.login(token);
            }
        }catch (UnknownAccountException e) {
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return new BaseResponse(StatusCode.AccountPasswordNotMatch);
        }catch (LockedAccountException e) {
            return new BaseResponse(StatusCode.AccountHasBeenLocked);
        }catch (AuthenticationException e) {
            return new BaseResponse(StatusCode.AccountValidateFail);
        }
        return new BaseResponse(StatusCode.Success);
    }

```

**身份认证的流程**

![Image text](https://github.com/ChoiBin/PermissionMangementPlatform/blob/master/picture/9.png)
1. 首先调用 Subject.login(token) 进行登录，其会自动委托给 **Security Manager**，调用之前必须通过 SecurityUtils.setSecurityManager() 设置；
2. SecurityManager 负责真正的身份验证逻辑；它会委托给 Authenticator 进行身份验证；
3. Authenticator 才是真正的身份验证者，Shiro API 中核心的身份认证入口点，此处可以自定义插入自己的实现；
4. Authenticator 可能会委托给相应的 AuthenticationStrategy 进行多 Realm 身份验证，默认 ModularRealmAuthenticator 会调用 AuthenticationStrategy 进行多 Realm 身份验证；
5. Authenticator 会把相应的 token 传入 Realm，从 Realm 获取身份验证信息，如果没有返回 / 抛出异常表示身份验证失败了。此处可以配置多个 Realm，将按照相应的顺序及策略进行访问。

自己实现了一个Realm，在ShiroConfig中将这个Realm传入，并设置进Security Manager当中。在这个UserRealm中，继承了AuthorizingRealm，在后面用于资源权限的分配和用户登录认证

```java
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
```


