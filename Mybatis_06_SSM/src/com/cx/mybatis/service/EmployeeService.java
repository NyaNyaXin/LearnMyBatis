package com.cx.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cx.mybatis.bean.Employee;
import com.cx.mybatis.dao.EmployeeMapper;

@Service
public class EmployeeService {
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private EmployeeMapper employeeMapper;
	
	public List<Employee> getEmps(){
		//EmployeeMapper employeeMapper2 = sqlSession.getMapper(EmployeeMapper.class);
		return employeeMapper.getEmps();
	}
}
