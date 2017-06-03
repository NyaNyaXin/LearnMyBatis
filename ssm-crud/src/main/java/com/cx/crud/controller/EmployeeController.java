package com.cx.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cx.crud.bean.Employee;
import com.cx.crud.bean.Message;
import com.cx.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 处理员工CRUD请求
 **/
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 导入jackson包
	 * **/
	@ResponseBody
	@RequestMapping("/emps")
	public Message getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 这不是一个分页查询
		// 引入pageHelper插件
		// 在查询之前只需调用如下方法,传入页码以及每页的大小
		PageHelper.startPage(pn, 5);
		// startPage紧跟的查询就是分页查寻
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询之后的结果，只需要将pageInfo交给页面就行
		// 封装了详细的分页信息，包括数据,可以传入连续显示的页数
		PageInfo page = new PageInfo(emps, 5);
		model.addAttribute("pageInfo", page);
		return Message.success().add("pageInfo",page);
	}

	/**
	 * 查询员工数据（分页）
	 **/
	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 这不是一个分页查询
		// 引入pageHelper插件
		// 在查询之前只需调用如下方法,传入页码以及每页的大小
		PageHelper.startPage(pn, 8);
		// startPage紧跟的查询就是分页查寻
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询之后的结果，只需要将pageInfo交给页面就行
		// 封装了详细的分页信息，包括数据,可以传入连续显示的页数
		PageInfo page = new PageInfo(emps, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Message saveEmp(Employee employee) {
		employeeService.saveEmp(employee);
		return Message.success();
	}
	/**
	 * 检查用户名
	 * **/
	@ResponseBody
	@RequestMapping("/checkuser")
	public Message checkUser(@RequestParam("empName") String empName) {
		Boolean b =  employeeService.checkUser(empName);
		if(b) {
			return Message.success();
		}else {
			return Message.fail();
		}
	}
}
