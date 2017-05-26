package com.cx.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSql {
	
	public List<Employee> getEmpsByConIf(Employee employee);
	
	public List<Employee> getEmpsByConTrim(Employee employee);
	
	public List<Employee> getEmpBysConChoose(Employee employee);

	public void updateEmp(Employee employee);
	
	public List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);
}
