package com.cx.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.cx.mybatis.bean.Employee;
import com.cx.mybatis.dao.EmployeeMapper;

public class MyBatisTest {

	private SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * 两级缓存
	 * 一级缓存：(本地缓存)
	 * 	与数据库同一次会话期间查询到的数据会被放在本地缓存中。
	 * 	以后如果需要获取相同的数据，直接从缓存中拿，不再去查数据库
	 * 二级缓存：(全局缓存)
	 * @throws IOException 
	 */
	@Test
	public void testFristLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			System.out.println(employee);
			Employee employee2 = mapper.getEmpById(1);
			System.out.println(employee2==employee);
		} finally {
			session.close();
		}
	}
}


