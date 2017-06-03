package com.cx.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cx.crud.bean.Employee;
import com.cx.crud.bean.EmployeeExample;
import com.cx.crud.bean.EmployeeExample.Criteria;
import com.cx.crud.dao.EmployeeMapper;
@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	/**
	 * 查询所有员工
	 * **/
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return employeeMapper.selectByExampleWithDept(null);
	}
	/**
	 * 员工保存
	 * **/
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}
	/**
	 * 检验员工名称是否可用
	 *
	 * **/
	public Boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count==0;
	}
	
}
