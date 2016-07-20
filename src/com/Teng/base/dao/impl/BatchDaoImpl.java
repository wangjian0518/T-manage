package com.Teng.base.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.Teng.base.dao.BatchDao;
/**
 * 
 * 批量处理 insert、update、delete
 *
 */
public class BatchDaoImpl implements BatchDao {
	private Map<String, SqlSessionFactory> sqlSessionBeans;
	
	public long insertBatchObjects(List obj, String dataSource, String sqlId) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans
				.get(dataSource);
		if(obj==null||obj.size()==0){
			return -1;
		}
		int insertResult = 0;
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			for (Object object : obj) {
				if (object != null) {
					insertResult += session.insert(sqlId,
							object);
				} else {
					insertResult += session.insert(sqlId);
				}
				if(insertResult%100==0){
					session.commit();
				}
			}
			session.commit();
		} finally {
			session.close();
		}
		return insertResult;
	}
	public long updateBatchObjects(List obj, String dataSource, String sqlId) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans
				.get(dataSource);
		if(obj==null||obj.size()==0){
			return -1;
		}
		int updateResult = 0;
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			for (Object object : obj) {
				if (object != null) {
					updateResult += session.update(sqlId,
							object);
				} else {
					updateResult += session.update(sqlId);
				}
				if(updateResult%100==0){
					session.commit();
				}
			}
			session.commit();
		} finally {
			session.close();
		}
		return updateResult;
	}
	
	public <T> long insertList(String sqlId,String dataSource,List<T> list) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans.get(dataSource);
		if(list==null||list.size()==0){
			return -1;
		}
		int insertResult = 0;
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			for (int i=0;i<list.size();i++) {
				T object = list.get(i);
				if (object != null) {
					insertResult += session.insert(sqlId,
							object);
				} else {
					insertResult += session.insert(sqlId);
				}
				if(i %1000 == 0) {
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
		} finally {
			session.close();
		}
		return insertResult;
	}
	
	public <T> long insertBatchList(String sqlId,String dataSource,List<T> list) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans.get(dataSource);
		if(list==null||list.size()==0){
			return -1;
		}
		int insertResult = 0;
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			insertResult = session.insert(sqlId, list);
		} finally {
			session.close();
		}
		return insertResult;
	}
	
	public Map<String, SqlSessionFactory> getSqlSessionBeans() {
		return sqlSessionBeans;
	}

	public void setSqlSessionBeans(Map<String, SqlSessionFactory> sqlSessionBeans) {
		this.sqlSessionBeans = sqlSessionBeans;
	}

}
