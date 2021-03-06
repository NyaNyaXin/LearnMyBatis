package com.cx.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.cx.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	//多条记录封装一个map：map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javabean
	@MapKey("lastName")//告诉mybatis封装这个map的时候使用哪个属性作为主键
	public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);
	
	
	//返回一条记录的map(key:列名,value:值)
	public Map<String, Object> getEmpByIdReturnMap(Integer id);
	public List<Employee> getEmpsByLastNameLike(String lastName);
	public Employee getEmpByMap(Map<String, Object> map);
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
	public Employee getEmpById(Integer id);
	public long addEmployee(Employee employee);
	public boolean updateEmployee(Employee employee);
	public long delete(Integer id);
}
