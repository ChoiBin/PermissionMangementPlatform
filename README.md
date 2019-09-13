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
