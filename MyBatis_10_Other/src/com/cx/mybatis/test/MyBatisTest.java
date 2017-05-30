package com.cx.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.cx.mybatis.bean.Employee;
import com.cx.mybatis.dao.EmployeeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 1.接口式编程
 * 		原生：		Dao		======>		DaoImpl
 * 		MyBatis：	Mapper	======>		xxMapper.xml
 * 
 * 2.SqlSession对象代表和数据库的一次会话；用完必须关闭
 * 3.SqlSession对象和connection一样，都是非线程安全的。每次使用都应该去获取新的对象
 * 4.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		(将接口和xml进行绑定)
 * 		EmployeeMapper empMapper = sqlSession.getmapper(EmployeeMapper.class);
 * 5.两个重要的配置文件
 * 		全局配置文件：包含数据库连接池信息，事务管理器信息。。。。等系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息：将sql抽取出来
 * **/
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
		Page<Object> page = PageHelper.startPage(5, 1);
		
		List<Employee> emps = mapper.getEmps();
		//传入要连续显示多少页
		PageInfo<Employee> pageInfo = new PageInfo<>(emps,5);
		for(Employee emp:emps) {
			System.out.println(emp);
		}
		System.out.println("当前页码:"+page.getPageNum());
		System.out.println("总计录数："+page.getTotal()+":"+page.getPageSize()+page.getPages());
		System.out.println(pageInfo.getPageNum());
		System.out.println(pageInfo.isIsLastPage());
		System.out.println("连续显示的页码");
		
		int[] nums = pageInfo.getNavigatepageNums();
		for(int i=0;i<nums.length;i++) {
			System.out.println(nums[i]);
		}
		}finally {
			session.close();
		}
		
	}

}
