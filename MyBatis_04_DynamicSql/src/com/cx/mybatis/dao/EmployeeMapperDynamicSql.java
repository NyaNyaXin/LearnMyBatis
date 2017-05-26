package com.cx.mybatis.dao;

import java.util.List;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSql {
	
	public List<Employee> getEmpsByConIf(Employee employee);

}
