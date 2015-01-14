package com.qijiabin.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.qijiabin.entity.Student;
import com.qijiabin.query.StudentQuery;

@Repository
public class StudentDao extends HibernateDaoSupport {

	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> findList(StudentQuery query){
		StringBuilder sb = new StringBuilder();
		sb.append("from Student t where 1=1");
		//查询过滤条件
		
		sb.append(" order by cid");
		return this.getHibernateTemplate().find(sb.toString());
	}
	
	public void save(Student entity){
		this.getHibernateTemplate().save(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Student getById(int sid){
		String hql = "from Student t where t.sid="+sid;
		List<Student> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
