package com.qijiabin.manager;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qijiabin.dao.UserDao;
import com.qijiabin.entity.Userinfo;
import com.qijiabin.query.UserQuery;
import com.qijiabin.util.EncryptUtils;

@Transactional
@Service
public class UserManager {

	@Autowired
	private UserDao userDao;
	
	
	
	

	public List<Userinfo> findList(UserQuery query){
		return userDao.findList(query);
	}
	
	public void save(Userinfo entity){
		if(entity.getUid()==0 || "".equals(entity.getUid())){//新增用户时对密码进行加密
			String newPass = EncryptUtils.encryptMD5(entity.getUpass());
			entity.setUpass(newPass);//设置加密后的密码
		}
		userDao.save(entity);
	}
	
	public Userinfo getById(int uid){
		return userDao.getById(uid);
	}
	
	public Userinfo getByNamePass(String uname,String upass){
		return userDao.getByNamePass(uname, upass);
	}
	
	public Userinfo getByName(String uname){
		return userDao.getByName(uname);
	}
	
	public void delete(Userinfo entity){
		userDao.delete(entity);
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
