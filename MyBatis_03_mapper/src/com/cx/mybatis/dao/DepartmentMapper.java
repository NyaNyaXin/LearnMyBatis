package com.cx.mybatis.dao;

import com.cx.mybatis.bean.Department;

public interface DepartmentMapper {

	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id);
}
