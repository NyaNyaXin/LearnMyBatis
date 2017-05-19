package com.cx.mybatis.dao;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapper {
	public Employee getEmpById(Integer id);
	public long addEmployee(Employee employee);
	public boolean updateEmployee(Employee employee);
	public long delete(Integer id);
}
