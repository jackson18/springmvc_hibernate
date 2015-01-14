package com.qijiabin.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qijiabin.dao.ClazzDao;
import com.qijiabin.entity.Clazz;
import com.qijiabin.query.ClazzQuery;

@Transactional
@Service
public class ClazzManager {

	@Autowired
	private ClazzDao clazzDao;
	
	
	public List<Clazz> findList(ClazzQuery query){
		return clazzDao.findList(query);
	}
	
	public void save(Clazz entity){
		clazzDao.save(entity);
	}
	
	public Clazz getById(int cid){
		return clazzDao.getById(cid);
	}
	
	public void delete(Clazz entity){
		clazzDao.delete(entity);
	}

	public void setClazzDao(ClazzDao clazzDao) {
		this.clazzDao = clazzDao;
	}
	
	
}
