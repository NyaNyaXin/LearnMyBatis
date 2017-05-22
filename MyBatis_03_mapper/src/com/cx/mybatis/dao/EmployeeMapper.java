package com.cx.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapper {
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
	public Employee getEmpById(Integer id);
	public long addEmployee(Employee employee);
	public boolean updateEmployee(Employee employee);
	public long delete(Integer id);
}
