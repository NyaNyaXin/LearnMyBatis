package com.cx.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 测试Dao层的工作
 * 
 * 推荐Spring项目就是用Spring的单元测试，可以自动注入需要的组件
 * 1.导入SpringTest模块
 * 2.使用@ContextConfiguration注解指定Spring配置文件的注解
 * 3.直接autowired要使用的组件即可
 * **/

import com.cx.crud.bean.Employee;
import com.cx.crud.dao.DepartmentMapper;
import com.cx.crud.dao.EmployeeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestMapper {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;

	/**
	 * 测试Departmentmapper
	 **/
	@Test
	public void testCRUD() {
		/*
		 * //1.创建SpringIOC容器 ApplicationContext context = new
		 * ClassPathXmlApplicationContext("applicationContext.xml"); //2.从容器中获取mapper
		 * DepartmentMapper departmentMapper = context.getBean(DepartmentMapper.class);
		 */
		System.out.println(departmentMapper);
		// 1.插入几个部门
		// departmentMapper.insertSelective(new Department(null,"开发部"));
		// departmentMapper.insertSelective(new Department(null,"测试部"));
		// 2.生成员工数据，测试员工插入
		// employeeMapper.insertSelective(new Employee(null, "chenxin", "M",
		// "chenxin@163.com", 1));
		// employeeMapper.insertSelective(new Employee(null, "丁健辉", "M",
		// "chenxin@163.com", 2));
		// //3.批量插入多个员工：使用可以执行批量操作的sqlsession

		EmployeeMapper employeeMapper2 = sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5) + "" + i;
			employeeMapper2.insertSelective(new Employee(null, uid, "M", uid + "@163.com", 2));
		}
		System.out.println("批量");
	}
}
