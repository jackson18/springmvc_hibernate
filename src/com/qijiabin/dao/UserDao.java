package com.qijiabin.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.qijiabin.entity.Userinfo;
import com.qijiabin.query.UserQuery;

@Repository
public class UserDao extends HibernateDaoSupport{

	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public List<Userinfo> findList(UserQuery query){
		StringBuilder sb = new StringBuilder();
		sb.append("from Userinfo t where 1=1");
		//查询过滤条件
		
		sb.append(" order by uid");
		return this.getHibernateTemplate().find(sb.toString());
	}
	
	public void save(Userinfo entity){
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Userinfo getById(int uid){
		String hql = "from Userinfo t where t.uid="+uid;
		List<Userinfo> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public Userinfo getByNamePass(String uname,String upass){
		String hql = "from Userinfo t where t.uname='"+uname+"' and t.upass='"+upass+"' ";
		List<Userinfo> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public Userinfo getByName(String uname){
		String hql = "from Userinfo t where t.uname='"+uname+"' ";
		List<Userinfo> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public void delete(Userinfo entity){
		this.getHibernateTemplate().delete(entity);
	}
}
