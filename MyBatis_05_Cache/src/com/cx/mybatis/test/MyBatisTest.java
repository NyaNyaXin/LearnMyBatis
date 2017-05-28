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
	 * 二级缓存：(全局缓存)：基于namespace级别的缓存，一个namespace可以对应一个二级缓存
	 * 	工作机制：
	 * 	1.一个会话查询一条数据，这个数据会被放在当前会话的一级缓存中；
	 * 	2.如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容
	 * 	3.sqlSession===EmployeeMapper====Employee
	 * 	  			   DepartmentMapper===Department
	 * 		不同namespace查出的数据会放入自己对应的缓存中(map)
	 * 		效果：数据会从二级缓存中获取
	 * 		查出的数据都会被默认放在一级缓存中。
	 * 		只有会话关闭后，一级缓存中的数据才会转移到二级缓存
	 * 
	 * 	使用步骤：
	 * 	1.开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
	 * 	2.去mapper.xml中配置使用二级缓存：<cache></cache>
	 * 	3.POJO需要实现序列化接口
	 * 
	 * 	和缓存有关的设置/属性
	 * 		1.cacheEnabled=true:false:关闭二级缓存,一级缓存一直可用
	 * 		2.每个select标签都有useCache="true"属性：
	 * 			false：一级使用，二级不使用
	 * 		3.每个增删改标签的flushCache="true"属性默认为true，每次增删改就会清除缓存，一级缓存有效,二级缓存也有效
	 * 			flushCache="true在查询标签中默认为false，如果改为true，每次查询都会清除缓存；缓存是没有被使用的
	 * 		4.sqlsession.clearCache()都是清除一级缓存
	 * 		5.localCacheScope：本地缓存作用域：（一级缓存SESSION）：当前会话的所有数据保存在会话缓存中
	 * 			STATEMENT：可以禁用一级缓存；
	 * @throws IOException 
	 * 
	 * 
	 */
	/**
	 * @throws IOException
	 */
	@Test
	public void testSecondLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		SqlSession session2 = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			System.out.println(employee);
			session.close();
			session.clearCache();
			mapper2.addEmployee(new Employee(null, "aa", "aa.com", "1"));
			//第二次查询是从二级缓存中拿到的，并没有发送新的sql
			Employee employee2 = mapper2.getEmpById(1);
			System.out.println(employee2);
			
			session2.close();
		} finally {
			session.close();
			session2.close();
		}
	}
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
			//session.clearCache();
			Employee employee2 = mapper.getEmpById(1);
			System.out.println(employee2);
		} finally {
			session.close();
			//session2.close();
		}
	}
}


