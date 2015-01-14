package com.qijiabin.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qijiabin.dao.ClazzDao;
import com.qijiabin.dao.StudentDao;
import com.qijiabin.entity.Clazz;
import com.qijiabin.entity.Student;

@Transactional
@Service
public class StudentManager {

	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ClazzDao clazzDao;
	
	public Student getById(int sid){
		return studentDao.getById(sid);
	}
	
	public void saveEntity(Student entity,Integer cid){
		Clazz clazz = clazzDao.getById(cid);
		entity.setClazz(clazz);//设置班级
		studentDao.save(entity);
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public void setClazzDao(ClazzDao clazzDao) {
		this.clazzDao = clazzDao;
	}
	
	
	
	
}
