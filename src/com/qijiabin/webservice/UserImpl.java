package com.qijiabin.webservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.qijiabin.entity.Userinfo;
import com.qijiabin.manager.UserManager;

public class UserImpl implements IUser {
	
	@Autowired
	private UserManager userManager;

	public void saveUser(int uid, String uname, String upass) {
		Userinfo u = new Userinfo();
		u.setUid(uid);
		u.setUname(uname);
		u.setUpass(upass);
		userManager.save(u);
	}

}
