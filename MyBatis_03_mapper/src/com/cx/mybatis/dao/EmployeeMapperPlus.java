package com.cx.mybatis.dao;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapperPlus {
	public Employee getEmpById(Integer id);
	
	public Employee getEmpAndDept(Integer id);
	
	public Employee getEmpByIdStep(Integer id);
}
