1.
xFire的发布地址如下：
http://localhost:8080/springmvc_hibernate/services/IUser?wsdl

2.
此项目目前的日志输出级别为：INFO,可在log4j.properties文件中进行修改。

3.
此项目目前所用的数据库为:mysql,在spring-common.xml文件中<prop key="hibernate.hbm2ddl.auto">update</prop>
表示根据实体bean生成表结构。在实际的开发中，都是根据表生成实体bean,因此此属性可删除。

4.
必须在spring-mvc.xml中加入“支持 Shiro对Controller的方法级AOP安全控制 ”下面的代码，shiro注解式权限验证才会生效。

5.
刚导入项目时，没有任何用户数据，可访问http://localhost:8080/springmvc_hibernate/user/create
添加数据，再登录即可。
