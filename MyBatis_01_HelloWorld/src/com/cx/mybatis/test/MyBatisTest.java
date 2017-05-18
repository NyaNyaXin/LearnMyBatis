package com.cx.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.cx.mybatis.bean.Employee;

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
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.获取SqlSession实例，能直接执行已经映射的sql语句
		// arg0:sql的唯一标识符
		// arg1:执行sql要用到的参数
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Employee employee = session.selectOne("com.cx.mybatis.selectEmployee", "1");
			System.out.println(employee);
		} finally {
			session.close();
		}
	}

}
