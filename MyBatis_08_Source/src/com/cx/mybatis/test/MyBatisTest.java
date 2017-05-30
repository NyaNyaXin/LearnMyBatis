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
	 * 1.获取SqlSessionFactory对象
	 * 		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSessionFactory
	 * 		注意：MappedStatement：代表一个增删改查的详细信息
	 * 2.获取SqlSession对象
	 * 		返回一个DefaultSqlSession对象，包含Executor和Configuration
	 * 		这一步会创建Executor
	 * 3.获取接口的实现类对象(MapperProxy)
	 * 		getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
	 * 		代理对象里面包含了DefaultSqlSession(Executor)
	 * 4.执行增删改查方法
	 * 
	 * 总结：
	 * 	1.根据配置文件(全局，映射)初始化出Configuration对象
	 * 	2.创建DefaultSqlSession对象，他里面包含Configuration和
	 * 	Executor(根据全局配置文件中的defaultExecutorType创建出对应的Executor)
	 * 	3.DefaultSqlSession.getMapper()：拿到Mapper接口对应的MapperProxy
	 * 	4.MapperProxy里面有(DefaultSqlSession)
	 * 	5.执行增删改查方法：
	 * 		1)调用DefaultSqlSession的增删改查(Executor);
	 * 		2)会创建一个StatementHandler对象.(同时也会创建出ParameterHandler和ResultSetHandler)
	 * 		3)调用StatementHandler的预编译参数以及设置参数值
	 * 			使用ParameterHandler来给Sql语句设置参数
	 * 		4)调用StatementHandler的增删改查方法
	 * 		5)使用ResultSetHandler封装结果
	 * 	注意：四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
	 * **/
	
	@Test
	public void test01() throws IOException {
		//1.获取SqlSessionFactory对象
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		//2.获取SqlSession对象
		SqlSession session = sessionFactory.openSession();
		try {
		//3.获取接口的实现类对象
		//会为接口自动的创建一个代理对象，代理对象去执行CRUD操作
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		
		Employee employee = mapper.getEmpById(1);
		System.out.println(mapper.getClass());
		System.out.println(employee);
		}finally {
			session.close();
		}
		
	}

}