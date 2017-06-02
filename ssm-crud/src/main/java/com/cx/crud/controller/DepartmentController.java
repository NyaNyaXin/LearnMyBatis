package com.cx.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cx.crud.bean.Department;
import com.cx.crud.bean.Message;
import com.cx.crud.service.DepartmentService;

/**
 * 处理和部门有关的请求
 * **/
@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 返回所有的部门信息
	 * **/
	@ResponseBody
	@RequestMapping("/depts")
	public Message getDepts() {
		//查出的所有部门信息
		List<Department> depts = departmentService.getDepts();
		return Message.success().add("depts", depts);
	}
}
