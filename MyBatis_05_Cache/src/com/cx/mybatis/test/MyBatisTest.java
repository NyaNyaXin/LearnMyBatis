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
	 * 一级缓存：(本地缓存)：sqlSession级别的缓存，一级缓存是一直开启的;SqlSession级别的一个Map
	 * 	与数据库同一次会话期间查询到的数据会被放在本地缓存中。
	 * 	以后如果需要获取相同的数据，直接从缓存中拿，不再去查数据库
	 * 	
	 * 	一级缓存失效的情况(没有使用到当前一级缓存的情况，效果就是，还需要再次向数据库发送sql语句)
	 * 	1.SQLSession不同
	 * 	2.sqlSession相同，查询条件不同(当前一级缓存中还没有这个数据)
	 * 	3.SQLSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
	 * 	4.SQLSession相同，手动清除了一级缓存中的数据
	 * 二级缓存：(全局缓存)
	 * @throws IOException 
	 */
	@Test
	public void testFristLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		//1.SQLSession不同
		//SqlSession session2 = sqlSessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			System.out.println(employee);
			//EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);
			//2.sqlSession相同，查询条件不同
			
			//3.SQLSession相同，两次查询之间执行了增删改操作
//			mapper.addEmployee(new Employee(null, "testCache", "cache", "1"));
//			System.out.println("数据添加");

			//4.SQLSession相同，手动清除了一级缓存中的数据
			session.clearCache();
			Employee employee2 = mapper.getEmpById(1);
			System.out.println(employee2);
		} finally {
			session.close();
			//session2.close();
		}
	}
}


