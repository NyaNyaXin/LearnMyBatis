package com.cx.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.cx.mybatis.bean.Employee;
import com.cx.mybatis.bean.EmployeeExample;
import com.cx.mybatis.bean.EmployeeExample.Criteria;
import com.cx.mybatis.dao.EmployeeMapper;

public class MyBatisTest {

	private SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testMbg() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("mbg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
	
//	@Test
//	public void testMyBatis3Simple() throws IOException {
//		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
//		SqlSession session = sqlSessionFactory.openSession();
//		try {
//			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
//			List<Employee> list = mapper.selectAll();
//			for(Employee employee:list) {
//				System.out.println(employee.getLastName());
//			}
//		} finally {
//			session.close();
//		}
//	}
	
	@Test
	public void testMyBatis3() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			//xxxExample封装查询条件
			//1.查询所有
			//List<Employee> employees = mapper.selectByExample(null);
			//2.查询员工名字中有e字母的，并且性别是1
			//封装员工查询条件
			EmployeeExample employeeExample = new EmployeeExample();
			//创建一个Criteria，这个Criteria就是拼装查询条件
			Criteria criteria = employeeExample.createCriteria();
			criteria.andLastNameLike("%c%").andGenderEqualTo("1");
			
			Criteria criteria2 = employeeExample.createCriteria();
			criteria2.andEmailLike("%c%");
			employeeExample.or(criteria2);
			List<Employee> list = mapper.selectByExample(employeeExample);

			for(Employee employee:list) {
				System.out.println(employee.getLastName()+":"+employee.getId());
			}
		} finally {
			session.close();
		}
	}

}
