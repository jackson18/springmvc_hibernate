package com.qijiabin.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.qijiabin.entity.Clazz;
import com.qijiabin.query.ClazzQuery;

@Repository
public class ClazzDao extends HibernateDaoSupport {

	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public List<Clazz> findList(ClazzQuery query){
		StringBuilder sb = new StringBuilder();
		sb.append("from Clazz t where 1=1");
		//查询过滤条件
		
		sb.append(" order by cid");
		return this.getHibernateTemplate().find(sb.toString());
	}
	
	public void save(Clazz entity){
		this.getHibernateTemplate().save(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Clazz getById(int cid){
		String hql = "from Clazz t where t.cid="+cid;
		List<Clazz> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public void delete(Clazz entity){
		this.getHibernateTemplate().delete(entity);
	}
}
