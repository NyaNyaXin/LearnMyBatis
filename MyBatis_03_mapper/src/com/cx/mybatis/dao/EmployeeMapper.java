package com.cx.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapper {
	public List<Employee> getEmpsByLastNameLike(String lastName);
	public Employee getEmpByMap(Map<String, Object> map);
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
	public Employee getEmpById(Integer id);
	public long addEmployee(Employee employee);
	public boolean updateEmployee(Employee employee);
	public long delete(Integer id);
}
