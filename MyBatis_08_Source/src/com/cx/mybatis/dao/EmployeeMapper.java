package com.cx.mybatis.dao;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapper {
	public Employee getEmpById(Integer id);
}
