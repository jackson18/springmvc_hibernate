package com.qijiabin.manager;



import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qijiabin.entity.Userinfo;
import com.qijiabin.util.EncryptUtils;


@Service
public class MonitorRealm extends AuthorizingRealm {
	
	@Autowired
	private UserManager userManager;
	
	
	@Override
	public String getName() {
		return "myRealm";
	}
	
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//根据用户名查找角色
		 String username = (String) principals.getPrimaryPrincipal();  
		 if("admin".equals(username)){//admin具有最高权限
			 info.addRole("superAdmin");
			 info.addStringPermission("/user/update");
			 info.addStringPermission("/user/delete");
		 }else{
			 info.addRole("default");
		 }
		 info.addStringPermission("/user/list");
		 info.addStringPermission("/user/save");
		 return info;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//取得令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		//取得用户名
		String username = token.getUsername();
		//取得密码(加密后的)
		String password = EncryptUtils.encryptMD5(token.getPrincipal().toString());
		//根据用户名密码去验证数据库
		Userinfo user = userManager.getByNamePass(username, password);
		if(user != null){
			//	user.setUpass(password);//成功，则对密码进行加密
			//如果身份验证成功，返回一个AuthenticationInfo实现
			return new SimpleAuthenticationInfo(username,password, getName());
		}else{
			throw new AuthenticationException();//失败
		}
		
	}

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
