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

	/**
	 * 1.根据xml配置文件(全局配置文件)创建一个SqlSessionFactory对象
	 * 		有数据源一些运行环境的信息
	 * 2.sql映射文件：配置了每一个sql，以及sql的封装规则等。
	 * 3.将sql映射文件注册在全局配置文件中
	 * 4.写代码
	 * 		1).通过全局配置文件得到SqlSessionFactory
	 * 		2).使用sqlSession工厂，获取到SqlSession对象，使用他来进行增删改查
	 * 		一个sqlsession就是代表和数据库的一次会话，用完需要关闭
	 * 		3).使用sql唯一标识来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
	 **/
	@Test
	public void test() throws IOException {
		
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2.获取SqlSession实例，能直接执行已经映射的sql语句
		// arg0:sql的唯一标识符
		// arg1:执行sql要用到的参数
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Employee employee = session.selectOne("com.cx.mybatis.selectEmployee", 1);
			System.out.println(employee);
		} finally {
			session.close();
		}
		
		
	}
	private SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
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
