package com.cx.mybatis.dao;

import java.util.List;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapper {
	public Employee getEmpById(Integer id);
	
	public List<Employee> getEmps();
	
	public long addEmployee(Employee employee);
}
